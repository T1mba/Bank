package ru.yotc.worldskills

import android.app.Application

class Myapp:Application() {
    lateinit var username:String
    lateinit var token:String
    val cardList = ArrayList<CardsUser>()
    var valuteList = ArrayList<Valutes>()
     var bankList = ArrayList<Banks>()

}