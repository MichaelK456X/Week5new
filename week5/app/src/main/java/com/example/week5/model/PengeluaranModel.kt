package com.example.week5.model

import java.util.UUID

data class PengeluaranModel(
    val id: String = UUID.randomUUID().toString(),
    val nama: String,
    val jumlah: Double
)
