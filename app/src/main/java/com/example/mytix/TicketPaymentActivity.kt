package com.example.mytix

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mytix.databinding.ActivityPaymentBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TicketPaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentBinding
    private val seatPrice = arrayOf(0, 35000, 80000)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Menghubungkan layout XML activity_payment.xml dengan kode Kotlin
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengatur spinner dan button listeners
        setupSpinners()
        setupButtonListeners()
    }

    // Fungsi untuk mengatur adapter dan listener pada spinner
    private fun setupSpinners() {
        // Mendapatkan array string dari resources untuk spinner bioskop, seat, dan payment
        val bioskop = resources.getStringArray(R.array.listBioskop)
        val seat = resources.getStringArray(R.array.listSeats)
        val payment = resources.getStringArray(R.array.listPayment)

        // Membuat adapter untuk spinner bioskop, seat, dan payment
        val bioskopAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, bioskop)
        val seatAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, seat)
        val paymentAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, payment)

        // Menetapkan adapter ke masing-masing spinner
        binding.bioskopSpinner.adapter = bioskopAdapter
        binding.jenisSeatSpinner.adapter = seatAdapter
        binding.paymentSpinner.adapter = paymentAdapter

        // Listener untuk spinner jenis seat
        binding.jenisSeatSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Mengupdate harga pada tampilan berdasarkan pilihan jenis seat
                val selectedSeatPrice = seatPrice[position]
                binding.regularSeatPrice.text = "Rp$selectedSeatPrice"
                binding.actualPayPrice.text = binding.regularSeatPrice.text
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // Mengatasi jika tidak ada item yang dipilih pada jenis seat
                binding.regularSeatPrice.text = "Rp0-"
                binding.actualPayPrice.text = binding.regularSeatPrice.text
            }
        }

        // Array string untuk spinner bank
        val listBankArray = resources.getStringArray(R.array.listBank)

        // Membuat adapter untuk spinner bank
        val listBankAdapter = ArrayAdapter(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, listBankArray)

        // Listener untuk spinner payment
        binding.paymentSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Menetapkan adapter bank jika pilihan payment adalah bank
                when (position) {
                    1 -> binding.bankSpinner.adapter = listBankAdapter
                }
            }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
        }

    // Fungsi untuk mengatur listeners pada button
    private fun setupButtonListeners() {
        // Listener untuk button back
        binding.backButton.setOnClickListener {
            val intentDetails = Intent(this, DetailsActivity::class.java)
            startActivity(intentDetails)
        }

        // Listener untuk button date picker
        binding.datePicker.setOnClickListener {
            // Membuat date picker menggunakan MaterialDatePicker
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
            datePicker.show(supportFragmentManager, "datePicker")
            datePicker.addOnPositiveButtonClickListener {
                // Mengupdate tampilan dengan tanggal yang dipilih
                val simpleDateFormat = SimpleDateFormat("yyyy-MMM-dd", Locale.getDefault())
                binding.pilihTanggal.text = simpleDateFormat.format(Date(it).time)
            }
        }

        // Listener untuk button order
        binding.orderButton.setOnClickListener {
            // Mendapatkan nilai dari elemen-elemen yang dibutuhkan untuk order
            val selectedDate = binding.pilihTanggal.text.toString()
            val selectedAccNumber = binding.accountNumber.text.toString()
            val selectedPayment = binding.paymentSpinner.selectedItem.toString()
            val selectedPaymentPick = binding.bankSpinner.selectedItem.toString()
            val selectedBioskop = binding.bioskopSpinner.selectedItem.toString()
            val selectedSeat = binding.jenisSeatSpinner.selectedItem.toString()

            // Memeriksa apakah semua informasi telah diisi
            if (selectedDate.isNotBlank() && selectedDate != "Select" &&
                selectedAccNumber.isNotBlank() &&
                selectedPayment != "Select" &&
                selectedPaymentPick != "Select" &&
                selectedBioskop != "Select" &&
                selectedSeat != "Select"
            ) {
                // Membuat nomor order dengan format tertentu
                val currentDate = SimpleDateFormat("ddMMyyyy", Locale.getDefault()).format(Date())
                val random = (1..100).random()
                val orderNumber = "$currentDate-$random"

                // Menyiapkan intent untuk memulai TransactionOrderActivity
                val intent = Intent(this, TransactionOrderActivity::class.java)
                intent.putExtra("selectedDate", selectedDate)
                intent.putExtra("accNumber", selectedAccNumber)
                intent.putExtra("payment", selectedPayment)
                intent.putExtra("paymentPick", selectedPaymentPick)
                intent.putExtra("bioskop", selectedBioskop)
                intent.putExtra("seat", selectedSeat)
                intent.putExtra("price", binding.actualPayPrice.text)
                intent.putExtra("orderNumber", orderNumber)
                startActivity(intent)
            } else {
                // Menampilkan pesan jika ada informasi yang belum diisi
                Toast.makeText(this, "Lengkapi Informasi", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
