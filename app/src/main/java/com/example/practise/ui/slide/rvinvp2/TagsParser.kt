package com.example.practise.ui.slide.rvinvp2

import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.select.Elements

/**
 *  @ProjectName:   InsSaver
 *      @Package:   com.instore.videodownloaderforinstagram.inssaver.insapp.utils
 *    @ClassName:   TagsParser
 *  @Description:   标签解析类
 *       @Author:   毛毛虫
 *   @CreateDate:   2022/2/22 10:24 上午
 *   @UpdateUser:   更新者
 *   @UpdateDate:   2022/2/22 10:24 上午
 * @UpdateRemark:   更新说明
 *      @Version:   1.0
 */
object TagsParser {

    val url = "https://inflact.com/tools/instagram-top-hashtags/";
    val list_today = arrayListOf<TagModel>()
    val list_last7days = arrayListOf<TagModel>()
    val list_alltime = arrayListOf<TagModel>()
    fun log(content: String) {
        Log.d(TagsParser::class.java.simpleName, content)
    }

    fun parseUrl() {
        //获得document
        val doc = Jsoup.connect(url)
            //设置解析时浏览器的agent以获得相同形式的数据
            .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36")
            .get();

        //开始解析，获得hashtags元素列表容器
        val app = doc.body().getElementsByClass("app")
        if (app.isEmpty()) return
        val mainHashtag = app[0].getElementsByClass("main hashtag");
        if (mainHashtag.isEmpty()) return
        val containerFluid = mainHashtag[0].getElementsByClass("container-fluid")
        if (containerFluid.isEmpty()) return
        val hashtags = containerFluid[0].getElementsByClass("hashtags")
        if (hashtags.isEmpty()) return

        //解析hashtags，获得列表内容
        val row = hashtags[0].getElementsByClass("row")
        if (row.isEmpty()) return
        val col12 = row[0].getElementsByClass("col-12 col-lg-8")
        if (col12.isEmpty()) return
        val tab_content = col12[0].getElementsByClass("tab-content reset-cp-sm position-relative")
        if (tab_content.isEmpty()) return

        //当天
        var today: Elements? = null
        val todayList = tab_content[0].getElementById("today").getElementsByClass("hashtags-list")
        if (!todayList.isEmpty())
            today = todayList[0].getElementsByTag("label")

        //过去7天
        var last7days: Elements? = null
        val last7daysList = tab_content[0].getElementById("last7days")
            .getElementsByClass("hashtags-list")
        if (!last7daysList.isEmpty())
            last7days = last7daysList[0].getElementsByTag("label")

        //所有时间
        var alltime: Elements? = null
        val alltimeList = tab_content[0].getElementById("alltime")
            .getElementsByClass("hashtags-list")
        if (!alltimeList.isEmpty())
            alltime = alltimeList[0].getElementsByTag("label")

        list_today.run {
            clear()
            addAll(parseTags(today))
        }
        list_last7days.run {
            clear()
            addAll(parseTags(last7days))
        }
        list_alltime.run {
            clear()
            addAll(parseTags(alltime))
        }

    }

    fun parseTags(nodes: Elements?): List<TagModel> {
        nodes ?: let { return emptyList() }
        if (nodes.isEmpty()) return emptyList()
        val list = arrayListOf<TagModel>()

        //逐条解析数据
        nodes.forEach {
            //标签索引
            val number = list.size

            //标签内容
            var content = ""
            val itemHash = it.getElementsByClass("item-hash")
            if (itemHash.isEmpty()) return@forEach
            val a = itemHash[0].getElementsByTag("a")
            if (a.isEmpty()) return@forEach
            content = a[0].text()


            //标签热度和单位
            val itemInfo = it.getElementsByClass("item-info")
            if (itemInfo.isEmpty()) return@forEach
            val info = itemInfo[0].text().trim()
            if (info.equals("0")) return@forEach
            val unit = info.get(info.length - 1)
            val sum = info.replace(unit, ' ').trim().toFloat()
            if (sum == 0f) return@forEach //相当于continue
            log("$number : $content : ${sum} : $unit")
            TagModel(number, content, sum, unit).let {
                log(it.toString())
                list.add(it)
            }
        }
        return list
    }

    fun getListToday(): List<TagModel> = list_today

    fun getListLast7days(): List<TagModel> = list_last7days

    fun getListAlltime(): List<TagModel> = list_alltime
}