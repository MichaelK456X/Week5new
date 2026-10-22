package com.example.week5.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.week5.model.Soal
import com.example.week5.model.JawabanUser

class QuizViewModel : ViewModel() {

    private val semuaSoal = listOf(
        Soal("Siapakah ilmuwan terkenal yang mencetuskan Teori Relativitas?", listOf("Isaac Newton", "Albert Einstein", "Galileo Galilei", "Nikola Tesla"), "Albert Einstein"),
        Soal("Gas apakah yang paling banyak terkandung di dalam atmosfer bumi kita?", listOf("Oksigen", "Hidrogen ", "Karbondioksida", "Nitrogen"), "Nitrogen"),
        Soal("Garis khayal yang membagi bumi secara horizontal menjadi belahan utara dan selatan dinamakan garis?", listOf("Meridian", "Bujur", "Khatulistiwa", "Greenwich"), "Khatulistiwa"),
        Soal("Ada berapa hari dalam seminggu?", listOf("5", "6", "7", "8"), "7"),
        Soal("Benua manakah yang memiliki wilayah gurun pasir terluas di dunia?", listOf("Benua Afrika", "Benua Asia", "Benua Amerika", "Benua Australia"), "Benua Afrika")
    )

    var layarSekarang by mutableStateOf("QUIZ")
    var daftarSoal by mutableStateOf<List<Soal>>(emptyList())
    var indeksSoalSekarang by mutableStateOf(0)
    var jawabanDipilih by mutableStateOf("")
    val historyJawaban = mutableListOf<JawabanUser>()

    init {
        buatSoalAcak()
    }

    val soalSekarang: Soal?
        get() = daftarSoal.getOrNull(indeksSoalSekarang)

    val totalBenar: Int
        get() = historyJawaban.count { it.jawabanDipilih == it.soal.jawabanBenar }

    private fun buatSoalAcak() {
        daftarSoal = semuaSoal.shuffled().take(3)
    }

    fun soalBerikutnya() {
        val s = soalSekarang ?: return
        historyJawaban.add(JawabanUser(s, jawabanDipilih))

        if (indeksSoalSekarang + 1 < daftarSoal.size) {
            indeksSoalSekarang++
            jawabanDipilih = ""
        } else {
            layarSekarang = "RESULT"
        }
    }

    fun restart() {
        buatSoalAcak()
        indeksSoalSekarang = 0
        jawabanDipilih = ""
        historyJawaban.clear()
        layarSekarang = "QUIZ"
    }
}
