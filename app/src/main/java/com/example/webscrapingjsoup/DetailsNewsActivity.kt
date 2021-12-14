package com.example.webscrapingjsoup

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.FileProvider.getUriForFile
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.activity_details_news.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception

class DetailsNewsActivity : AppCompatActivity(), ILoadDetails {

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

        //Aqui ele habilita a opção de voltar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    }

    //Função para compartilhar o item
    fun shareNewsImage(){
        Picasso.get().load(urlImage).into(object : Target{

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {  }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) { }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                val intent = Intent(Intent.ACTION_SEND)
                intent.setType("image/*")
                intent.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap!!))
                startActivity(Intent.createChooser(intent, "Share"))
            }

            private fun getLocalBitmapUri(bitmap: Bitmap): Uri? {

                var bitmapUri: Uri? = null
                try {

                    val file = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    "shareimage" + ".png")

                    val out = FileOutputStream(file)
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, out)
                    out.close()

                    bitmapUri = getUriForFile(applicationContext,
                        applicationContext.packageName + ".fileprovider",
                    file)

                }catch(e : IOException){
                    e.printStackTrace()
                }
                return bitmapUri
            }

        })
    }

    //função que manipula o menu na barra superior da tela
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_details_news, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId

        when(id){
            android.R.id.home -> finish() //Aqui ele volta para a tela de home(principal) MainActivity
            R.id.btn_share -> shareNewsImage()

        }

        return super.onOptionsItemSelected(item)
    }

    //Aqui ele pega os itens separados somente do texto e exibe
    override fun getDetails(details: ArrayList<String>) {
        for (index in 0..details.size-1){
            if (index == details.size-1){
                txt_detail.append("\n" + details[index] + "\n")

            }else{
                txt_detail.append(details[index] + "\n\n")
            }
        }
    }
}