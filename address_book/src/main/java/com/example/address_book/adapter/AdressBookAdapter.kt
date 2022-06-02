package com.example.address_book.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.address_book.R
import com.example.address_book.model.AdressBookModel

/**
 *  @ProjectName:   Practise
 *      @Package:   com.example.address_book.adapter
 *    @ClassName:   AdressBookAdapter
 *  @Description:   java类作用描述
 *       @Author:   毛毛虫
 *   @CreateDate:   2022/5/31 4:13 下午
 *   @UpdateUser:   更新者
 *   @UpdateDate:   2022/5/31 4:13 下午
 * @UpdateRemark:   更新说明
 *      @Version:   1.0
 */
class AdressBookAdapter : RecyclerView.Adapter<AdressBookAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView
            get() = itemView.findViewById(R.id.item_adress_tv_name)

        private val phoneNumber: TextView
            get() = itemView.findViewById(R.id.item_adress_tv_phone_number)

        fun init(model: AdressBookModel) {
            name.text = model.name
            phoneNumber.text = model.phoneNumber
            itemView.setOnClickListener {
                clickItem(model)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item_adress_book, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.init(data.get(position))
    }

    override fun getItemCount(): Int = data.size

    /**
     * data
     */
    private val data = arrayListOf<AdressBookModel>()

    fun setData(list: List<AdressBookModel>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    /**
     * Listener
     */
    var clickItem: (AdressBookModel) -> Unit = {}

}