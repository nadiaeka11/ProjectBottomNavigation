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
        binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bioskop = intent.getStringExtra("bioskop")
        val selectedDate = intent.getStringExtra("selectedDate")
        val orderNumber = intent.getStringExtra("orderNumber")
        val payment = intent.getStringExtra("payment")
        val paymentPick = intent.getStringExtra("paymentPick")
        val price = intent.getStringExtra("price")
        val seat = intent.getStringExtra("seat")

        val formattedDate = formatDateString(selectedDate)

        with(binding) {
            backButton.setOnClickListener {
                finish()
            }

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

    private fun formatDateString(date: String?): String {
        val inputFormat = SimpleDateFormat("yyyy-MMM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("EEEE, dd-MM-yyyy", Locale.getDefault())
        return outputFormat.format(inputFormat.parse(date))
    }
}