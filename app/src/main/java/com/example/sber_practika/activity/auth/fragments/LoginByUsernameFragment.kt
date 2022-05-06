package com.example.sber_practika.activity.auth.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.sber_practika.R


class LoginByUsernameFragment : Fragment() {

    companion object {
        var username : String = ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login_by_username, container, false)
    }

    override fun onPause() {
        super.onPause()
        username = activity?.findViewById<EditText>(R.id.edittext_username)?.text.toString()
    }

    override fun onResume() {
        super.onResume()
        activity?.findViewById<EditText>(R.id.edittext_username)?.setText(username)
    }

}