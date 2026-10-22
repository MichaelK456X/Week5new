package com.example.week5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.week5.ui.PengeluaranView
import com.example.week5.ui.Quiz
import com.example.week5.ui.theme.Week5Theme
import com.example.week5.viewmodel.PengeluaranViewModel
import com.example.week5.viewmodel.QuizViewModel

class MainActivity : ComponentActivity() {
    private val pengeluaranViewModel: PengeluaranViewModel by viewModels()
    private val quizViewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Week5Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    // ini buat Soal Quiz
                    Quiz(viewModel = quizViewModel)
                    // ini buat Soal Pengeluaran
                    // PengeluaranView(viewModel = pengeluaranViewModel)
                }
            }
        }
    }
}
