package com.example.useraccount

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.useraccount.R
import com.example.useraccount.databinding.ActivityScreenRegisterBinding


class screenRegister : AppCompatActivity() {
    private lateinit var binding: ActivityScreenRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityScreenRegisterBinding.inflate(this.layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.button.setOnClickListener {
            if(binding.editTextText3.text.isNotEmpty() && binding.editTextText.text.isNotEmpty() && binding.editTextText2.text.isNotEmpty()){
                var username = binding.editTextText.text.toString()
                var password = binding.editTextText2.text.toString()
                var email = binding.editTextText3.text.toString()
                var sharedPreferences = this.getSharedPreferences("userproperties", MODE_PRIVATE)
                var editor = sharedPreferences.edit()
                editor.putString("username", username)
                editor.putString("password", password)
                editor.putString("email", email)
                editor.apply()
                hideRegisterUI()
                openFragment(CreatePinFragment())
                Toast.makeText(this, R.string.succes_register_message, Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, R.string.empty_name_or_password_message, Toast.LENGTH_SHORT).show()

            }
        }
        binding.textView2.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    private fun hideRegisterUI() {
        binding.imageView.visibility = View.GONE
        binding.constraintLayout.visibility = View.GONE
        binding.button.visibility = View.GONE
        binding.textView2.visibility = View.GONE
    }
    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.framlayout1.id, fragment)
            .commit()
    }
}
