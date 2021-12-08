package ru.yotc.worldskills

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject
import java.lang.Exception

class BankActivity : AppCompatActivity() {
    private lateinit var banksRecyclerView: RecyclerView
    private  val bankList = ArrayList<Banks>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank)
        banksRecyclerView = findViewById(R.id.Banks)
        banksRecyclerView.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        val bankAdapder = BankAdapter(bankList,this)
            banksRecyclerView.adapter = bankAdapder
        HTTP.requestGET( "http://192.168.0.182:8080/bankomats",
            null
        )
        { result, error ->
            runOnUiThread {
                if (result != null)
                    try {
                        bankList.clear()
                        val json = JSONObject(result)
                        if (!json.has("notice"))
                            throw Exception("Не верный формат ответа, ожидался объект notice")
                        if (json.getJSONObject("notice").has("data")) {
                            val data = json.getJSONObject("notice").getJSONArray("data")
                            for (i in 0 until data.length()) {
                                val item = data.getJSONObject(i)
                                bankList.add(
                                    Banks(
                                        item.getString("address"),
                                        item.getString("timework")
                                    )
                                )
                            }

                            banksRecyclerView.adapter?.notifyDataSetChanged()

                        } else {
                            throw Exception("Не верный формат ответа")
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

}