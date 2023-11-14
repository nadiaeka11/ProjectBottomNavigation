package com.example.mytix

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mytix.databinding.ActivityTransactionBinding
import java.text.SimpleDateFormat
import java.util.Locale

class TransactionOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Menghubungkan layout XML activity_transaction.xml dengan kode Kotlin
        binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mendapatkan data yang dikirimkan dari TicketPaymentActivity
        val bioskop = intent.getStringExtra("bioskop")
        val selectedDate = intent.getStringExtra("selectedDate")
        val orderNumber = intent.getStringExtra("orderNumber")
        val payment = intent.getStringExtra("payment")
        val paymentPick = intent.getStringExtra("paymentPick")
        val price = intent.getStringExtra("price")
        val seat = intent.getStringExtra("seat")

        // Memformat tanggal
        val formattedDate = formatDateString(selectedDate)

        with(binding) {
            // Listener untuk button back
            backButton.setOnClickListener {
                finish()
            }

            // Mengatur teks pada elemen-elemen tampilan dengan data yang diterima
            movieTitle.text = "Mencuri Raden Saleh"
            movieDetails.text = bioskop
            date.text = formattedDate
            orderNumberText.text = orderNumber
            convenienceFee.text = "$payment ($paymentPick)"
            seatPriceValue.text = price
            actualPayValue.text = seatPriceValue.text
            seatType.text = "$seat Seat"
        }
    }

    // Fungsi untuk memformat tanggal
    private fun formatDateString(date: String?): String {
        val inputFormat = SimpleDateFormat("yyyy-MMM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("EEEE, dd-MM-yyyy", Locale.getDefault())
        return outputFormat.format(inputFormat.parse(date))
    }
}