package com.terasoltechnologies.recyclerviewanimation

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item.view.*

class CarouseAdapter(
    val itemList: ArrayList<String>,
    val context: Context,
    val initItem: Int,
    val scrollerInterface: ScrollerInterface
) : RecyclerView.Adapter<CarouselViewHolder>() {

    companion object {

        lateinit var instance: MainActivity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        return CarouselViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item,
                parent,
                false
            )
        )

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemViewType(position: Int): Int {
        if (itemList[position] == "0")
            return 1
        else
            return 0
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {

        holder.textView.text = itemList[position]

        if (holder.itemViewType == 0) {
            holder.cardItem.setBackgroundColor(Color.parseColor("#000000"))
        }

        holder.card.setOnClickListener {

            instance.NEWSELECTION = position
            // instance.NEWX = it.scrollX
            // instance.NEWY = it.scrollY
            instance.NEWWIDTH = it.width
            scrollerInterface.scrollToNewPosition()
            scrollerInterface.getPositionItem(itemList[position])
        }

        /*if (initItem == position) {
            instance.NEWWIDTH = holder.card.width
        }*/

    }
}

class CarouselViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    var card: LinearLayout = view.card
    var cardItem: LinearLayout = view.carditem
    var textView: TextView = view.text


}
