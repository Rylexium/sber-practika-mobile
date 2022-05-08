package com.example.sber_practika.activity.cabinet.entity

object Transactions {
    var listTransactionsOfBankNumber: ArrayList<Transaction> = ArrayList()
    var listTransactionsOfBankCard: MutableMap<String, ArrayList<Transaction>?> = HashMap()

    fun clearData() {
        listTransactionsOfBankNumber.clear()
        listTransactionsOfBankCard.clear()
    }
}