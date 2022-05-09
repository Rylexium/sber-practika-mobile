package com.example.sber_practika.activity.auth.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.sber_practika.R


class BankCardFragment : Fragment() {

    companion object {
        var bankCard : String = ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.fragment_bank_card, container, false)
        binding.findViewById<EditText>(R.id.edittext_bankcard).addTextChangedListener (object : TextWatcher {
            private val space = ' '

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable) {
                // Remove all spacing char
                var pos = 0
                while (true) {
                    if (pos >= s.length) break
                    if (space == s[pos] && ((pos + 1) % 5 != 0 || pos + 1 == s.length)) {
                        s.delete(pos, pos + 1)
                    } else {
                        pos++
                    }
                }

                // Insert char where needed.
                pos = 4
                while (true) {
                    if (pos >= s.length) break
                    val c = s[pos]
                    // Only if its a digit where there should be a space we insert a space
                    if ("0123456789".indexOf(c) >= 0) {
                        s.insert(pos, "" + space)
                    }
                    pos += 5
                }
            }
        })
        return binding
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