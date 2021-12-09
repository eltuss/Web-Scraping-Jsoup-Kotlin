package com.example.webscrapingjsoup

import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_loader.view.*

class LoaderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    val progressbar: ProgressBar = itemView.progressbar

    fun bindView(){
        progressbar.isIndeterminate = true
    }

}