package com.example.sber_practika.activity.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.LinearLayout
import androidx.lifecycle.lifecycleScope
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.example.sber_practika.R
import com.example.sber_practika.activity.auth.LoginActivity
import com.example.sber_practika.utils.ShowToast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONArray
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.random.Random

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var layoutSplash : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initComponents()
    }

    private fun initComponents() {
        layoutSplash = findViewById(R.id.layout_splash)
        layoutSplash.alpha = 0f
        layoutSplash.animate().setDuration(Random.nextInt(400, 700).toLong()).alpha(1f).withEndAction {
            lifecycleScope.launch {
                run tryToConnect@{
                    repeat(1001) {
                        if (awaitConnect()) return@tryToConnect
                        if (it % 7 == 0) Handler(Looper.getMainLooper()).post {
                            ShowToast.show(this@SplashActivity, "Проверьте подключение к интернету")
                        }
                        delay(1000)
                    }
                }
                delay(500)
                Handler(Looper.getMainLooper()).post {
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                    finish()
                }
            }
        }
    }

    private suspend fun awaitConnect() : Boolean {
        return suspendCoroutine {
            AndroidNetworking.get("https://sber-practika.herokuapp.com/hello")
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsJSONArray(object : JSONArrayRequestListener {
                    override fun onResponse(response: JSONArray) { it.resume(true) }
                    override fun onError(anError: ANError?) { it.resume(true) }
                })
        }

    }

}