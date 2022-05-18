package com.example.test_nsfw

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.IntegerRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.devzwy.nsfw.NSFWHelper
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var rv:RecyclerView
    val drawableList= arrayListOf<@IntegerRes Int>()
    val data= arrayListOf<NsfwModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv=findViewById(R.id.rv)
        val width=rv.width/2
        val adapter=SampleAdapter()
        adapter.itemWidth=width

        rv.layoutManager=GridLayoutManager(this,2)
        rv.adapter=adapter

        drawableList.addAll(Arrays.asList(R.drawable.beauty_1,R.drawable.beauty_2,R.drawable.beauty_3,R.drawable.beauty_4,R.drawable.beauty_5))

        for(drawableId in drawableList){
            val bitmap=BitmapFactory.decodeResource(resources,drawableId)
            try {
                NSFWHelper.getNSFWScore(bitmap) {
//                NsfwModel(drawableId,it.nsfwScore,it.sfwScore).apply { data.add(this) }
//                if (drawableList.indexOf(drawableId)==drawableList.size-1){
//                    adapter.setData(data)
//                }
                }
            }catch (e :Exception){
                e.printStackTrace()
            }
        }

    }
}