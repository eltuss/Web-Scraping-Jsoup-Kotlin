package com.example.webscrapingjsoup

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.io.IOException

class Splash : AppCompatActivity() {

    private var loader: AsyncTask<Void, Void, ArrayList<New>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loader = LoadInitNews(this)
        loader!!.execute()

    }

    //Precisa desse var como parametro para abrir a outra tela lá em baixo
    internal class LoadInitNews(var activity: AppCompatActivity?): AsyncTask<Void, Void, ArrayList<New>>(){

        private var news: ArrayList<New> = ArrayList()

        override fun doInBackground(vararg p0: Void?): ArrayList<New> {
            try {
                val url = "https://antigo.saude.gov.br/fakenews/"

                val doc: org.jsoup.nodes.Document = Jsoup.connect(url).get()
                //Pega a imagem dentro da div
                val div: Elements = doc.select("div.tileItem")
                //Pega o titulo dentro do H2
                val tagHeading: Elements = doc.select("h2.tileHeadline")

                val size: Int = div.size
                for (index in 0..size){
                    //Pega o link da imagem dentro da tag "img" com o atributo src
                    val imgUrl: String = div.select("img").eq(index).attr("src")
                    //Pega o texto dentro da tag "a"
                    val title: String = tagHeading.select("a").eq(index).text()
                    //Pega os detalhes da noticia dentro da tag "a" com o atributo "href"
                    val details: String = tagHeading.select("a").attr("href")

                    Log.i("Result", "$imgUrl $title $details")
                    news.add(New("https://antigo.saude.gov.br$imgUrl", title, details))
                }

            }catch (e: IOException){
                e.printStackTrace()
            }
            return  news
        }

        override fun onPostExecute(result: ArrayList<New>?) {
            val intent = Intent(activity, MainActivity::class.java)
            activity!!.startActivity(intent)
            activity!!.finish()

        }
    }

}