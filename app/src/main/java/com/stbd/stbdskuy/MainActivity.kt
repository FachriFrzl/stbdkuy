package com.stbd.stbdskuy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TempatAdapter.TempatItemClickListener {

    private lateinit var tempatAdapter: TempatAdapter

    override fun tempatItemClickListener(dataClass: dataclass) {
        val intent = Intent(this,TampilTempatActivity::class.java)
        intent.putExtra("TEMPAT_ID",dataClass.uid)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tampilData()
        tempatAdapter = TempatAdapter()
        btn_pindahtambah.setOnClickListener(){
            startActivity(Intent(this,tambahtempat::class.java))
        }
    }

    fun tampilData(){
        val ref = FirebaseDatabase.getInstance().getReference("db")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    val listTempat = ArrayList<dataclass>()
                    for (h in p0.children) {
                        val iniData = h.getValue(dataclass::class.java)
                        if (iniData != null) {
                            listTempat.add(iniData)
                            println("DATA => ${iniData.Jarak}")
                        }
                    }
                    tempatAdapter.tempatAdapter(listTempat,this@MainActivity)
                    rv_utama.layoutManager = LinearLayoutManager(this@MainActivity)
                    rv_utama.adapter = tempatAdapter
                }
            }
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

    }


}