package com.example.webscrapingjsoup

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.io.IOException

class Splash : AppCompatActivity(), IJsoupData {

    private var loader: AsyncTask<Void, Void, ArrayList<New>>? = null
    private val WEB_PAGE: String = "limitstart=0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loader = LoadNews(this, WEB_PAGE)
        loader!!.execute()

    }

    override fun getWebData(datas: ArrayList<New>) {
        if (datas.isEmpty()){ // se os dados recebidos estiverem vazios, ele cai na tela de erro
            val intent = Intent(this, Error::class.java)
            startActivity(intent)
            finish()
        }else{
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("NEWS", datas)
            //Log.i("Result", "recyclerView Splash  $datas")
            startActivity(intent)
            finish()
        }

    }

}
