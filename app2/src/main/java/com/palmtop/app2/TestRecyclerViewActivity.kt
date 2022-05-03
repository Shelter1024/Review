package com.palmtop.app2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.palmtop.app2.model.Singer
import com.palmtop.app2.view.SingerItemDecoration
import kotlinx.android.synthetic.main.activity_test_recycler_view.*

class TestRecyclerViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_recycler_view)
        initView()
    }

    private fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(SingerItemDecoration())
        recyclerView.adapter = SingerAdapter(getData())
    }

    private fun getData(): List<Singer> {
        val list = ArrayList<Singer>()
        for (i in 0..5) {
            for (j in 0 until 10) {
                if (i % 2 == 0) {
                    val singer = Singer("邓紫棋 $j", "香港歌手")
                    list.add(singer)
                } else {
                    val singer = Singer("周杰伦 $j", "台湾歌手")
                    list.add(singer)
                }
            }
        }
        return list
    }


}