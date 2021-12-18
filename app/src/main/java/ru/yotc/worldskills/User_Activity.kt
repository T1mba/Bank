package ru.yotc.worldskills

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject

class User_Activity : AppCompatActivity() {
    private lateinit var firstView: RecyclerView
    private lateinit var app: Myapp
    private lateinit var secView:RecyclerView
    private lateinit var creditView:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        firstView = findViewById(R.id.firstView)

        app = applicationContext as Myapp
        secView = findViewById(R.id.secView)
        firstView.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        val cardsAdapter = CardsAdapter(app.cardList,this)
            firstView.adapter = cardsAdapter
        secView.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        val userAdapter = UserAdapter(app.userList,this)
        secView.adapter = userAdapter
        creditView = findViewById(R.id.creditView)
        creditView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
        val creditAdapter = CreditsAdapter(app.creditList, this)
        creditView.adapter = creditAdapter
        getCards()
        getUsers()
        getCredits()
    }

    fun getCards() {
        HTTP.requestGET(
            "http://192.168.0.3:8080/getcards",
            null
        ) { result, error ->
            runOnUiThread {
                if (result != null) {
                    try {
                        app.cardList.clear()
                        val jsonGet = JSONObject(result)
                        if(!jsonGet.has("notice"))
                            throw Exception("Не верный формат ответа,ожидался объект notice")
                        if(jsonGet.getJSONObject("notice").has("data")){
                            val data = jsonGet.getJSONObject("notice").getJSONArray("data")
                            for (i in 0 until data.length()){
                                val item = data.getJSONObject(i)
                                app.cardList.add(
                                    CardsUser(
                                        item.getString("Cards"),
                                        item.getString("balance")
                                    )
                                )
                            }
                            firstView.adapter?.notifyDataSetChanged()
                        }else{
                            throw Exception("не верный формат ответа")
                        }

                    }
                    catch (e:java.lang.Exception){
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
    fun getUsers(){
        HTTP.requestGET(
            "http://192.168.0.3:8080/User",
            null
        ){ result, error ->
            runOnUiThread {
                if(result != null){
                    try {
                            app.userList.clear()
                            val json = JSONObject(result)
                        if(!json.has("notice"))
                            throw Exception("Не верный формат ответа, ожидался объект notice")
                        if(json.getJSONObject("notice").has("data")){
                            val data = json.getJSONObject("notice").getJSONArray("data")
                                for (i in 0 until  data.length()){
                                    val item = data.getJSONObject(i)
                                    app.userList.add(
                                        Users(
                                            item.getString("Name")
                                        )
                                    )
                                }
                            secView.adapter?.notifyDataSetChanged()
                        }
                        else{
                            throw Exception("Не верный формат ответа")
                        }
                    }
                    catch (e:java.lang.Exception){
                        AlertDialog.Builder(this)
                            .setTitle("Ошибка")
                            .setMessage(e.message)
                            .setPositiveButton("ОК",null)
                            .create()
                            .show()
                    }
                }
                else{
                    AlertDialog.Builder(this)
                        .setTitle("Ошибка http-запроса")
                        .setMessage(error)
                        .setPositiveButton("ОК",null)
                        .create()
                        .show()
                }
            }
        }
    }
    fun getCredits(){
        HTTP.requestGET("http://192.168.0.3:8080/Credit",
        null
        ){result, error ->
            runOnUiThread {
                if(result!=null){
                    try{
                        app.creditList.clear()
                        val json = JSONObject(result)
                        if(!json.has("notice"))
                            throw Exception("Не верный формат ответа, ожидался объект notice")
                        if(json.getJSONObject("notice").has("data")){
                            val data = json.getJSONObject("notice").getJSONArray("data")
                            for(i in 0 until data.length()){
                                val item = data.getJSONObject(i)
                                app.creditList.add(
                                    Credits(
                                        item.getString("Type"),
                                        item.getString("Payment"),
                                        item.getString("Price")
                                    )
                                )
                            }
                            creditView.adapter?.notifyDataSetChanged()

                        }
                        else{
                            throw Exception("Не верный формат ответа")
                        }
                    }
                    catch (e:java.lang.Exception){
                        AlertDialog.Builder(this)
                            .setTitle("Ошибка")
                            .setMessage(e.message)
                            .setPositiveButton("OK",null)
                            .create()
                            .show()
                    }


                }
                else{
                    AlertDialog.Builder(this)
                        .setTitle("Ошибка")
                        .setMessage(error)
                        .setPositiveButton("OK",null)
                        .create()
                        .show()
                }
            }
        }
    }
}