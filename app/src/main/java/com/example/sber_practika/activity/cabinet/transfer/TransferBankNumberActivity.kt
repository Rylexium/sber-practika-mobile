package com.example.sber_practika.activity.cabinet.transfer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.sber_practika.R
import com.example.sber_practika.activity.auth.fragments.BankCardFragment
import com.example.sber_practika.activity.auth.fragments.PhoneFragment
import com.example.sber_practika.activity.cabinet.entity.User
import com.example.sber_practika.activity.cabinet.transfer.fragments.BankNumberFragment
import com.example.sber_practika.activity.cabinet.transfer.util.BeautifulOutput
import com.example.sber_practika.utils.Fields
import com.example.sber_practika.utils.HideKeyboardClass

class TransferBankNumberActivity : AppCompatActivity() {
    private lateinit var btnTransfer : Button
    private lateinit var layoutBankNumber : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_bank_number)
        initComponents()
        applyEvents()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            HideKeyboardClass.hideKeyboard(this)
            currentFocus!!.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun applyEvents() {
        btnTransfer.setOnClickListener {

        }
    }

    private fun initComponents() {
        btnTransfer = findViewById(R.id.btnTransferBankNumber)

        layoutBankNumber = findViewById(R.id.layout_for_bank_number)

        Fields.onAddBankNumberField(this, layoutBankNumber)

        supportFragmentManager.beginTransaction()
            .add(R.id.containerTransferBankNumber,
                when(intent.getIntExtra("method", 1)){
                    1 -> BankNumberFragment()
                    2 -> BankCardFragment()
                    else -> PhoneFragment()
            })
            .commit()
    }
}