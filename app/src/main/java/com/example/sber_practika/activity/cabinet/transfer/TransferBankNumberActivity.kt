package com.example.sber_practika.activity.cabinet.transfer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.sber_practika.R
import com.example.sber_practika.activity.auth.fragments.BankCardFragment
import com.example.sber_practika.activity.auth.fragments.PhoneFragment
import com.example.sber_practika.activity.cabinet.entity.User
import com.example.sber_practika.activity.cabinet.transfer.controllers.TransferBankNumberController
import com.example.sber_practika.activity.cabinet.transfer.fragments.BankNumberFragment
import com.example.sber_practika.utils.Fields
import com.example.sber_practika.utils.HideKeyboardClass
import com.example.sber_practika.utils.ShowToast
import kotlinx.coroutines.launch

class TransferBankNumberActivity : AppCompatActivity() {
    private lateinit var btnTransfer : Button
    private lateinit var layoutBankNumber : LinearLayout
    private lateinit var value : EditText
    private var method = 0

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
            lifecycleScope.launch {
                val message = when (method) {
                    1 -> TransferBankNumberController.transferToBankNumber(
                        User.bankNumber,
                        findViewById<TextView>(R.id.edittext_bank_number).text.toString(),
                        value.text.toString())
                    2 -> TransferBankNumberController.transferToBankCard(
                        User.bankNumber,
                        findViewById<TextView>(R.id.edittext_bankcard).text.toString(),
                        value.text.toString())
                    else -> TransferBankNumberController.transferToPhone(
                        User.bankNumber,
                        findViewById<TextView>(R.id.edittext_phone).text.toString(),
                        value.text.toString())
                }
                ShowToast.show(baseContext, message)
                onBackPressed()
            }
        }
    }

    private fun initComponents() {
        btnTransfer = findViewById(R.id.btnTransferBankNumber)
        method = intent.getIntExtra("method", 1)
        layoutBankNumber = findViewById(R.id.layout_for_bank_number)
        value = findViewById(R.id.edittext_transfer_value_bank_number)
        Fields.onAddBankNumberField(this, layoutBankNumber, false)

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