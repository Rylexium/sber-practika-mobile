package com.example.sber_practika.activity.auth.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.sber_practika.R


class BankCardFragment : Fragment() {

    companion object {
        var bankCard : String = ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bank_card, container, false)
    }

    override fun onPause() {
        super.onPause()
        bankCard = activity?.findViewById<EditText>(R.id.edittext_bankcard)?.text.toString()
    }

    override fun onResume() {
        super.onResume()
        activity?.findViewById<EditText>(R.id.edittext_bankcard)?.setText(bankCard)
    }

}