package com.example.sber_practika.activity.cabinet.infoUser

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import com.example.sber_practika.R
import com.example.sber_practika.activity.cabinet.entity.User
import com.example.sber_practika.utils.Fields

class InfoUserActivity : AppCompatActivity() {
    private lateinit var containerBankCards : LinearLayout
    private lateinit var containerBankNumber : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_user)
        initComponents()
    }

    @SuppressLint("SetTextI18n")
    private fun initComponents() {
        containerBankNumber = findViewById(R.id.containerForBankNumber)
        containerBankCards = findViewById(R.id.containerForBankCards)

        findViewById<TextView>(R.id.textview_username).text = User.username
        findViewById<TextView>(R.id.textview_full_name).text = User.family + " " + User.name + " " + (if(User.patronymic == "null") "" else User.patronymic)
        findViewById<TextView>(R.id.textview_email).text = if(User.email == "null") "<Пусто>" else User.email
        findViewById<TextView>(R.id.textview_address).text = User.address
        findViewById<TextView>(R.id.textview_date_of_birthday).text = if(User.dateOfBirthday == "null") "<Пусто>" else User.dateOfBirthday

        Fields.onAddBankNumberField(this, containerBankNumber, null)
        User.listCards.forEach { card ->
            Fields.onAddBankCardField(card.idCard, card.nameCard, card.dateCard,card.balanceCard.toString(),
                this, containerBankCards, null)
        }
    }
}