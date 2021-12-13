package ru.yotc.worldskills

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception
import java.util.ArrayList

/*
Класс адаптера наследуется от RecyclerView.Adapter с указанием класса,
который будет хранить ссылки на виджеты элемента списка, т.е. класса, реализующего ViewHolder.
В нашем случае класс объявлен внутри класса адаптера.
В параметры основного конструктора передаем список c данными о погоде и указатель на активити главного окна
дело в том, что runOnUiThread работает только в контексте активити
Использование:
в КЛАССЕ активности объявляем переменные
private lateinit var someRecyclerView: RecyclerView
private val someClassList = ArrayList<SomeClass>()
в КОНСТРУКТОРЕ инициализируем:
someRecyclerView = findViewById(R.id.someRecyclerView)
// назначаем менеджер разметки
someRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
// создаем адаптер
val someClassAdapter = WeatherAdapter(someClassList, this)
// при клике на элемент списка показать подробную информацию (сделайте сами)
someClassAdapter.setItemClickListener { weather ->
    Log.d("KEILOG", "Click on Weather item")
}
someRecyclerView.adapter = weatherAdapter
разбор JSONObject
// перед заполнением очищаем список
someClassList.clear()
val json = JSONObject(result)
val list = json.getJSONArray("list")
// перебираем json массив
for(i in 0 until list.length()){
    val item = list.getJSONObject(i)
    ...
*/
class BankAdapter(
    private val values: ArrayList<Banks>,
    private val activity: Activity
): RecyclerView.Adapter<BankAdapter.ViewHolder>(){

    // обработчик клика по элементу списка (лямбда выражение), может быть не задан
    private var itemClickListener: ((Banks) -> Unit)? = null

    fun setItemClickListener(itemClickListener: (Banks) -> Unit) {
        this.itemClickListener = itemClickListener
    }

    // Метод onCreateViewHolder вызывается при создании визуального элемента
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // грузим layout, который содержит вёрстку элемента списка (нарисуйте сами)
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.banks_item,
                parent,
                false)

        // создаем на его основе ViewHolder
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = values.size

    // заполняет визуальный элемент данными
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.AdressView.text = values[position].Adress
        holder.TimeView.text = values[position].TimeWork
        if (values[position].isWork()){
            holder.isWork.text = "Работает"
            holder.isWork.setTextColor(Color.parseColor("#00ff00"))
        }
        else{
            holder.isWork.text = "Не работает"
            holder.isWork.setTextColor(Color.parseColor("#ff0000"))
        }

        // onIconLoad.invoke(holder.iconImageView, values[position].weatherIcon)

        holder.container.setOnClickListener {
            //кликнули на элемент списка
            itemClickListener?.invoke(values[position])
        }

    }

    //Реализация класса ViewHolder, хранящего ссылки на виджеты.
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var AdressView: TextView = itemView.findViewById(R.id.Adress)
        var TimeView: TextView = itemView.findViewById(R.id.Time)
        var container: LinearLayout = itemView.findViewById(R.id.container)
        var isWork: TextView = itemView.findViewById(R.id.isWork)

    }
}