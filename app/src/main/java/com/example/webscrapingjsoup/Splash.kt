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


            val intent = Intent(activity, MainActivity::class.java)
            activity!!.startActivity(intent)
            activity!!.finish()

        }
    }

}