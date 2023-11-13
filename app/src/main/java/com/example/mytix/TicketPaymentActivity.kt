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
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinners()
        setupButtonListeners()
    }

        private fun setupSpinners() {
            val bioskop = resources.getStringArray(R.array.listBioskop)
            val seat = resources.getStringArray(R.array.listSeats)
            val payment = resources.getStringArray(R.array.listPayment)

            val bioskopAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, bioskop)
            val seatAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, seat)
            val paymentAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, payment)

            binding.bioskopSpinner.adapter = bioskopAdapter
            binding.jenisSeatSpinner.adapter = seatAdapter
            binding.paymentSpinner.adapter = paymentAdapter

        // Button click event for Order Summary
        binding.jenisSeatSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedSeatPrice = seatPrice[position]
                binding.regularSeatPrice.text = "Rp$selectedSeatPrice"
                binding.actualPayPrice.text = binding.regularSeatPrice.text
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                binding.regularSeatPrice.text = "Rp0-"
                binding.actualPayPrice.text = binding.regularSeatPrice.text
            }
        }
//MENYIAPKAN ADAPTER DAN MENGAITKANNYA DENGAN SPINNER UNTUK METODE PEMBAYARAN BANK DAN QR
            val listBankArray = resources.getStringArray(R.array.listBank)

            val listBankAdapter = ArrayAdapter(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, listBankArray)

            //MENANGANI PERUBAHAN PEMILIHAN METODE PEMBAYARAN
            binding.paymentSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    when (position) {
                        1 -> binding.bankSpinner.adapter = listBankAdapter
                    }
                }

                //bagian dari implementasi AdapterView.OnItemSelectedListener, yang digunakan untuk mendengarkan perubahan pemilihan item pada spinner (dropdown menu) di aplikasi.
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
        }

    private fun setupButtonListeners() {
        binding.backButton.setOnClickListener {
            val intentDetails = Intent(this, DetailsActivity::class.java)
            startActivity(intentDetails)
        }

        //MENGHANDLE PEMILIHAN TANGGAL
        binding.datePicker.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
            datePicker.show(supportFragmentManager, "datePicker")
            datePicker.addOnPositiveButtonClickListener {
                val simpleDateFormat = SimpleDateFormat("yyyy-MMM-dd", Locale.getDefault())
                binding.pilihTanggal.text = simpleDateFormat.format(Date(it).time)
            }
        }

        //MENGHANDLE TOMBOL ORDER SUMMARY
        binding.orderButton.setOnClickListener {
            val selectedDate = binding.pilihTanggal.text.toString()
            val selectedAccNumber = binding.accountNumber.text.toString()
            val selectedPayment = binding.paymentSpinner.selectedItem.toString()
            val selectedPaymentPick = binding.bankSpinner.selectedItem.toString()
            val selectedBioskop = binding.bioskopSpinner.selectedItem.toString()
            val selectedSeat = binding.jenisSeatSpinner.selectedItem.toString()

            if (selectedDate.isNotBlank() && selectedDate != "Select" &&
                selectedAccNumber.isNotBlank() &&
                selectedPayment != "Select" &&
                selectedPaymentPick != "Select" &&
                selectedBioskop != "Select" &&
                selectedSeat != "Select"
            ) {
                val currentDate = SimpleDateFormat("ddMMyyyy", Locale.getDefault()).format(Date())
                val random = (1..100).random()
                val orderNumber = "$currentDate-$random"

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
                Toast.makeText(this, "Lengkapi Informasi", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
