package com.example.sber_practika.activity.cabinet.entity


object User {
    lateinit var bankNumber: String
    lateinit var username: String
    lateinit var family: String
    lateinit var name: String
    lateinit var patronymic: String
    lateinit var email: String
    lateinit var address: String
    lateinit var phone: String
    lateinit var dateOfBirthday: String
    lateinit var balanceBank: String
    lateinit var jwt : String
    var listCards : MutableList<BankCard> = ArrayList()

    fun init(bankNumber : String,
             username : String,
             family : String,
             name  : String,
             patronymic  : String,
             email  : String,
             address  : String,
             phone  : String,
             dateOfBirthday  : String,
             balanceBank  : String,
             jwt : String) {
        User.bankNumber = bankNumber
        User.username = username
        User.family = family
        User.name = name
        User.patronymic = patronymic
        User.email = email
        User.address = address
        User.phone = phone
        User.dateOfBirthday = dateOfBirthday
        User.balanceBank = balanceBank
        User.jwt = jwt
    }

    fun clearData() {
        bankNumber = ""
        username = ""
        family = ""
        name = ""
        patronymic = ""
        email = ""
        address = ""
        phone = ""
        dateOfBirthday = ""
        balanceBank = ""
    }
}