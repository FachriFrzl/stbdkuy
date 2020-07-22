package com.example.stbdskuy

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_tempat.view.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_utama.adapter = adapter
        tampilData()
        btn_pindahtambah.setOnClickListener(){
            startActivity(Intent(this,tambahtempat::class.java))
        }
    }

    class simpanData(val iniData:dataclass): Item<GroupieViewHolder>(){
        override fun bind(viewHolder: GroupieViewHolder, position: Int) {
            viewHolder.itemView.tv_nama_tempat.text = iniData.nama
            viewHolder.itemView.tv_desc_tempat.text = iniData.deskripsi
            viewHolder.itemView.tv_jarak.text = iniData.Jarak
            if (iniData.imageUrl != "null"){
                val ivImagetTempat = viewHolder.itemView.iv_imageTempat
                Picasso.get().load(iniData.imageUrl).into(ivImagetTempat)
            }
        }

        override fun getLayout(): Int {
            return R.layout.activity_tempat
        }
    }

    val adapter = GroupAdapter<GroupieViewHolder>()
    fun tampilData(){
        val ref = FirebaseDatabase.getInstance().getReference("db")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    adapter.clear()
                    for (h in p0.children) {
                        val iniData = h.getValue(dataclass::class.java)
                        if (iniData != null) {
                            adapter.add(simpanData(iniData))
                            println("DATA => ${iniData.Jarak}")
                        }
                    }
                    rv_utama.layoutManager = LinearLayoutManager(this@MainActivity)
                    rv_utama.adapter = adapter
                }
            }
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

    }


}