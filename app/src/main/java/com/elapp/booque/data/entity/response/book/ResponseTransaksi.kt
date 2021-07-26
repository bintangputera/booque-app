package com.elapp.booque.data.entity.response.book

import com.elapp.booque.data.entity.transaction.Transaksi

data class ResponseTransaksi(
    var error: Int,
    var msg: List<Transaksi>
)