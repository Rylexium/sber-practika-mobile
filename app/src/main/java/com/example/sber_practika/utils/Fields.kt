package com.example.sber_practika.utils

import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.sber_practika.R
import com.example.sber_practika.activity.cabinet.entity.User
import com.example.sber_practika.activity.cabinet.transfer.TransferBankCardActivity.Companion.selectedBankCard
import com.example.sber_practika.activity.cabinet.transfer.util.BeautifulOutput

object Fields {

    fun onAddBankCardField(bankCardId: String, nameOfUser: String,
                           dateOfEnd: String, balance: String, activity: AppCompatActivity, layout : LinearLayout) {
        val inflater = activity.getSystemService(AppCompatActivity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView: View = inflater.inflate(R.layout.field_bank_card, null)

        val mainLayout = rowView.findViewById<LinearLayout>(R.id.layout_of_bank_card)

        rowView.findViewById<TextView>(R.id.textview_bank_card_id).text =
            BeautifulOutput.beautifulIdBankCard(bankCardId)
        rowView.findViewById<TextView>(R.id.textview_name_of_user).text = nameOfUser
        rowView.findViewById<TextView>(R.id.textivew_date_of_end).text = dateOfEnd
        rowView.findViewById<TextView>(R.id.textview_bank_card_balance).text = BeautifulOutput.beautifulBalance(balance) + " р."

        mainLayout.setOnClickListener {
            ShowToast.show(activity.baseContext, "Выбрана карта : " + BeautifulOutput.beautifulIdBankCard(bankCardId))
            selectedBankCard = bankCardId
        }

        layout.addView(rowView)
    }

    fun onAddBankNumberField(activity: AppCompatActivity, layout : LinearLayout) {
        val inflater = activity.getSystemService(AppCompatActivity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView: View = inflater.inflate(R.layout.field_bank_number, null)

        val mainLayout = rowView.findViewById<LinearLayout>(R.id.layout_of_bank_number)

        rowView.findViewById<TextView>(R.id.textview_bank_number).text = User.bankNumber
        rowView.findViewById<TextView>(R.id.textview_phone).text = User.phone
        rowView.findViewById<TextView>(R.id.textview_bank_number_balance).text = BeautifulOutput.beautifulBalance(User.balanceBank) + " р."

        mainLayout.setOnClickListener {
            ShowToast.show(activity.baseContext, "Выбран банковский счёт")
        }

        layout.addView(rowView, 0)
    }

    fun onAddTransactionTransferField(idTransfer: String, fromTransaction: String,
                                      whereTransaction: String, valueTransaction: String,
                                      dateTransaction : String,
                                      activity: AppCompatActivity, layout : LinearLayout) {
        val inflater = activity.getSystemService(AppCompatActivity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView: View = inflater.inflate(R.layout.field_of_transfer_transaction, null)

        val mainLayout = rowView.findViewById<LinearLayout>(R.id.layout_of_transaction_transfer)

        rowView.findViewById<TextView>(R.id.textview_id_transaction).text = idTransfer
        rowView.findViewById<TextView>(R.id.textview_from_transaction).text = fromTransaction
        rowView.findViewById<TextView>(R.id.textview_where_transaction).text = whereTransaction
        rowView.findViewById<TextView>(R.id.textview_value_transaction).text = valueTransaction
        rowView.findViewById<TextView>(R.id.textview_date_transaction).text = dateTransaction


        mainLayout.setOnClickListener {
            ShowToast.show(activity.baseContext, idTransfer)
        }

        layout.addView(rowView, 0)
    }
}