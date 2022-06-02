package com.example.address_book.model

import org.litepal.LitePal
import org.litepal.crud.LitePalSupport
import java.io.Serializable

/**
 *  @ProjectName:   Practise
 *      @Package:   com.example.address_book.model
 *    @ClassName:   AdressModel
 *  @Description:   java类作用描述
 *       @Author:   毛毛虫
 *   @CreateDate:   2022/5/31 3:50 下午
 *   @UpdateUser:   更新者
 *   @UpdateDate:   2022/5/31 3:50 下午
 * @UpdateRemark:   更新说明
 *      @Version:   1.0
 */
class AdressBookModel() : LitePalSupport(), Serializable {
    var id: Long = 0L
    var name: String = ""              //名字
    var phoneNumber: String = ""       //号码
    var adress: String = ""            //地址
    var notes: String = ""             //备注
    var tag: String = ""               //标签，用于排序

    constructor(
        name: String,
        phoneNumber: String,
        adress: String?=null,
        notes: String?=null,
        tag: String?=null
    ) : this() {
        this.name = name
        this.phoneNumber = phoneNumber
        adress?.let {
            this.adress = it
        }
        notes?.let {
            this.notes = it
        }
        tag?.let {
            this.tag = it
        }
    }
}