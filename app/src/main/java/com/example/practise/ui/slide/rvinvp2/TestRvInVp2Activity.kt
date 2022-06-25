package com.example.practise.ui.slide.rvinvp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.practise.R
import com.example.base.utils.VUiKit
import com.google.android.material.tabs.TabLayout
import java.util.*

class TestRvInVp2Activity : AppCompatActivity() {
    lateinit var adapter: HashtagAdapter
    private val tabNameList = arrayListOf<String>()
    lateinit var rv: RecyclerView

    fun log(content: String) {
        Log.d(TestRvInVp2Activity::class.java.simpleName, content)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_rv_in_vp2)

        VUiKit.defer().`when`({
            TagsParser.parseUrl()   //解析数据
        })

        tabNameList.addAll(
            Arrays.asList(
                getString(R.string.today),
                getString(R.string.last7day),
                getString(R.string.alltime)
            )
        )

        initVp2()
    }


    /**
     * 初始化ViewPager2
     */
    fun initVp2() {

        //ViewHolder
        class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val iv: ImageView
            val rv: RecyclerView
            val tabLayout: TabLayout


            init {
                iv = itemView.findViewById(R.id.item_iv)
                rv = itemView.findViewById(R.id.item_rv)
                tabLayout = itemView.findViewById(R.id.item_tb)
            }

            fun init(position: Int) {
                iv.visibility = if (position != 1) View.VISIBLE else View.GONE
                rv.visibility = if (position != 1) View.GONE else View.VISIBLE
                tabLayout.visibility = if (position != 1) View.GONE else View.VISIBLE
                if (position == 1) {
                    initRv()
                    initTb()
                }

            }

            /**
             * 初始化tb
             */
            fun initTb() {

                tabNameList.forEach {
                    val tab = tabLayout.newTab()
                    tab.setText(it)
                    tabLayout.addTab(tab)
                }
                tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        if (tab?.text?.equals(tabNameList[0]) ?: false) {
                            adapter.setData(TagsParser.getListToday())
                        } else if (tab?.text?.equals(tabNameList[1]) ?: false) {
                            adapter.setData(TagsParser.getListLast7days())
                        } else if (tab?.text?.equals(tabNameList[2]) ?: false) {
                            adapter.setData(TagsParser.getListAlltime())
                        }
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {
                    }

                    override fun onTabReselected(tab: TabLayout.Tab?) {
                    }
                })
            }

            /**
             * 初始化ViewPager2子项RecyclerView
             */
            fun initRv() {
                adapter = HashtagAdapter()
                rv.layoutManager = LinearLayoutManager(this@TestRvInVp2Activity)
                rv.adapter = adapter
                val data = TagsParser.getListToday()
                log("data.size:" + data.size)
                adapter.setData(data)

                //处理rv左右滑动切换数据源事件
                var startX = 0f //滑动起始坐标
                var startY = 0f
                rv.setOnTouchListener { v, event ->
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            startX = event.getX()
                            startY = event.getY()
                        }
                        MotionEvent.ACTION_MOVE -> {
                            val endX = event.getX()
                            val endY = event.getY()
                            val disX = Math.abs(endX - startX)
                            val disY = Math.abs(endY - startY)
                            if (disX > disY) {
                                var result = if (position == 1) {
                                    if (startX < endX) scrollToLeft() else scrollToRight()
                                } else {
                                    false
                                }

                                if (false)
                                    v.parent.requestDisallowInterceptTouchEvent(
                                        v.canScrollHorizontally(
                                            (startX - endX).toInt()
                                        )
                                    )
                            } else {
                                v.parent.requestDisallowInterceptTouchEvent(
                                    v.canScrollVertically(
                                        (startY - endY).toInt()
                                    )
                                )
                            }
                        }
                        MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                            v.parent.requestDisallowInterceptTouchEvent(false)
                        }
                    }
                    return@setOnTouchListener false
                }
            }

            /**
             * 滑动到下一个标签
             * 是否消费了该事件 true:已经消费 false:未消费
             */
            fun scrollToRight(): Boolean {
                val currentPosition = tabLayout.selectedTabPosition
                var result = false
                if (currentPosition < tabNameList.size - 1) {
                    tabLayout.getTabAt(currentPosition + 1)?.select()
//                    scrollT = System.currentTimeMillis()
                    result = true
                }
                return result
            }

            fun scrollToLeft(): Boolean {
                val currentPosition = tabLayout.selectedTabPosition
                var result = false
                if (currentPosition > 0) {
                    tabLayout.getTabAt(currentPosition - 1)?.select()
//                    scrollT = System.currentTimeMillis()
                    result = true
                }
                return result
            }
        }

        //vp适配器
        val vpAdapter = object : RecyclerView.Adapter<Holder>() {

            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): Holder {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.vp2_item, parent, false)
                return Holder(view)
            }

            override fun onBindViewHolder(holder: Holder, position: Int) {
                holder.init(position)
            }

            override fun getItemCount(): Int = 3
        }
        findViewById<ViewPager2>(R.id.vp).adapter = vpAdapter
    }


}