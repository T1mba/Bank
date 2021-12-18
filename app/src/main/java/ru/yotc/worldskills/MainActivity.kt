package ru.yotc.worldskills

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject
import java.lang.Exception
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private lateinit var Bank: Button
    private lateinit var stringDate: String
    private lateinit var app: Myapp
    private lateinit var usdView: TextView
    private lateinit var eurView: TextView
    lateinit var log: TextView
    lateinit var logout: TextView
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Bank = findViewById(R.id.Bank)
        app = applicationContext as Myapp
        usdView = findViewById(R.id.USD)
        eurView = findViewById(R.id.EUR)
        val date = LocalDateTime.now()
        log = findViewById(R.id.logButton)
        logout = findViewById(R.id.logoutButton)
        stringDate = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(date)
        val onLoginResponce: (login: String, password: String) -> Unit = { login, password ->
            // первым делом сохраняем имя пользователя,
            // чтобы при необходимости можно было разлогиниться
            app.username = login

            // затем формируем JSON объект с нужными полями
            val json = JSONObject()
            json.put("username", login)
            json.put("password", password)

            // и вызываем POST-запрос /login
            // в параметрах не забываем указать заголовок Content-Type
            HTTP.requestPOST(
                "http://s4a.kolei.ru/login",
                json,
                mapOf(
                    "Content-Type" to "application/json"
                )
            ) { result, error ->
                if (result != null) {
                    try {
                        // анализируем ответ
                        val jsonResp = JSONObject(result)

                        // если нет объекта notice
                        if (!jsonResp.has("notice"))
                            throw Exception("Не верный формат ответа, ожидался объект notice")

                        // есть какая-то ошибка
                        if (jsonResp.getJSONObject("notice").has("answer"))
                            throw Exception(jsonResp.getJSONObject("notice").getString("answer"))

                        // есть токен!!!
                        if (jsonResp.getJSONObject("notice").has("token")) {
                            app.token = jsonResp.getJSONObject("notice").getString("token")
                            runOnUiThread {

                                // тут можно переходить на следующее окно
                                Toast.makeText(
                                    this,
                                    "Success get token: ${app.token}",
                                    Toast.LENGTH_LONG
                                )
                                    .show()


                            }
                        } else
                            throw Exception("Не верный формат ответа, ожидался объект token")
                    } catch (e: Exception) {
                        runOnUiThread {
                            AlertDialog.Builder(this)
                                .setTitle("Ошибка")
                                .setMessage(e.message)
                                .setPositiveButton("OK", null)
                                .create()
                                .show()
                        }
                    }
                } else
                    runOnUiThread {
                        AlertDialog.Builder(this)
                            .setTitle("Ошибка http-запроса")
                            .setMessage(error)
                            .setPositiveButton("OK", null)
                            .create()
                            .show()
                    }
            }

        }
        log.setOnClickListener {
            LoginDialog(onLoginResponce)
                .show(supportFragmentManager, null)
        }
        logout.setOnClickListener {
            HTTP.requestPOST("http://s4a.kolei.ru/logout",
            JSONObject().put("username", app.username),
                mapOf(
                    "Content-Type" to "application/json"
                )
                ){result, error ->
                app.token = ""
                    runOnUiThread{
                        if(result!=null){
                            Toast.makeText(this,"Logout success!", Toast.LENGTH_LONG).show()
                        }
                        else{
                            AlertDialog.Builder(this)
                                .setTitle("Ошибка http-запроса")
                                .setMessage(error)
                                .setPositiveButton("OK",null)
                                .create()
                                .show()
                        }
                    }
                }
            }
        }
    fun findValute(charCode: String): Valutes? {
        for (i in 0 until app.valuteList.size) {
            if (app.valuteList[i].charCode == charCode) {
                return app.valuteList[i]
            }
        }
        return null
    }

    fun showDetailInfo() {

    }

    fun BanksClick(view: View) {
        startActivity(Intent(this, BankActivity::class.java))
    }

    fun KursValue(view: View) {

        startActivity(Intent(this, ValutesActivity::class.java))
    }

    fun getValute() {
        HTTP.requestGET(
            "http://www.cbr.ru/scripts/XML_daily.asp?date_req=${stringDate} ",
            null
        ) { result, error ->

            runOnUiThread {
                if (result != null)
                    try {
                        val re =
                            """<CharCode>(\w{2}.*?)</CharCode>.*?<Nominal>(.*?)</Nominal><Name>(.*?)</Name><Value>(.*?)</Value>""".toRegex(
                                RegexOption.DOT_MATCHES_ALL
                            )
                        val seq = re.findAll(result)
                        seq.forEach {
                            app.valuteList.add(
                                Valutes(
                                    it.groupValues[1],
                                    it.groupValues[2].toInt(),
                                    it.groupValues[3],
                                    it.groupValues[4].replace(',', '.').toDouble()
                                )
                            )

                        }


                    } catch (e: Exception) {
                        AlertDialog.Builder(this)
                            .setTitle("Ошибка")
                            .setMessage(e.message)
                            .setPositiveButton("OK", null)
                            .create()
                            .show()
                    }

            }
        }
    }

    fun user(view: android.view.View) {
        startActivity(Intent(this,User_Activity::class.java))
    }
}




