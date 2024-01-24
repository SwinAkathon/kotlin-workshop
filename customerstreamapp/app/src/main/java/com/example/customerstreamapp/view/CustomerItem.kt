package com.example.customerstreamapp.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.customerstreamapp.model.Customer

@Composable
fun CustomerItem(customer: Customer, onCheckedChange: (Boolean) -> Unit) {
  var isChecked by remember { mutableStateOf(false) }

  Row(
    modifier = Modifier.fillMaxWidth().padding(8.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Column(
      modifier = Modifier.weight(1f).padding(start = 8.dp)
    ) {
      Text(text = "ID: ${customer.id}", fontWeight = FontWeight.Bold)
      Text(text = "Name: ${customer.name}")
    }
    Checkbox(
      checked = isChecked,
      onCheckedChange = {
        isChecked = it
        onCheckedChange(it)
      }
    )
  }
}