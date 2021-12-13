package com.example.webscrapingjsoup

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException

class LoadDetailsNews(activity: AppCompatActivity, var urlDetails: String):
    AsyncTask<Void, Void, ArrayList<String>>() {

    private var details: ArrayList<String>? = ArrayList()

    override fun doInBackground(vararg p0: Void?): ArrayList<String> {
        try {
            val baseUrl = "https://jovemnerd.com.br/nerdbunker/"
            val url = baseUrl+urlDetails

            val doc: Document = Jsoup.connect(url).get()
            val div: Elements = doc.select("div.content-left")
            div.select("div").remove()
            //Aqui ele pega apenas o texto dentro das tags "p" e "li"
            val textComplete = div.select("p, ul > li")

            for (element in textComplete){
                if (element.text() != "")
                    details!!.add(element.text())
            }

        }catch (e: IOException){
            e.printStackTrace()
        }
        return details!!
    }
}