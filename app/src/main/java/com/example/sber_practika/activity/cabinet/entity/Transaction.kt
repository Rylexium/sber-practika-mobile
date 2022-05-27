package com.example.sber_practika.activity.cabinet.entity

data class Transaction(val uuid : String,
                       val senderBankNumber : String, val recipientBankNumber: String,
                       val senderBankCard : String, val recipientBankCard : String,
                       val value : String, val date : String)
