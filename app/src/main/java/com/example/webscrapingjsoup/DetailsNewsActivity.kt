package com.example.webscrapingjsoup

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details_news.*

class DetailsNewsActivity : AppCompatActivity() {

    private var urlImage: String? = null
    private var urlDetails: String? = null
    private var loadDetailsNews: AsyncTask<Void, Void, ArrayList<String>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_news)

        urlImage = intent.getStringExtra("IMAGE")
        urlDetails = intent.getStringExtra("DETAILS")

        txt_title_detail!!.text = intent.getStringExtra("TITLE")
        Picasso.get().load(urlImage).into(image_detail)
        loadDetailsNews = LoadDetailsNews(this, urlDetails!!)
        loadDetailsNews!!.execute()

    }
}