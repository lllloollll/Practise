package com.example.practise.ui.slide.rvinvp2

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.practise.R
import java.util.*

/**
 *  @ProjectName:   InsSaver
 *      @Package:   com.instore.videodownloaderforinstagram.inssaver.insapp.adapter
 *    @ClassName:   HashtagAdapter
 *  @Description:   java类作用描述
 *       @Author:   毛毛虫
 *   @CreateDate:   2022/2/22 3:23 下午
 *   @UpdateUser:   更新者
 *   @UpdateDate:   2022/2/22 3:23 下午
 * @UpdateRemark:   更新说明
 *      @Version:   1.0
 */
class HashtagAdapter : RecyclerView.Adapter<HashtagAdapter.Holder>() {

    private val data = mutableListOf<TagModel>()
    private lateinit var rv: RecyclerView
    private val checkedList = arrayListOf<String>()
    private val MAX_CHECKED = 30  //最多只能选中30个

    fun log(content: String) {
        Log.d(HashtagAdapter::class.java.simpleName, content)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        rv = recyclerView
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_number: TextView
        val tv_tag: TextView
        val tv_sum: TextView
        val bt_toggle: ToggleButton
        val bg_toggle: FrameLayout

        init {
            tv_number = itemView.findViewById(R.id.item_tv_number)
            tv_tag = itemView.findViewById(R.id.item_tv_hashtag)
            tv_sum = itemView.findViewById(R.id.item_tv_sum)
            bt_toggle = itemView.findViewById(R.id.item_toggle_bt)
            bg_toggle = itemView.findViewById(R.id.item_toggle_bg)
        }

        //初始化
        fun init(info: TagModel) {
            tv_number.text = (info.number + 1).toString()

            tv_tag.text = info.content
            tv_sum.text = "${info.sum.toInt()} ${info.unit}"
            bg_toggle.setOnClickListener { bt_toggle.isChecked = !bt_toggle.isChecked }
            //不一定是人为点击会触发该监听器
            bt_toggle.setOnCheckedChangeListener { buttonView, isChecked ->
                log("checkedSize:${checkedList.size}")
                if (isChecked && checkedList.size >= MAX_CHECKED) {
                    checkedListener(null,true)
                    if (!info.checked)
                        buttonView.isChecked = false
                    return@setOnCheckedChangeListener
                }
                info.checked = isChecked
                data[data.indexOf(info)] = info
                if (isChecked) {
//                    val index = data.indexOf(info)
//                    data.remove(info)
//                    data.add(0, info)
//                    notify { notifyItemMoved(index, 0) }
//                    rv.scrollToPosition(0)
                    if (!checkedList.contains(info.content)) {
                        checkedList.add(info.content)
                        checkedListener(info, true)
                    }
                } else {
//                    val index = data.indexOf(info)
//                    val toIndex = data.checkedIndex(info)
//                    data.remove(info)
//                    data.add(toIndex, info)
//                    notify { notifyItemMoved(index, toIndex) }
                    if (checkedList.contains(info.content)) {
                        checkedList.remove(info.content)
                        checkedListener(info, false)
                    }
                }
//                log(info.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item_hashtag, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val info = data.get(position)
        holder.init(info)
        holder.bt_toggle.isChecked = info.checked
    }

    override fun getItemCount(): Int = data.size

    fun setData(list: List<TagModel>) {
        data.clear()
        data.addAll(list)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            log(
                "checkedList:${checkedList.toString()} data.checkedsize:${
                    data.stream().filter(TagModel::checked).count().toInt()
                }"
            )
        }
        data.forEach {
            if (checkedList.contains(it.content))
                it.checked = true
            else
                it.checked = false
            data[data.indexOf(it)] = it

        }
        notifyDataSetChanged()
    }

    fun notify(action: () -> Unit) {
        if (rv.isComputingLayout) rv.post { action() }
        else action()
    }

    //TagModel数据被打乱之后获取它将要移动的位置
    fun MutableList<TagModel>.checkedIndex(tagModel: TagModel): Int {
        var checkedSum = 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            checkedSum = this.stream().filter(TagModel::checked).count().toInt()
        } else {
            for (info in this) {
                if (info.checked) checkedSum++
            }
        }
//        checkedSum++    //加上自身
        val oriIndex = tagModel.number
        var index = if (oriIndex < checkedSum || oriIndex == 0) checkedSum else oriIndex
        while (index < this.size - 1 && oriIndex > this.get(index + 1).number) index++
        return index
    }

    /**
     * 选中和取消选中监听，与hashtag页面hashtagCopy模块联动
     * 当info为空 而checked为true时，只进行UI响应，显示复制模块
     */
    var checkedListener: (info: TagModel?, checked: Boolean) -> Unit =
        { info: TagModel?, checked: Boolean -> }

    fun clearCheckedTags() {
        checkedList.clear()
        log("checkedList:${checkedList.toString()}")
        data.forEach {
            it.checked = false
            data[data.indexOf(it)] = it
        }
//        Collections.sort(data, kotlin.Comparator { o1, o2 ->
//            if (o1.checked) {
//                o1.checked = false
//            }
//            if (o2.checked) {
//                o2.checked = false
//            }
//            o1.number - o2.number
//        })
        notifyDataSetChanged()
    }

}