package com.example.useraccount

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.useraccount.databinding.ActivityMainBinding
import androidx.fragment.app.Fragment


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(this.layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var preferences = getSharedPreferences("userproperties", MODE_PRIVATE)
        var isLoggedIn = preferences.getBoolean("isLoggedIn", false)
        if(isLoggedIn){
            showOnlyPinFragment()
        }
        binding.forgotPassword.setOnClickListener {
            intent = Intent(this, screenForgotPassword::class.java)
            startActivity(intent)
        }
        binding.button.setOnClickListener{
            if(binding.editTextText.text.isNotEmpty() && binding.editTextText2.text.isNotEmpty()){
               var preferences= getSharedPreferences("userproperties", MODE_PRIVATE)
                var userName = preferences.getString("username", "")
                var password = preferences.getString("password", "")
                var email = preferences.getString("email", "")
                var userInputName = binding.editTextText.text.toString()
                var userInputPassword = binding.editTextText2.text.toString()
                if(userInputPassword == password && (userInputName == userName || userInputName == email)){
                    Toast.makeText(this, R.string.succes_login_message, Toast.LENGTH_SHORT).show()
                    intent = Intent(this, screenMain::class.java)
                    var sharedPreferences = this.getSharedPreferences("userproperties", MODE_PRIVATE)
                    var editor = sharedPreferences.edit()
                    editor.putBoolean("isLoggedIn", true).apply()
                    startActivity(intent)
                    finish()
                }
                else{
                    binding.wrongText.visibility = View.VISIBLE
                    Toast.makeText(this, R.string.incorrect_password_message, Toast.LENGTH_SHORT).show()
                    binding.editTextText.text.clear()
                    binding.editTextText2.text.clear()
                }
            }
            else{
                Toast.makeText(this, R.string.empty_name_or_password_message, Toast.LENGTH_SHORT).show()
            }
        }
        binding.textView2.setOnClickListener {
            intent = Intent(this, screenRegister::class.java)
            startActivity(intent)
        }
    }
    private fun showOnlyPinFragment() {

        // login layout tamamilə gizlənir
        binding.imageView.visibility = View.GONE
        binding.constraintLayout2.visibility = View.GONE
        binding.button.visibility = View.GONE
        binding.textView2.visibility = View.GONE

        // FrameLayout tam ekran olur
        binding.frameLayout.visibility = View.VISIBLE

        openFragment(PinCodeFragment())
    }
    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.frameLayout.id, fragment)
            .commit()
    }
}