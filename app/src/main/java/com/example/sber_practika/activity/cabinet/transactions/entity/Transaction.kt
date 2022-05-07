package com.example.sber_practika.activity.cabinet.transactions.entity

data class Transaction(val uuid : String,
                       val bankNumber1 : String, val bankNumber2: String,
                       val bankCard1 : String, val bankCard2 : String,
                       val value : String, val date : String)
