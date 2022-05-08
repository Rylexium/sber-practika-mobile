package com.example.sber_practika.activity.cabinet.transfer.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sber_practika.R

class BankNumberFragment : Fragment() {

    companion object {
        var bankNumber : String = ""
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bank_number, container, false)
    }
}