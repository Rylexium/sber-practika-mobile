package com.example.sber_practika.activity.cabinet.transactions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.example.sber_practika.R
import com.example.sber_practika.activity.cabinet.entity.User
import com.example.sber_practika.utils.Fields

class TransactionTransferActivity : AppCompatActivity() {
    private lateinit var layoutContainer : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_transfer)
        initComponents()
        applyEvents()
    }

    private fun applyEvents() {

    }

    private fun initComponents() {
        layoutContainer = findViewById(R.id.layout_of_bank_cards_and_bank_number)
        Fields.onAddBankNumberField(this, layoutContainer, true)
        User.listCards.forEach { card ->
            Fields.onAddBankCardField(card.idCard, card.nameCard, card.dateCard,card.balanceCard.toString(),
                this, layoutContainer, true)
        }
    }
}