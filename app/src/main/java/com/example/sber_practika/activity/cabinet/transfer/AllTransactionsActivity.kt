package com.example.sber_practika.activity.cabinet.transfer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.lifecycle.lifecycleScope
import com.example.sber_practika.R
import com.example.sber_practika.activity.cabinet.entity.Transaction
import com.example.sber_practika.activity.cabinet.entity.Transactions
import com.example.sber_practika.activity.cabinet.transfer.util.BeautifulOutput
import com.example.sber_practika.utils.Fields
import kotlinx.coroutines.launch

class AllTransactionsActivity : AppCompatActivity() {
    private lateinit var containerTransactions : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_transactions)
        initComponents()
    }

    private fun initComponents() {
        containerTransactions = findViewById(R.id.container_for_transaction)

        lifecycleScope.launch {
            var list: ArrayList<Transaction>? = null
            if (intent.getStringExtra("method") == "bankCard") {
                list = Transactions.listTransactionsOfBankCard[intent.getStringExtra("id")]
            } else if (intent.getStringExtra("method") == "bankNumber") {
                list = Transactions.listTransactionsOfBankNumber
            }
            list?.forEach { item ->
                val from =
                    if(item.bankNumber1 == "null") "Банковская карта : " + BeautifulOutput.beautifulIdBankCard(item.bankCard1)
                    else "Банковский счёт : " + item.bankNumber1
                val where =
                    if(item.bankNumber2 == "null") "Банковская карта : " + BeautifulOutput.beautifulIdBankCard(item.bankCard2)
                    else "Банковский счёт : " + item.bankNumber2
                val value = BeautifulOutput.beautifulBalance(item.value) + " р."
                val date = item.date.subSequence(0, 10).toString() + " " + item.date.subSequence(11, 19)
                Fields.onAddTransactionTransferField(item.uuid, from, where, value, date,
                    this@AllTransactionsActivity, containerTransactions)
            }
        }
    }
}