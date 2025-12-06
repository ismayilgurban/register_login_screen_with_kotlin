package com.example.useraccount

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.useraccount.databinding.FragmentCreatePinBinding

class CreatePinFragment : Fragment() {
    private var _binding: FragmentCreatePinBinding? = null
    private val binding get() = _binding!!
    private var pinInput = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatePinBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttons = listOf(
            binding.btn0, binding.btn1, binding.btn2, binding.btn3, binding.btn4,
            binding.btn5, binding.btn6, binding.btn7, binding.btn8, binding.btn9
        )

        val prefs = requireContext().getSharedPreferences("userproperties", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        var username = prefs.getString("username", "")
        binding.textView3.text = "Xoş gəldin, $username"



        buttons.forEach { btn ->
            btn.setOnClickListener {
                if (pinInput.length < 4) {
                    pinInput += btn.text
                    updatePinView()
                }
                else{
                    Toast.makeText(requireContext(), "Pin kod 4 rəqəmdən ibarətdir", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnbackspace.setOnClickListener {
            if (pinInput.isNotEmpty()) {
                pinInput = pinInput.dropLast(1)
                updatePinView()
            }
        }
        binding.button2.setOnClickListener {
            if (pinInput.length == 4) {
                val prefs =
                    requireContext().getSharedPreferences("userproperties", Context.MODE_PRIVATE)
                val editor = prefs.edit()
                editor.putBoolean("isLoggedIn", true)
                editor.putString("pin", pinInput).apply()
                Toast.makeText(requireContext(), "PIN uğurla yaradıldı!", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), screenMain::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
            else{
                Toast.makeText(requireContext(), "Pin boş olabilməz", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun updatePinView() {
        binding.inputPin.text = "●".repeat(pinInput.length)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
