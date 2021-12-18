package ru.yotc.worldskills

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList
class CardsAdapter(
    private val values: ArrayList<CardsUser>,
    private val activity: Activity
): RecyclerView.Adapter<CardsAdapter.ViewHolder>(){

    // обработчик клика по элементу списка (лямбда выражение), может быть не задан
    private var itemClickListener: ((CardsUser) -> Unit)? = null

    fun setItemClickListener(itemClickListener: (CardsUser) -> Unit) {
        this.itemClickListener = itemClickListener
    }

    // Метод onCreateViewHolder вызывается при создании визуального элемента
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // грузим layout, который содержит вёрстку элемента списка (нарисуйте сами)
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.cards_item,
                parent,
                false)

        // создаем на его основе ViewHolder
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = values.size

    // заполняет визуальный элемент данными
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.cardsInfo.text = values[position].Cards
        holder.balanceInfo.text = values[position].Balance.toString()
        // onIconLoad.invoke(holder.iconImageView, values[position].weatherIcon)




    }

    //Реализация класса ViewHolder, хранящего ссылки на виджеты.
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var cardsInfo:TextView = itemView.findViewById(R.id.cardsInfo)
        val balanceInfo:TextView = itemView.findViewById(R.id.balanceInfo)
        var container: LinearLayout = itemView.findViewById(R.id.container)
    }
}

