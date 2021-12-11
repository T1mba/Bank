package ru.yotc.worldskills

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private lateinit var Bank: Button
    private lateinit var stringDate:String
    private lateinit var app: Myapp
    private lateinit var usdView: TextView
    private lateinit var eurView:TextView
    val valuteLayout = layoutInflater.inflate(R.layout.activity_valutes, null)
    val valutesRecyclerView = valuteLayout.findViewById<RecyclerView>(R.id.valutesRecyclerView)


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Bank = findViewById(R.id.Bank)
        app = applicationContext as Myapp
        usdView = findViewById(R.id.USD)
        eurView = findViewById(R.id.EUR)
        val date = LocalDateTime.now()
      stringDate  = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(date)
        getValute()

    }
        fun findValute(charCode:String): Valutes? {
            for (i in 0 until app.valuteList.size) {
                if (app.valuteList[i].charCode == charCode) {
                        return app.valuteList[i]
                }
            }
            return null
        }
    fun showDetailInfo(){

    }

    fun BanksClick(view: View) {
        startActivity(Intent(this,BankActivity::class.java))
    }
    fun KursValue(view: View) {
        startActivity(Intent(this,ValutesActivity::class.java))
    }
    fun Auth(view: View) {}
    fun getValute(){
        HTTP.requestGET("http://www.cbr.ru/scripts/XML_daily.asp?date_req=${stringDate} ",
                null
        ){result, error ->

            runOnUiThread {
                if(result!=null)
                    try {
                        val re = """<CharCode>(\w{2}.*?)</CharCode>.*?<Nominal>(.*?)</Nominal><Name>(.*?)</Name><Value>(.*?)</Value>""".toRegex(
                                RegexOption.DOT_MATCHES_ALL)
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
                        valutesRecyclerView.adapter?.notifyDataSetChanged()

                    }

                    catch (e: Exception){
                        AlertDialog.Builder(this)
                                .setTitle("Ошибка")
                                .setMessage(e.message)
                                .setPositiveButton("OK",null)
                                .create()
                                .show()
                    }

            }
        }
    }
}