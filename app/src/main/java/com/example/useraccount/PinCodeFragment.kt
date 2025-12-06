package com.example.useraccount

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.useraccount.databinding.FragmentPinCodeBinding

class PinCodeFragment : Fragment() {

    private var _binding: FragmentPinCodeBinding? = null
    private val binding get() = _binding!!

    private var pinInput = "" // istifadəçinin daxil etdiyi PIN

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPinCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // SharedPreferences-dən saxlanmış PIN-i oxuyuruq
        val prefs = requireContext().getSharedPreferences("userproperties", Context.MODE_PRIVATE)
        val correctPin = prefs.getString("pin", "0000") ?: ""
        val username = prefs.getString("username", "")
        binding.textView3.text = "Səni görmək xoşdur, $username"



        // Rəqəm düymələri
        val buttons = listOf(
            binding.btn0, binding.btn1, binding.btn2, binding.btn3,
            binding.btn4, binding.btn5, binding.btn6, binding.btn7,
            binding.btn8, binding.btn9
        )

        buttons.forEach { btn ->
            btn.setOnClickListener {
                if (pinInput.length < 4) {
                    pinInput += btn.text
                    updatePinView(correctPin)
                }
            }
        }

        // Backspace düyməsi
        binding.btnbackspace.setOnClickListener {
            if (pinInput.isNotEmpty()) {
                pinInput = pinInput.dropLast(1)
                updatePinView(correctPin)
            }
        }

        // Kömək düyməsi
        binding.btnhelp.setOnClickListener {
            Toast.makeText(requireContext(), "Pin daxil edin. 4 rəqəm olmalıdır.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updatePinView(correctPin: String) {
        // TextView-da ● göstərmək
        binding.inputPin.text = "●".repeat(pinInput.length)

        // PIN tamamlandıqda yoxlama
        if (pinInput.length == 4) {
            if (pinInput == correctPin) {
                startActivity(Intent(requireContext(), screenMain::class.java))
                requireActivity().finish()
            } else {
                Toast.makeText(requireContext(), "PIN səhv!", Toast.LENGTH_SHORT).show()
                pinInput = ""
                binding.inputPin.text = ""
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
