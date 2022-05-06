package com.example.sber_practika.activity.cabinet.transfer.util

object BeautifulOutput {
    fun beautifulIdBankCard(bankCardId : String) : String {
        var res = ""
        for(i in bankCardId.indices){
            res += bankCardId[i]
            if((i+1) % 4 == 0) res += " "
        }
        return res
    }

    fun beautifulBalance(balance : String) : String {
        var res = ""
        val revBalance = balance.reversed()
        for(i in revBalance.indices){
            res += revBalance[i]
            if((i+1) % 3 == 0) res += " "
        }
        return res.reversed()
    }
}