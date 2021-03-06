package com.example.y.fusencalendar

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import kotlinx.android.synthetic.main.one_fusen.view.*

class FusenRecyclerViewAdapter(
    private val collection: OrderedRealmCollection<Fusen>
    ): RealmRecyclerViewAdapter<Fusen, FusenRecyclerViewAdapter.FusenViewHolder>(collection, true) {


    class FusenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val fusenBackground: ConstraintLayout = itemView.fusenBackground
        val fusenTitleText: TextView = itemView.fusenTitleText
        val fusenMemoText: TextView = itemView.fusenMemoText
    }


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): FusenViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_fusen, parent, false)
        return FusenViewHolder(view)
    }


    override fun getItemCount(): Int {
        return collection.size
    }


    override fun onBindViewHolder(holder: FusenViewHolder, position: Int) {

        //レコードを取得
        val fusen = collection[position]

        //タイトルとメモの文字列をセット
        holder.fusenTitleText.text = fusen?.title.toString()
        holder.fusenMemoText.text = fusen?.memo.toString()

        //もしタイトルが空なら、fusenTitleTextは非表示
        if(fusen?.title.isNullOrEmpty()){
            holder.fusenTitleText.visibility = View.GONE
        }

        //colorIdに応じて、カードの色を設定
        when(fusen.colorId){
            0 -> {
                holder.fusenBackground.setBackgroundResource(R.drawable.background_card_blue)
            }
            1 -> {
                holder.fusenBackground.setBackgroundResource(R.drawable.background_card_green)
            }
            2 -> {
                holder.fusenBackground.setBackgroundResource(R.drawable.background_card_orange)
            }
            3 -> {
                holder.fusenBackground.setBackgroundResource(R.drawable.background_card_red)
            }
            4 -> {
                holder.fusenBackground.setBackgroundResource(R.drawable.background_card_purple)
            }
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, EditFusenActivity::class.java)
            intent.putExtra("id", fusen?.id)
            Log.d("hello", "id: ${fusen?.id}")
            it.context.startActivity(intent)
        }
    }

}