package com.example.useraccount

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.useraccount.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferences = requireContext().getSharedPreferences("userproperties", MODE_PRIVATE)
        var username = preferences.getString("username", "")
        var email = preferences.getString("email","" )

        binding.textUsername.text = "Xoş gəldin, $username"
        binding.textEmail.text = email

        binding.btnLogout.setOnClickListener {
            var preferences = requireContext().getSharedPreferences("userproperties", MODE_PRIVATE)
            var editor = preferences.edit()
            editor.putBoolean("isLoggedIn", false).apply()
            editor.apply()
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
            Toast.makeText(requireContext(), R.string.loggedout_message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}