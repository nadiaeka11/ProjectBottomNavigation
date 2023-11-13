package com.example.mytix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.mytix.databinding.ActivityLayoutBinding

class Layout : AppCompatActivity() {
    private lateinit var binding: ActivityLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("EXT_USN")
        val bundle = Bundle()
        bundle.putString("EXT_USN", username)

        val homeFragment = HomeFragment()
        homeFragment.arguments = bundle

        val accountFragment = AccountFragment()
        accountFragment.arguments = bundle

        replaceFragment(HomeFragment())

        with(binding) {
            binding.bottomNavbar.setOnItemSelectedListener() {
                when(it.itemId){
                    R.id.itemHome -> replaceFragment(HomeFragment())
                    R.id.itemAccount -> replaceFragment(AccountFragment())
                    else -> {}
                }
                true
            }
        }
    }

    //untuk menggantikan fragmen yang ditampilkan di dalam tampilan yang memiliki id dengan nama
    // "frameLayout." Ini adalah bagian dari pengelolaan fragmen dalam Android.
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}