package ru.yotc.worldskills

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

class Banks(
    val Adress:String,
    val TimeWork: String,

)
 {
     @RequiresApi(Build.VERSION_CODES.O)
     fun isWork(): Boolean{
         val currentTime = LocalDateTime.now()
         val re = Regex("""(\d{2}):(\d{2})-(\d{2}):(\d{2})""")
         val math = re.find(TimeWork)
         if(math != null){
             val fromHour = math.groupValues[1].toInt()
             val toHour = math.groupValues[3].toInt()
             return currentTime.hour>=fromHour && currentTime.hour<=toHour
         }
         return false
     }
 }
