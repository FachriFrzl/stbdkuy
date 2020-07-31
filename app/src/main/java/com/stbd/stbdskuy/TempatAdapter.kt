package com.stbd.stbdskuy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_tempat.view.*

class TempatAdapter: RecyclerView.Adapter<TempatAdapter.ViewHolder>(){

    private lateinit var listTempat: List<dataclass>
    private lateinit var tempatClickListener: TempatItemClickListener

    interface TempatItemClickListener{
        fun tempatItemClickListener(dataClass: dataclass)
    }

    class ViewHolder(view:View): RecyclerView.ViewHolder(view){
        val ivImageTempat = view.iv_imageTempat
        val tvNamaTempat = view.tv_nama_tempat
        val tvJarak = view.tv_jarak
        val tvDesc = view.tv_desc_tempat

        fun b(dataClass: dataclass, tempatClickListener: TempatItemClickListener){
            tvNamaTempat.text = dataClass.nama
            tvJarak.text = dataClass.Jarak
            tvDesc.text = dataClass.deskripsi
            if(dataClass.imageUrl != "null"){
                Picasso.get().load(dataClass.imageUrl).into(ivImageTempat)
            }

            itemView.setOnClickListener {
                tempatClickListener.tempatItemClickListener(dataClass)
            }
        }
    }

    fun tempatAdapter(listTempat: List<dataclass>, tempatClickListener: TempatItemClickListener){
        this.listTempat = listTempat
        this.tempatClickListener = tempatClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_tempat,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return listTempat.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.b(listTempat.get(position),tempatClickListener)
    }
}