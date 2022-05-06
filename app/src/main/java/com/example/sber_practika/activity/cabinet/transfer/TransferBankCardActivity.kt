package com.example.sber_practika.activity.cabinet.transfer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.sber_practika.R
import com.example.sber_practika.activity.auth.fragments.BankCardFragment
import com.example.sber_practika.activity.auth.fragments.PhoneFragment
import com.example.sber_practika.activity.cabinet.entity.User
import com.example.sber_practika.activity.cabinet.transfer.fragments.BankNumberFragment
import com.example.sber_practika.activity.cabinet.transfer.util.BeautifulOutput.beautifulBalance
import com.example.sber_practika.activity.cabinet.transfer.util.BeautifulOutput.beautifulIdBankCard
import com.example.sber_practika.utils.HideKeyboardClass
import com.example.sber_practika.utils.ShowToast

class TransferBankCardActivity : AppCompatActivity() {
    private lateinit var btnTransfer : Button
    private lateinit var layoutBankCards : LinearLayout
    private var selectedBankCard : String? = null

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
            ShowToast.show(baseContext, selectedBankCard)
        }
    }

    private fun initComponents() {
        btnTransfer = findViewById(R.id.btnTransferBankCard)
        layoutBankCards = findViewById(R.id.layout_of_bank_cards)

        supportFragmentManager.beginTransaction()
            .add(R.id.containerTransferBankCard,
                when(intent.getIntExtra("method", 1)){
                    1 -> BankNumberFragment()
                    2 -> BankCardFragment()
                    else -> PhoneFragment()
                })
            .commit()

        User.listCards.forEach { card ->
            onAddField(card.idCard, card.nameCard, card.dateCard,card.balanceCard.toString())
        }
    }


    private fun onAddField(bankCardId: String, nameOfUser: String,
                           dateOfEnd: String, balance: String) {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView: View = inflater.inflate(R.layout.field_bank_card, null)

        val mainLayout = rowView.findViewById<LinearLayout>(R.id.layout_of_bank_card)

        rowView.findViewById<TextView>(R.id.textview_bank_card_id).text = beautifulIdBankCard(bankCardId)
        rowView.findViewById<TextView>(R.id.textview_name_of_user).text = nameOfUser
        rowView.findViewById<TextView>(R.id.textivew_date_of_end).text = dateOfEnd
        rowView.findViewById<TextView>(R.id.textview_bank_card_balance).text = beautifulBalance(balance) + " р."

        mainLayout.setOnClickListener {
            ShowToast.show(baseContext, "Выбрана карта : " + beautifulIdBankCard(bankCardId))
            selectedBankCard = bankCardId
        }

        layoutBankCards.addView(rowView)
    }
}