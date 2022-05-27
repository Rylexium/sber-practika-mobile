package com.example.sber_practika.activity.cabinet.transactions.controllers

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.sber_practika.activity.cabinet.entity.User
import com.example.sber_practika.activity.cabinet.entity.Transaction
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import org.json.JSONObject
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object TransactionTransferController {

    suspend fun downloadTransactionTransferByBankNumber(bankNumber : String) : ArrayList<Transaction>? {
        return downloadTransactionTransfer("bankNumber", bankNumber)
    }

    suspend fun downloadTransactionTransferByBankCard(bankCard : String) : ArrayList<Transaction>? {
        return downloadTransactionTransfer("bankCard", bankCard)
    }

    private suspend fun downloadTransactionTransfer(method : String, param : String) : ArrayList<Transaction>? {
        return suspendCoroutine {
            AndroidNetworking.get("https://sber-practika.herokuapp.com/api/transaction/$method?id=$param")
                .setPriority(Priority.IMMEDIATE)
                .setOkHttpClient(
                    OkHttpClient.Builder()
                        .connectTimeout(2, TimeUnit.SECONDS)
                        .build()
                )
                .addHeaders("Authorization", "Bearer " + User.jwt)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        val jsonNode = ObjectMapper().readTree(response.toString())
                        val res = ArrayList<Transaction>()
                        jsonNode["transactions"].forEach { item ->
                            res.add(
                                Transaction(item["uuid"].asText(),
                                    item["senderBankNumber"].asText(),
                                    item["recipientBankNumber"].asText(),
                                    item["senderBankCard"].asText(),
                                    item["recipientBankCard"].asText(),
                                    item["value"].asText(),
                                    item["date"].asText())
                            )
                        }
                        it.resume(res)
                    }

                    override fun onError(anError: ANError?) {
                        it.resume(null)
                    }
                })
        }
    }
}