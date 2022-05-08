package com.example.sber_practika.activity.cabinet

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.sber_practika.R
import com.example.sber_practika.activity.auth.LoginActivity
import com.example.sber_practika.activity.auth.LoginActivity.Companion.pass
import com.example.sber_practika.activity.auth.utils.AuthService
import com.example.sber_practika.activity.cabinet.entity.BankCard
import com.example.sber_practika.activity.cabinet.entity.User
import com.example.sber_practika.activity.cabinet.infoUser.InfoUserActivity
import com.example.sber_practika.activity.cabinet.transactions.TransactionTransferActivity
import com.example.sber_practika.activity.cabinet.transfer.TransferBankCardActivity
import com.example.sber_practika.activity.cabinet.transfer.TransferBankNumberActivity
import com.example.sber_practika.utils.ShowToast
import com.fasterxml.jackson.databind.JsonNode
import kotlinx.coroutines.launch


class CabinetActivity : AppCompatActivity() {
    private lateinit var btnBankNumberToBankNumber : Button
    private lateinit var btnBankNumberToBankCard : Button
    private lateinit var btnBankNumberToPhone : Button
    private lateinit var btnPersonalDataAndBankNumber : Button

    private lateinit var btnBankCardToBankNumber : Button
    private lateinit var btnBankCardToBankCard : Button
    private lateinit var btnBankCardToPhone : Button
    private lateinit var btnAllMyTransaction : Button

    private lateinit var btnWhoLikeMe : Button
    private lateinit var btnAboutMe : Button
    private var isFirst = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cabinet)
        initComponents()
        applyEvents()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        User.clearData()
    }

    override fun onResume() {
        super.onResume()
        if(isFirst){
            isFirst = false
            return
        }
        var jsonNode : JsonNode?
        lifecycleScope.launch {
            jsonNode = AuthService.authByPhone(User.phone, pass)
            User.clearData()
            User.init(
                jsonNode!!["body"]["bankNumber"].asText(),
                jsonNode!!["body"]["username"].asText(),
                jsonNode!!["body"]["family"].asText(),
                jsonNode!!["body"]["name"].asText(),
                jsonNode!!["body"]["patronymic"].asText(),
                jsonNode!!["body"]["email"].asText(),
                jsonNode!!["body"]["address"].asText(),
                jsonNode!!["body"]["phone"].asText(),
                jsonNode!!["body"]["dateOfBirthday"].asText(),
                jsonNode!!["body"]["balanceBank"].asText(),
                jsonNode!!["token"].asText())

            jsonNode!!["body"]["cardList"].forEach { card ->
                User.listCards.add(BankCard(card["id"].asText(), card["date"].asText(), card["name"].asText(), card["balance"].bigIntegerValue()))
            }

        }

    }

    private fun applyEvents() {
        btnBankNumberToBankNumber.setOnClickListener {
            startActivity(Intent(this@CabinetActivity, TransferBankNumberActivity::class.java)
                .putExtra("method", 1))
        }
        btnBankNumberToBankCard.setOnClickListener {
            startActivity(Intent(this@CabinetActivity, TransferBankNumberActivity::class.java)
                .putExtra("method", 2))
        }
        btnBankNumberToPhone.setOnClickListener {
            startActivity(Intent(this@CabinetActivity, TransferBankNumberActivity::class.java)
                .putExtra("method", 3))
        }

        btnBankCardToBankNumber.setOnClickListener {
            startActivity(Intent(this@CabinetActivity, TransferBankCardActivity::class.java)
                .putExtra("method", 1))
        }
        btnBankCardToBankCard.setOnClickListener {
            startActivity(Intent(this@CabinetActivity, TransferBankCardActivity::class.java)
                .putExtra("method", 2))
        }
        btnBankCardToPhone.setOnClickListener {
            startActivity(Intent(this@CabinetActivity, TransferBankCardActivity::class.java)
                .putExtra("method", 3))
        }
        btnAllMyTransaction.setOnClickListener {
            startActivity(Intent(this@CabinetActivity, TransactionTransferActivity::class.java))
        }
        btnPersonalDataAndBankNumber.setOnClickListener {
            startActivity(Intent(this@CabinetActivity, InfoUserActivity::class.java))
        }
    }

    private fun initComponents() {
        btnBankNumberToBankNumber = findViewById(R.id.btn_bankNumber_to_bankNumber)
        btnBankNumberToBankCard = findViewById(R.id.btn_bankNumber_to_bankCard)
        btnBankNumberToPhone = findViewById(R.id.btn_bankNumber_to_phone)
        btnPersonalDataAndBankNumber = findViewById(R.id.btn_personal_data_and_bankNumber)

        btnBankCardToBankNumber = findViewById(R.id.btn_bankCard_to_bankNumber)
        btnBankCardToBankCard = findViewById(R.id.btn_bankCard_to_bankCard)
        btnBankCardToPhone = findViewById(R.id.btn_bankCard_to_phone)
        btnAllMyTransaction = findViewById(R.id.btn_all_my_transaction)

        btnWhoLikeMe = findViewById(R.id.btn_who_like_me)
        btnAboutMe = findViewById(R.id.btn_about_me)
    }

}