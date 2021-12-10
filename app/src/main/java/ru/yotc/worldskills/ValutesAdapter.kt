package ru.yotc.worldskills
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
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
class ValutesAdapter(
        private val values: ArrayList<Valutes>,
        private val activity: Activity
): RecyclerView.Adapter<ValutesAdapter.ViewHolder>(){

    // обработчик клика по элементу списка (лямбда выражение), может быть не задан
    private var itemClickListener: ((Valutes) -> Unit)? = null

    fun setItemClickListener(itemClickListener: (Valutes) -> Unit) {
        this.itemClickListener = itemClickListener
    }

    // Метод onCreateViewHolder вызывается при создании визуального элемента
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // грузим layout, который содержит вёрстку элемента списка (нарисуйте сами)
        val itemView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.valutes_item,
                        parent,
                        false)

        // создаем на его основе ViewHolder
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = values.size

    // заполняет визуальный элемент данными
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.valute.text = values[position].charCode
        holder.names.text = values[position].name

        // onIconLoad.invoke(holder.iconImageView, values[position].weatherIcon)



        /*HTTP.getImage("https://openweathermap.org/img/w/${values[position].weatherIcon}.png") { bitmap, error ->
            if (bitmap != null) {
                activity.runOnUiThread {
                    try {
                        holder.iconImageView.setImageBitmap(bitmap)
                    } catch (e: Exception) {

                    }
                }
            } else
                Log.d("KEILOG", error)
        }
        */

    }

    //Реализация класса ViewHolder, хранящего ссылки на виджеты.
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var iconImage: ImageView = itemView.findViewById(R.id.icon)
        var valute:TextView = itemView.findViewById(R.id.valut)
        var names:TextView = itemView.findViewById(R.id.name)
        var upPrice:TextView = itemView.findViewById(R.id.upPrice)
        var downPrice:TextView = itemView.findViewById(R.id.downPrice)

    }
}