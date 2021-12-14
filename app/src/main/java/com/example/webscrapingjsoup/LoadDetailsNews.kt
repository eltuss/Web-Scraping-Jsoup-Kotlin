package com.example.webscrapingjsoup

import android.os.AsyncTask
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException

class LoadDetailsNews(activity: AppCompatActivity, var urlDetails: String):
    AsyncTask<Void, Void, ArrayList<String>>() {

    private var details: ArrayList<String>? = ArrayList()
    private var loader = activity as ILoadDetails

    override fun doInBackground(vararg p0: Void?): ArrayList<String> {
        try {

            val url = urlDetails

            val doc: Document = Jsoup.connect(url).get()
            val div: Elements = doc.select("div.content-left")
            div.select("div").remove()
            //Aqui ele pega apenas o texto dentro das tags "p" e "li"
            val textComplete = div.select("p, h2, ul > li")

            for (element in textComplete){
                if (element.text() != "")
                    details!!.add(element.text())

            }

        }catch (e: IOException){
            e.printStackTrace()
        }
        return details!!
    }

    override fun onPostExecute(result: ArrayList<String>?) {
        loader.getDetails(result!!)
    }
}