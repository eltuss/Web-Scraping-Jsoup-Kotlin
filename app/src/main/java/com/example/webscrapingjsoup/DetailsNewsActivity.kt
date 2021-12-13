package com.example.webscrapingjsoup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details_news.*

class DetailsNewsActivity : AppCompatActivity() {

    var urlImage: String? = null
    var urlDetails: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_news)

        urlImage = intent.getStringExtra("IMAGE")
        urlDetails = intent.getStringExtra("DETAILS")

        txt_title_detail!!.text = intent.getStringExtra("TITLE")
        Picasso.get().load(urlImage).into(image_detail)

    }
}