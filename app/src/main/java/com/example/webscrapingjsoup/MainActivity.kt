package com.example.webscrapingjsoup

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),ILoadMore, IJsoupData {

    private var news: ArrayList<New>? = ArrayList()
    private var newsLoad: MutableList<New?> = ArrayList()
    lateinit var  newAdapter: NewAdapter
    private var loader: AsyncTask<Void, Void, ArrayList<New>>? = null
    private var numberPage: Int = 0
    private var WEB_PAGE: String? = null

    override fun onLoadMore() { //Aqui carregamos mais paginas do site
        numberPage += 10
        WEB_PAGE = "start=$numberPage"
        loader = LoadNews(this, WEB_PAGE!!)
        loader!!.execute()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        news = intent.getSerializableExtra("NEWS") as ArrayList<New>
        getTenNews(news!!)

        recyclerView.layoutManager = LinearLayoutManager(this)
        newAdapter = NewAdapter(recyclerView, this, newsLoad)
        recyclerView.adapter = newAdapter
        newAdapter.getLoadMore(this) //Aqui o adapter recebe as outras paginas

       // Log.i("Result", "recyclerView Main  $recyclerView")
    }

    private fun getTenNews(listNews: java.util.ArrayList<New>) { //Aqui ele carrega a quantidade de itens
        for (index in 0..27){
            newsLoad.add(listNews[index])
        }
    }

    // Quando carregar os 27 itens do getTenNews, ele carregaria mais 10 do onLoadMore até completar a quantidade que esta setado aqui abaixo
    override fun getWebData(datas: ArrayList<New>) {
        if (newsLoad.size < 27){
            newsLoad.add(null)
            newAdapter.notifyItemInserted(newsLoad.size -1)

            Handler().postDelayed({
                newsLoad.removeAt(newsLoad.size -1)
                newAdapter.notifyItemRemoved(newsLoad.size)

                getTenNews(datas)

                newAdapter.notifyDataSetChanged()
                newAdapter.setLoaded()

            }, 3000)
        }else{
            Toast.makeText(this,"Carregamento dos dados completo!",Toast.LENGTH_SHORT).show()
        }
    }
}