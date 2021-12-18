package ru.yotc.worldskills

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class User_Activity : AppCompatActivity() {
    private lateinit var firstView: RecyclerView
    private lateinit var app:Myapp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        firstView = findViewById(R.id.firstView)
        app = applicationContext as Myapp

    }
}