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
import io.realm.Realm
import io.realm.RealmRecyclerViewAdapter
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.one_fusen.view.*

class FusenRecyclerViewAdapter(
    private val collection: OrderedRealmCollection<Fusen>
    ): RealmRecyclerViewAdapter<Fusen, FusenRecyclerViewAdapter.FusenViewHolder>(collection, true) {


    class FusenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val fusenBackground: ConstraintLayout = itemView.eventBackground
        val fusenTitleText: TextView = itemView.eventTitleText
        val fusenMemoText: TextView = itemView.eventMemoText
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

        holder.itemView.eventTitleText.text = fusen?.title.toString()
        holder.itemView.eventMemoText.text = fusen?.memo.toString()
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, EditFusenActivity::class.java)
            intent.putExtra("id", fusen?.id)
            Log.d("hello", "id: ${fusen?.id}")
            it.context.startActivity(intent)
        }
    }

}