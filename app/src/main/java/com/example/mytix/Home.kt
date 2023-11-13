package com.example.mytix

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mytix.databinding.ActivityHomeBinding

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengambil data yang dikirim dari LoginActivity
        val usn = intent.getStringExtra("usn")

        with(binding) {
            editUsername.text = usn
            poster3.setOnClickListener {
                val intentView = Intent(this@Home, DetailsActivity::class.java)
                startActivity(intentView)
            }
        }
    }
}