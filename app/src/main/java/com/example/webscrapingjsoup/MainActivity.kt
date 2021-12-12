package com.example.webscrapingjsoup

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),ILoadMore, IJsoupData {

    private var news: ArrayList<News>? = ArrayList()
    private var newsLoad: MutableList<News?> = ArrayList()
    lateinit var  newsAdapter: NewsAdapter
    private var loader: AsyncTask<Void, Void, ArrayList<News>>? = null
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

        news = intent.getSerializableExtra("NEWS") as ArrayList<News>
        getTenNews(news!!)

        recyclerView.layoutManager = LinearLayoutManager(this)
        newsAdapter = NewsAdapter(recyclerView, this, newsLoad)
        recyclerView.adapter = newsAdapter
        newsAdapter.getLoadMore(this) //Aqui o adapter recebe as outras paginas

       // Log.i("Result", "recyclerView Main  $recyclerView")
    }

    private fun getTenNews(listNews: java.util.ArrayList<News>) { //Aqui ele carrega a quantidade de itens
        for (index in 0..27){
            newsLoad.add(listNews[index])
        }
    }

    // Quando carregar os 27 itens do getTenNews, ele carregaria mais 10 do onLoadMore até completar a quantidade que esta setado aqui abaixo
    override fun getWebData(datas: ArrayList<News>) {
        if (newsLoad.size < 27){
            newsLoad.add(null)
            newsAdapter.notifyItemInserted(newsLoad.size -1)

            Handler().postDelayed({
                newsLoad.removeAt(newsLoad.size -1)
                newsAdapter.notifyItemRemoved(newsLoad.size)

                getTenNews(datas)

                newsAdapter.notifyDataSetChanged()
                newsAdapter.setLoaded()

            }, 3000)
        }else{
            Toast.makeText(this,"Carregamento dos dados completo!",Toast.LENGTH_SHORT).show()
        }
    }
}