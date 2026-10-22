package com.example.week5.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.week5.ui.theme.Week5Theme
import com.example.week5.viewmodel.PengeluaranViewModel

@Composable
fun PengeluaranView(viewModel: PengeluaranViewModel) {
    var inputNama by remember { mutableStateOf("") }
    var inputJumlah by remember { mutableStateOf("") }
    var Error by remember { mutableStateOf<String?>(null) }
    var editId by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(14.dp)) {
        Text("Pengeluaran", style = MaterialTheme.typography.headlineMedium)
        Text(
            text = "Total Pengeluaran : Rp ${String.format("%,.0f", viewModel.totalPengeluaran)}",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 10.dp)
        )

        OutlinedTextField(
            value = inputNama,
            onValueChange = { inputNama = it },
            placeholder = { Text("Nama Pengeluaran") },
            enabled = editId == null,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(7.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputJumlah,
                onValueChange = { inputJumlah = it },
                placeholder = { Text("Jumlah Pengeluaran") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.width(7.dp))

            SmallFloatingActionButton(
                onClick = {
                    Error = viewModel.Simpan(editId, inputNama, inputJumlah)
                    if (Error == null) {
                        inputNama = ""
                        inputJumlah = ""
                        editId = null
                    }
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Save")
            }
        }

        Error?.let {
            Text(it, color = Color.Red, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 3.dp))
        }

        Spacer(modifier = Modifier.height(13.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(viewModel.daftarPengeluaran) { item ->
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Name : ${item.nama}")
                            Text("Rp ${String.format("%,.0f", item.jumlah)}", color = Color.Gray)
                        }
                        Row {
                            IconButton(onClick = {
                                editId = item.id
                                inputNama = item.nama
                                inputJumlah = item.jumlah.toInt().toString()
                            }) {
                                Icon(
                                    Icons.Default.Edit,
                                    contentDescription = "edit",
                                    tint = Color(0xFFFFB300)
                                )
                            }
                            IconButton(onClick = { viewModel.hapusPengeluaran(item) }) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Hapus",
                                    tint = Color.Red
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PengeluaranViewPreview() {
    val viewModel: PengeluaranViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    Week5Theme {
        PengeluaranView(viewModel = viewModel)
    }
}
