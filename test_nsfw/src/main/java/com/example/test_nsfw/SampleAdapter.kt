package com.example.test_nsfw

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 *  @ProjectName:   Practise
 *      @Package:   com.example.test_nsfw
 *    @ClassName:   SampleAdapter
 *  @Description:   java类作用描述
 *       @Author:   毛毛虫
 *   @CreateDate:   2022/1/18 5:26 下午
 *   @UpdateUser:   更新者
 *   @UpdateDate:   2022/1/18 5:26 下午
 * @UpdateRemark:   更新说明
 *      @Version:   1.0
 */
class SampleAdapter : RecyclerView.Adapter<SampleAdapter.Holder>() {
    private val data = arrayListOf<NsfwModel>()

    fun setData(list: List<NsfwModel>) {
        data.clear()
        data.addAll(list)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView
        val tv1: TextView
        val tv2: TextView

        init {
            img = itemView.findViewById(R.id.item_iv)
            tv1 = itemView.findViewById(R.id.item_tv1)
            tv2 = itemView.findViewById(R.id.item_tv2)
        }

        @SuppressLint("ResourceType")
        fun init(model: NsfwModel) {
            model.run {
                img.setImageResource(path)
                nsfw?.run { tv1.text = "nsfw:$nsfw" }
                sfw?.run { tv2.text = "sfw:$sfw" }

            }
        }
    }

    var itemWidth = 300;


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false).apply {
                val lp = layoutParams
                lp.width = itemWidth
                lp.height = itemWidth
                layoutParams = lp
            }
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.init(data.get(position))
    }

    override fun getItemCount(): Int = data.size
}