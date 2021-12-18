package ru.yotc.worldskills

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CreditsAdapter(
    private val values: ArrayList<Credits>,
    private val activity: Activity
): RecyclerView.Adapter<CreditsAdapter.ViewHolder>(){

    // обработчик клика по элементу списка (лямбда выражение), может быть не задан
    private var itemClickListener: ((Credits) -> Unit)? = null

    fun setItemClickListener(itemClickListener: (Credits) -> Unit) {
        this.itemClickListener = itemClickListener
    }

    // Метод onCreateViewHolder вызывается при создании визуального элемента
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // грузим layout, который содержит вёрстку элемента списка (нарисуйте сами)
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.credits_item,
                parent,
                false)

        // создаем на его основе ViewHolder
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = values.size

    // заполняет визуальный элемент данными
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.typeCredit.text = values[position].Type
        holder.date.text = values[position].Payment
        holder.price.text = values[position].Price

    }

    //Реализация класса ViewHolder, хранящего ссылки на виджеты.
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val typeCredit:TextView = itemView.findViewById(R.id.typeCredit)
        val date:TextView = itemView.findViewById(R.id.dateCredit)
        val price:TextView = itemView.findViewById(R.id.Price)

    }
}
