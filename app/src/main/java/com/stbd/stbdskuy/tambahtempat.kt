package com.stbd.stbdskuy

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.tambahtempat.*
import java.util.*

class tambahtempat : AppCompatActivity() {

    var selectedPhoto: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tambahtempat)
        btn_tambah.setOnClickListener {
            uploadImageToStorage()
//            if(selectedPhoto == null){
//                uploadImageToStorage()
//            }else tambahTempat("null")
        }
        btn_gambar.setOnClickListener()
        {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            selectedPhoto = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhoto)

            val bitmapDrawable = BitmapDrawable(bitmap)
            btn_gambar.setBackgroundDrawable(bitmapDrawable)
        }
    }

    private fun uploadImageToStorage() {
        if (selectedPhoto == null){
            tambahTempat("null")
            return
        }

        val filename = UUID.randomUUID().toString()
        val refStorage = FirebaseStorage.getInstance().getReference("/images/$filename")

        refStorage.putFile(selectedPhoto!!)
            .addOnSuccessListener {
                refStorage.downloadUrl.addOnSuccessListener {
                    tambahTempat(it.toString())
                }
            }
    }

    private fun tambahTempat(imageUrl: String) {
        val uid = UUID.randomUUID().toString()
        val ref = FirebaseDatabase.getInstance().getReference("/db/$uid")
        val nama = ed_nama.text.toString()
        val deskripsi = ed_deskripsi.text.toString()
        val lokasi = ed_lokasi.text.toString()
        val jarak = ed_jarak.text.toString()
        val estimasi = ed_estimasi.text.toString()

        val tempat = dataclass(uid, nama, deskripsi, imageUrl, lokasi, jarak,estimasi)

        ref.setValue(tempat)
            .addOnSuccessListener {
                Toast.makeText(this, "Berhasil", Toast.LENGTH_SHORT).show()
            }
    }
}
