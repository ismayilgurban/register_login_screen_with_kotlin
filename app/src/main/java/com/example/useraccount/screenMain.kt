package com.example.useraccount

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.useraccount.databinding.ActivityScreenMainBinding
import androidx.fragment.app.Fragment

class screenMain : AppCompatActivity() {
    private lateinit var binding: ActivityScreenMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityScreenMainBinding.inflate(this.layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        fragmentNavigation(HomeFragment())
        binding.bottomNavigation.setOnItemSelectedListener{
            item -> when(item.itemId){
                R.id.home -> fragmentNavigation(HomeFragment())
                R.id.search -> fragmentNavigation(SearchFragment())
                R.id.settings -> fragmentNavigation(SettingsFragment())
            }
            return@setOnItemSelectedListener true
        }
    }
    private fun fragmentNavigation(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()

    }
}