package com.example.ecoms.modules.product.view

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ecoms.modules.product.dao.ProductSource

@Composable
fun ProductItemsScreen(context: Context,
                       maxWidth: Float = 1f,
                       maxHeight: Float = 1f
) {
  val myItems = ProductSource.products

  Row(modifier = Modifier.padding(bottom = 5.dp)) {
    // use row to add padding to the next item on the same surface
    LazyColumn(
      modifier = Modifier
        .fillMaxWidth(maxWidth)
        .fillMaxHeight(maxHeight)
//        .border(width=1.dp,color= Color.Red)
      ,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      items(myItems.size) { index ->
        val product = myItems[index]
        ProductItem(product = product, onCheckedChange = { isChecked ->
          // todo: Handle product selection
          notify(context, product.toString())
        })
      }
    }
  }
}

fun notify(context: Context, msg: String) {
  Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}