package com.example.mytix

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mytix.databinding.ActivityHomeBinding

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Menghubungkan layout ActivityHomeBinding dengan kode Kotlin
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengambil data yang dikirim dari LoginActivity
        val usn = intent.getStringExtra(Layout.EXT_USN)

        // Menampilkan data username di elemen editUsername pada layout
        with(binding) {
            editUsername.text = usn

            // Menambahkan event listener pada elemen poster3 (dapat disesuaikan)
            poster3.setOnClickListener {
                // Membuat Intent untuk berpindah ke DetailsActivity
                val intentView = Intent(this@Home, DetailsActivity::class.java)
                // Menjalankan Intent untuk berpindah ke DetailsActivity
                startActivity(intentView)
            }
        }
    }
}