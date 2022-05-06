package com.example.sber_practika.activity.auth.utils

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.sber_practika.utils.HashPass
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import org.json.JSONObject
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object AuthService {
    suspend fun authByUsername(username: String?, password: String?) : JsonNode? {
        return suspendCoroutine {
            AndroidNetworking.post("https://sber-practika.herokuapp.com/authentication/username")
                .setPriority(Priority.IMMEDIATE)
                .setOkHttpClient(
                    OkHttpClient.Builder()
                        .connectTimeout(2, TimeUnit.SECONDS)
                        .build())
                .addJSONObjectBody(JSONObject()
                    .put("username", username)
                    .put("password", HashPass.sha256(password)))
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        try {
                            it.resume(ObjectMapper().readTree(response.toString()))
                        } catch (e: Exception) {
                            it.resume(null)
                        }
                    }

                    override fun onError(error: ANError) {
                        it.resume(null)
                    }
                })
        }
    }

    suspend fun authByPhone(phone: String?, password: String?) : JsonNode? {
        return suspendCoroutine {
            AndroidNetworking.post("https://sber-practika.herokuapp.com/authentication/phone")
                .setPriority(Priority.IMMEDIATE)
                .setOkHttpClient(
                    OkHttpClient.Builder()
                        .connectTimeout(2, TimeUnit.SECONDS)
                        .build()
                )
                .addJSONObjectBody(JSONObject()
                    .put("phone", phone)
                    .put("password", HashPass.sha256(password)))
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        try {
                            it.resume(ObjectMapper().readTree(response.toString()))
                        } catch (e: Exception) {
                            it.resume(null)
                        }
                    }

                    override fun onError(error: ANError) {
                        it.resume(null)
                    }
                })
        }
    }
    suspend fun authByBankCard(bankCard: String?, password: String?) : JsonNode? {
        return suspendCoroutine {
            AndroidNetworking.post("https://sber-practika.herokuapp.com/authentication/bankCard")
                .setPriority(Priority.IMMEDIATE)
                .setOkHttpClient(
                    OkHttpClient.Builder()
                        .connectTimeout(2, TimeUnit.SECONDS)
                        .build()
                )
                .addJSONObjectBody(JSONObject()
                    .put("bankCard", bankCard)
                    .put("password", HashPass.sha256(password)))
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        try {
                            it.resume(ObjectMapper().readTree(response.toString()))
                        } catch (e: Exception) {
                            it.resume(null)
                        }
                    }

                    override fun onError(error: ANError) {
                        it.resume(null)
                    }
                })
        }

    }
}