package ru.yotc.worldskills

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var Bank: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Bank = findViewById(R.id.Bank)

    }

    fun BanksClick(view: View) {
        startActivity(Intent(this,BankActivity::class.java))
    }
    fun KursValue(view: View) {
        startActivity(Intent(this,ValutesActivity::class.java))
    }
    fun Auth(view: View) {}
}