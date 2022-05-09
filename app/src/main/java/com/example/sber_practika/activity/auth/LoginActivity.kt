package com.example.sber_practika.activity.auth

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.lifecycleScope
import android.view.MotionEvent
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.sber_practika.R
import com.example.sber_practika.activity.auth.fragments.BankCardFragment
import com.example.sber_practika.activity.auth.fragments.PhoneFragment
import com.example.sber_practika.activity.auth.fragments.LoginByUsernameFragment
import com.example.sber_practika.activity.cabinet.CabinetActivity
import com.example.sber_practika.activity.auth.utils.AuthService
import com.example.sber_practika.activity.cabinet.entity.BankCard
import com.example.sber_practika.activity.cabinet.entity.Transactions
import com.example.sber_practika.activity.cabinet.entity.User
import com.example.sber_practika.utils.HideKeyboardClass
import com.example.sber_practika.utils.ShowToast
import com.fasterxml.jackson.databind.JsonNode
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var loginByUsername : TextView
    private lateinit var loginByPhone : TextView
    private lateinit var loginByBankCard : TextView
    private lateinit var rememberPassword : TextView
    private lateinit var password: EditText
    private lateinit var buttonLogin : Button
    private var methodAuth : Int = 1

    companion object {
        var pass : String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initComponents()
        applyEvents()
    }

    override fun onResume() {
        super.onResume()
        password.setText("")
        pass = ""
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            HideKeyboardClass.hideKeyboard(this)
            currentFocus!!.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun applyEvents() {
        buttonLogin.setOnClickListener {
            val param = when (methodAuth) {
                1 -> findViewById<EditText>(R.id.edittext_phone)?.text.toString()
                2 -> findViewById<EditText>(R.id.edittext_username)?.text.toString()
                else -> findViewById<EditText>(R.id.edittext_bankcard)?.text.toString()
            }
            auth(param, password.text.toString())
        }
        loginByPhone.setOnClickListener { selectMethodAuth(1) }
        loginByUsername.setOnClickListener { selectMethodAuth(2) }
        loginByBankCard.setOnClickListener { selectMethodAuth(3) }
        rememberPassword.setOnClickListener { }
    }

    private fun selectMethodAuth(method : Int) {
        when (method) {
            1 -> {
                loginByPhone.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_check_24, 0, R.drawable.ic_baseline_invisible_24, 0)
                loginByUsername.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                loginByBankCard.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)

                supportFragmentManager.beginTransaction()
                    .replace(R.id.containerLogin, PhoneFragment())
                    .commit()
            }
            2 -> {
                loginByPhone.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                loginByUsername.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_check_24, 0, R.drawable.ic_baseline_invisible_24, 0)
                loginByBankCard.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)

                supportFragmentManager.beginTransaction()
                    .replace(R.id.containerLogin, LoginByUsernameFragment())
                    .commit()
            }
            else -> {
                loginByPhone.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                loginByUsername.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                loginByBankCard.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_check_24, 0, R.drawable.ic_baseline_invisible_24, 0)

                supportFragmentManager.beginTransaction()
                    .replace(R.id.containerLogin, BankCardFragment())
                    .commit()
            }
        }
        this.currentFocus?.clearFocus()
        methodAuth = method
    }

    private fun auth(param : String, password : String) {
        if(param == "" || password == ""
            || param.length <= 2 || password.length <= 2 ) return
        var jsonNode : JsonNode?
        lifecycleScope.launch {
            val res = param.replace(" ", "").replace("-", "")
            jsonNode = when (methodAuth) {
                1 -> AuthService.authByPhone(res, password) //phone
                2 -> AuthService.authByUsername(res, password) //username
                else -> AuthService.authByBankCard(res, password) //bankcard
            }
            if(jsonNode == null) {
                Handler(Looper.getMainLooper()).post { ShowToast.show(baseContext, "Проблемы с сервером") }
                return@launch
            }

            if(jsonNode!!["status"].asText() == "Invalid date or password") {
                Handler(Looper.getMainLooper()).post { ShowToast.show(baseContext, "Неверный логин или пароль") }
                return@launch
            }

            pass = password

            Handler(Looper.getMainLooper()).post { ShowToast.show(baseContext, "Успешная авторизация") }
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
            timerUntilExit(jsonNode!!["expired"].asLong())
            startActivity(Intent(this@LoginActivity, CabinetActivity::class.java))

        }
    }
    private fun timerUntilExit(miles : Long) {
        Thread {
            Thread.sleep(miles)
            Handler(Looper.getMainLooper()).post {
                startActivity(Intent(this, LoginActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                finish()
                BankCardFragment.bankCard = ""
                LoginByUsernameFragment.username = ""
                PhoneFragment.phone = ""
                password.setText("")
                User.clearData()
                Transactions.clearData()
            }
        }.start()
    }


    private fun initComponents(){
        loginByBankCard = findViewById(R.id.textLoginByBankCard)
        loginByUsername = findViewById(R.id.textLoginByUsername)
        loginByPhone = findViewById(R.id.textLoginByPhone)
        rememberPassword = findViewById(R.id.textRememberPassword)

        password = findViewById(R.id.edittext_password)

        buttonLogin = findViewById(R.id.button_login)

        selectMethodAuth(2)
        supportFragmentManager.beginTransaction()
            .add(R.id.containerLogin, LoginByUsernameFragment())
            .commit()
    }
}