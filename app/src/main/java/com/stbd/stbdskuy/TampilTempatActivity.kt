package com.stbd.stbdskuy

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_tampil_tempat.*

class TampilTempatActivity : AppCompatActivity() {

    private lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tampil_tempat)

        db = Firebase.database.reference

        val tempatID = intent.getStringExtra("TEMPAT_ID").toString()

        db.child("db").child(tempatID).addListenerForSingleValueEvent(
            object : ValueEventListener{
                override fun onCancelled(error: DatabaseError) {

                }
                override fun onDataChange(snapshot: DataSnapshot) {
                    val dataTempat = snapshot.getValue(dataclass::class.java)
                    if(dataTempat != null){
                        tv_namaTempat_tampil.text = dataTempat.nama
                        tv_descTempat_tampil.text = dataTempat.deskripsi
                        tv_jarakTempat_tampil.text = dataTempat.Jarak
                        tv_estimasiHarga_tampil.text = dataTempat.estimasi
                        if(dataTempat.imageUrl != "null"){
                            Picasso.get().load(dataTempat.imageUrl).into(iv_imageTempat_tampil)
                        }
                        val namaTempatEdited = dataTempat.nama.replace(" ","+")
                        println("nama_tempat => $namaTempatEdited")
                        btn_direcCar_tampil.setOnClickListener {
                            val gmmIntentUri =
                                Uri.parse("google.navigation:q=$namaTempatEdited")
                            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                            mapIntent.setPackage("com.google.android.apps.maps")
                            startActivity(mapIntent)
                        }
                        btn_direcMotor_tampil.setOnClickListener {
                            val gmmIntentUri =
                                Uri.parse("google.navigation:q=$namaTempatEdited&mode=l")
                            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                            mapIntent.setPackage("com.google.android.apps.maps")
                            startActivity(mapIntent)
                        }
                        btn_direcJalan_tampil.setOnClickListener {
                            val gmmIntentUri =
                                Uri.parse("google.navigation:q=$namaTempatEdited&mode=w")
                            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                            mapIntent.setPackage("com.google.android.apps.maps")
                            startActivity(mapIntent)
                        }
                        btn_lokasi_tampil.setOnClickListener {
                            val gmmIntentUri =
                                Uri.parse("geo:0,0?q=$namaTempatEdited")
                            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                            mapIntent.setPackage("com.google.android.apps.maps")
                            startActivity(mapIntent)
                        }
                    }
                }
            }
        )

    }
}