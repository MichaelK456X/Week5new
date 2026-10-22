package com.example.week5.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.week5.viewmodel.QuizViewModel

@Composable
fun Quiz(viewModel: QuizViewModel) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        when (viewModel.layarSekarang) {
            "QUIZ" -> QuizAwal(viewModel)
            "RESULT" -> QuizHasil(viewModel)
        }
    }
}

@Composable
fun QuizAwal(viewModel: QuizViewModel) {
    val soal = viewModel.soalSekarang ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Question ${viewModel.indeksSoalSekarang + 1}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray
        )

        Text(
            text = soal.teks,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 10.dp, bottom = 32.dp)
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            soal.pilihan.forEach { opsi ->
                val apakahDipilih = viewModel.jawabanDipilih == opsi

                Button(
                    onClick = { viewModel.jawabanDipilih = opsi },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .focusProperties { canFocus = false },
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (apakahDipilih) Color(0xFF3F51B5) else Color(0xFFEBEBEB),
                        contentColor = if (apakahDipilih) Color.White else Color.DarkGray
                    )
                ) {
                    Text(text = opsi, fontSize = 12.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(35.dp))

        Button(
            onClick = { viewModel.soalBerikutnya() },
            enabled = viewModel.jawabanDipilih.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3F51B5),
                disabledContainerColor = Color(0xFFE0E0E0)
            )
        ) {
            Text("Next")
        }
    }
}

@Composable
fun QuizHasil(viewModel: QuizViewModel) {
    val jawabanSalah = viewModel.historyJawaban.filter { it.jawabanDipilih != it.soal.jawabanBenar }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(22.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            "Quiz Finished!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            "Your Score: ${viewModel.totalBenar} / ${viewModel.daftarSoal.size}",
            modifier = Modifier.padding(vertical = 7.dp)
        )

        Spacer(modifier = Modifier.height(18.dp))

        if (jawabanSalah.isEmpty()) {
            Text(
                "Perfect! All answers are correct.",
                color = Color(0xFF3F51B5),
                modifier = Modifier.padding(vertical = 15.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
        } else {
            LazyColumn(modifier = Modifier
                .weight(1f)
                .fillMaxWidth()) {
                items(jawabanSalah) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
                    ) {
                        Column(modifier = Modifier.padding(15.dp)) {
                            Text(
                                text = item.soal.teks,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                            Text(
                                text = "Your Answer: ${item.jawabanDipilih}",
                                color = Color.Red,
                                fontSize = 13.sp,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                            Text(
                                text = "Correct Answer: ${item.soal.jawabanBenar}",
                                color = Color(0xFF2E7D32),
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }

        Button(
            onClick = { viewModel.restart() },
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F51B5))
        ) {
            Text("Restart Quiz")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuizAppPreview() {
    val mockViewModel: QuizViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    Quiz(viewModel = mockViewModel)
}
