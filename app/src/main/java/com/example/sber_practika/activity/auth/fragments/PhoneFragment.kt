package com.example.sber_practika.activity.auth.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.sber_practika.R
import com.example.sber_practika.utils.PhoneTextFormatter

class PhoneFragment : Fragment() {

    companion object {
        var phone : String = ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.fragment_phone, container, false)
        val editPhone = binding.findViewById<EditText>(R.id.edittext_phone)
        editPhone.addTextChangedListener(PhoneTextFormatter(editPhone, "8-###-##-##-###"))
        return binding
    }

    override fun onPause() {
        super.onPause()
        phone = activity?.findViewById<EditText>(R.id.edittext_phone)?.text.toString()
    }

    override fun onResume() {
        super.onResume()
        activity?.findViewById<EditText>(R.id.edittext_phone)?.setText(phone)
    }
}