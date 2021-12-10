package ru.yotc.worldskills

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ValutesActivity : AppCompatActivity() {
    private val valutesList = ArrayList<Valutes>()
    private lateinit var date:TextView
    private lateinit var dates:LocalDateTime
    private lateinit var valutesRecyclerView: RecyclerView
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_valutes)
        date = findViewById(R.id.date)
        dates = LocalDateTime.now()

        date.text = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(dates)
        valutesRecyclerView = findViewById(R.id.valutesRecyclerView)
        HTTP.requestGET("http://www.cbr.ru/scripts/XML_daily.asp?date_req=02/03/2002 ",
        null
            ){result, error ->

            runOnUiThread {
                if(result!=null)
                    try{
                        val re = """<CharCode>(\w{2}.*?)</CharCode>.*?<Nominal>(.*?)</Nominal>.*?<Value>(.*?)</Value>""".toRegex(
                            RegexOption.DOT_MATCHES_ALL)
                        val seq = re.findAll(result)
                        seq.forEach {
                            Log.d("KEILOG", it.groupValues[1]+" "+it.groupValues[2]+" "+it.groupValues[3] + " " +it.groupValues[4])
                        }
                        valutesRecyclerView.adapter?.notifyDataSetChanged()

                    }
                    catch (e:Exception){
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