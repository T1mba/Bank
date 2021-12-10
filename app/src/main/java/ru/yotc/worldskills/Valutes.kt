package ru.yotc.worldskills

 class Valutes(
        val numCode:Int,
        val charCode: String,
        val nominal: Int,
        val name: String,
        val value: Double,
        var sum: Double
)
 {
         fun formula():Double{
             sum =  value+(nominal*value)
                 return sum
         }
 }

