package com.example.sber_practika.activity.cabinet.transfer.controllers

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.sber_practika.activity.cabinet.CabinetActivity
import com.example.sber_practika.activity.cabinet.entity.User
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import org.json.JSONObject
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object TransferBankCardController {

    suspend fun transferToBankNumber (bankCard : String, bankNumber : String, value : String) : String {
        return suspendCoroutine {
            AndroidNetworking.post("https://sber-practika.herokuapp.com/api/bankCard_to_bankNumber")
                .setPriority(Priority.IMMEDIATE)
                .setOkHttpClient(
                    OkHttpClient.Builder()
                        .connectTimeout(2, TimeUnit.SECONDS)
                        .build())
                .addHeaders("Authorization", "Bearer " + User.jwt)
                .addJSONObjectBody(
                    JSONObject()
                    .put("bankCard", bankCard)
                    .put("bankNumber", bankNumber)
                    .put("value", value))
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        it.resume(ObjectMapper().readTree(response.toString())["message"].asText())
                    }

                    override fun onError(anError: ANError?) {
                        it.resume("Произошла ошибка")
                    }
                })
        }
    }

    suspend fun transferToBankCard (bankCard1 : String, bankCard2 : String, value : String) : String {
        return suspendCoroutine {
            AndroidNetworking.post("https://sber-practika.herokuapp.com/api/bankCard_to_bankCard")
                .setPriority(Priority.IMMEDIATE)
                .setOkHttpClient(
                    OkHttpClient.Builder()
                        .connectTimeout(2, TimeUnit.SECONDS)
                        .build())
                .addHeaders("Authorization", "Bearer " + User.jwt)
                .addJSONObjectBody(
                    JSONObject()
                        .put("bankCard1", bankCard1)
                        .put("bankCard2", bankCard2)
                        .put("value", value))
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        it.resume(ObjectMapper().readTree(response.toString())["message"].asText())
                    }

                    override fun onError(anError: ANError?) {
                        it.resume("Произошла ошибка")
                    }
                })
        }
    }

    suspend fun transferToPhone (bankCard : String, phone : String, value : String) : String {
        return suspendCoroutine {
            AndroidNetworking.post("https://sber-practika.herokuapp.com/api/bankCard_to_phone")
                .setPriority(Priority.IMMEDIATE)
                .setOkHttpClient(
                    OkHttpClient.Builder()
                        .connectTimeout(2, TimeUnit.SECONDS)
                        .build())
                .addHeaders("Authorization", "Bearer " + User.jwt)
                .addJSONObjectBody(
                    JSONObject()
                        .put("bankCard", bankCard)
                        .put("phone", phone)
                        .put("value", value))
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        it.resume(ObjectMapper().readTree(response.toString())["message"].asText())
                    }

                    override fun onError(anError: ANError?) {
                        it.resume("Произошла ошибка")
                    }
                })
        }
    }
}