package com.example.webscrapingjsoup

import android.os.AsyncTask
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException

class LoadDetailsNews(activity: AppCompatActivity, var urlDetails: String) :
    AsyncTask<Void, Void, ArrayList<String>>() {

    private var details: ArrayList<String>? = ArrayList()
    private var loader = activity as ILoadDetails

    //Aqui formata o conteudo do texto que é mostrado na tela
    override fun doInBackground(vararg p0: Void?): ArrayList<String> {
        try {

            val url = urlDetails

            val doc: Document = Jsoup.connect(url).get()
            val div: Elements = doc.select("div.content-left")
            div.select("div").remove() //Aqui remove os itens que não quero que aparece na tela
            div.select("footer").remove()
            //Aqui ele pega apenas o texto dentro das tags "p" e "li"
            val textComplete = div.select("h2, p") // h2 é o titulo da materia, p é o conteudo dos paragrafos

            val subtitle: Elements = doc.select("p.excerpt")//Aqui pega o subtitulo da materia

            val author: Elements = doc.select("div.author")//Aqui pega o autor da materia

            val article: Elements = doc.select("p")

            Log.i("Result TOP", "TEXTO:  $article ")

            for (element in subtitle + textComplete + author) {
                if (element.text() != "")
                    details!!.add(element.text())
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }
        return details!!
    }

    override fun onPostExecute(result: ArrayList<String>?) {
        loader.getDetails(result!!)
    }
}