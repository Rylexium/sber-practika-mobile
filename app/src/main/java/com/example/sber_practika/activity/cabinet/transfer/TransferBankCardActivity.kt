package com.example.sber_practika.activity.cabinet.transfer

import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.sber_practika.R
import com.example.sber_practika.activity.auth.fragments.BankCardFragment
import com.example.sber_practika.activity.auth.fragments.LoginByUsernameFragment
import com.example.sber_practika.activity.auth.fragments.PhoneFragment
import com.example.sber_practika.activity.cabinet.entity.User
import com.example.sber_practika.activity.cabinet.transfer.controllers.TransferBankCardController
import com.example.sber_practika.activity.cabinet.transfer.fragments.BankNumberFragment
import com.example.sber_practika.activity.cabinet.transfer.util.BeautifulOutput
import com.example.sber_practika.utils.Fields
import com.example.sber_practika.utils.HideKeyboardClass
import com.example.sber_practika.utils.ShowToast
import kotlinx.coroutines.launch

class TransferBankCardActivity : AppCompatActivity() {
    private lateinit var btnTransfer : Button
    private lateinit var layoutBankCards : LinearLayout
    private lateinit var value : EditText
    private var method = 0
    companion object {
        var selectedBankCard: String? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_bank_card)
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
                    1 -> TransferBankCardController.transferToBankNumber(
                        selectedBankCard.toString(),
                        findViewById<TextView>(R.id.edittext_bank_number).text.toString(),
                        value.text.toString())
                    2 -> TransferBankCardController.transferToBankCard(
                        selectedBankCard.toString(),
                        findViewById<TextView>(R.id.edittext_bankcard).text.toString().replace(" ", ""),
                        value.text.toString())
                    else -> TransferBankCardController.transferToPhone(
                        selectedBankCard.toString(),
                        findViewById<TextView>(R.id.edittext_phone).text.toString().replace("-", ""),
                        value.text.toString())
                }
                ShowToast.show(baseContext, message)
                onBackPressed()
            }
        }
    }

    private fun initComponents() {
        btnTransfer = findViewById(R.id.btnTransferBankCard)
        layoutBankCards = findViewById(R.id.layout_of_bank_cards)
        value = findViewById(R.id.edittext_transfer_value_bank_card)

        BankCardFragment.bankCard = ""
        LoginByUsernameFragment.username = ""
        PhoneFragment.phone = ""

        selectedBankCard = User.listCards.first().idCard
        if(selectedBankCard != null && selectedBankCard != "")
            findViewById<TextView>(R.id.textview_select_bank_card).text = "Выбранная карта : " + BeautifulOutput.beautifulIdBankCard(selectedBankCard!!)

        method = intent.getIntExtra("method", 1)

        supportFragmentManager.beginTransaction()
            .add(R.id.containerTransferBankCard,
                when(intent.getIntExtra("method", 1)){
                    1 -> BankNumberFragment()
                    2 -> BankCardFragment()
                    else -> PhoneFragment()
                })
            .commit()

        User.listCards.forEach { card ->
            Fields.onAddBankCardField(card.idCard, card.nameCard, card.dateCard,card.balanceCard.toString(),
                this, layoutBankCards, false)
        }
    }

}