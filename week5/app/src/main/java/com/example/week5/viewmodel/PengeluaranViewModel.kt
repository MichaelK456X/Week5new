package com.example.week5.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.week5.model.PengeluaranModel

class PengeluaranViewModel : ViewModel() {
    val daftarPengeluaran = mutableStateListOf<PengeluaranModel>()

    val totalPengeluaran: Double
        get() = daftarPengeluaran.sumOf { it.jumlah }

    fun Simpan(idAkanDiedit: String?, nama: String, jumlahText: String): String? {
        if (nama.isBlank()) return "Nama pengeluaran tidak boleh kosong"

        val jumlahBener = jumlahText.replace(".", "").replace(",", "").trim()
        val jumlah = jumlahBener.toDoubleOrNull()
        if (jumlah == null || jumlah <= 0) return "Harus lebih besar dari 0"

        if (idAkanDiedit == null) {
            daftarPengeluaran.add(PengeluaranModel(nama = nama, jumlah = jumlah))
        } else {
            val indeks = daftarPengeluaran.indexOfFirst { it.id == idAkanDiedit }
            if (indeks != -1) {
                val sekarang = daftarPengeluaran[indeks]
                daftarPengeluaran[indeks] = sekarang.copy(nama = nama, jumlah = jumlah)
            }
        }
        return null
    }

    fun hapusPengeluaran(pengeluaran: PengeluaranModel) {
        daftarPengeluaran.remove(pengeluaran)
    }
}
