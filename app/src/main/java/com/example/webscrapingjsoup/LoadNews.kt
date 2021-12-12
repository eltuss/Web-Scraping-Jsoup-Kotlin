package com.example.webscrapingjsoup

import android.os.AsyncTask
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.io.IOException

class LoadNews(var activity: AppCompatActivity?, var page: String) :
    AsyncTask<Void, Void, ArrayList<New>>() {

    private var news: ArrayList<New> = ArrayList()
    private var loadedData = activity as IJsoupData

    override fun doInBackground(vararg p0: Void?): ArrayList<New> {
        try {
            val url = "https://jovemnerd.com.br/nerdbunker/"

            val doc: org.jsoup.nodes.Document = Jsoup.connect(url).get()
            //Pega a imagem dentro da div
            val div: Elements = doc.select("a.image")
            //Pega o titulo dentro do H2
            val tagHeading: Elements = doc.select("h2.title")

            //Log.i("Result TOP", "$div $tagHeading ")

            val size: Int = div.size
            for (index in 0..size) {
                //Pega o link da imagem dentro da tag "img" com o atributo src
                val imgUrl: String = div.select("img").eq(index).attr("src")
                //Pega o texto dentro da tag "a"
                val title: String = tagHeading.select("a").eq(index).text()
                //Pega os detalhes da noticia dentro da tag "a" com o atributo "href"
                val details: String = tagHeading.select("a").attr("href")

                //Log.i("Result", "$imgUrl $title $details")
                news.add(New(imgUrl, title, details))
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }
        return news
    }

    override fun onPostExecute(result: ArrayList<New>?) {
            loadedData.getWebData(result!!)

    }

}