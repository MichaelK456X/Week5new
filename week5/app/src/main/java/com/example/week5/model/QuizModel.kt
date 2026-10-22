package com.example.week5.model

data class Soal(
    val teks: String,
    val pilihan: List<String>,
    val jawabanBenar: String
)

data class JawabanUser(
    val soal: Soal,
    val jawabanDipilih: String
)
