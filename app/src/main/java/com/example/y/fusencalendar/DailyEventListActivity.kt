package com.example.y.fusencalendar

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_daily_event_list.*
import kotlinx.android.synthetic.main.fragment_fusen_list.recyclerView

class DailyEventListActivity : AppCompatActivity() {


    //Realmのインスタンスを取得
    var realm: Realm = Realm.getDefaultInstance()

    //年月日を格納する変数
    private var year = 0
    private var month = 0
    private var day = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_event_list)

        //CalendarRecyclerViewAdapterから日付情報を受け取る
        year = intent.getIntExtra("year", -1)
        month = intent.getIntExtra("month", -1)
        day = intent.getIntExtra("day", -1)

        //labelTextに、表示日をセット 例: "2021年 6月 16日"
        val dateString = year.toString() + "年 " + month.toString() + "月 " + day.toString() + "日"
        labelText.text = dateString

        //backButtonを押すとアクティビティ終了
        backButton.setOnClickListener {
            finish()
        }

        //floatingButtonを押すと、新規イベント作成のためにEditEventActivityへ遷移する
        floatingButton.setOnClickListener {
            val intent = Intent(this, EditEventActivity::class.java)
            intent.putExtra("currentYear", year)
            intent.putExtra("currentMonth", month)
            intent.putExtra("currentDay", day)
            startActivity(intent)
        }

    }


    override fun onStart() {
        super.onStart()

        //日付が一致するレコードを検索
        val realmResults = realm.where<Event>()
            .equalTo("year", year)
            .and()
            .equalTo("month", month)
            .and()
            .equalTo("date", day)
            .findAll()
            .sort("hour", Sort.ASCENDING, "minute", Sort.ASCENDING)

        //もしレコード数が0なら、画面にメッセージを表示
        if(realmResults.size == 0){
            noResultMessageText.visibility = View.VISIBLE
        }else{
            noResultMessageText.visibility = View.INVISIBLE
        }

        //recyclerViewを表示
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = EventRecyclerViewAdapter(realmResults)
    }


}