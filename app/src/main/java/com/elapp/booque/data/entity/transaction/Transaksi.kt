package com.elapp.booque.data.entity.transaction

import com.google.gson.annotations.SerializedName

data class Transaksi(
    @SerializedName("transaction_date")
    var transactionDate: String,
    @SerializedName("book_name")
    var bookName: String,
    @SerializedName("full_name")
    var fullName: String,
    @SerializedName("buyer")
    var buyer: String
)