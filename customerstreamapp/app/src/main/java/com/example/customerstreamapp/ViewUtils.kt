package com.example.customerstreamapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * @overview
 *  This is a utility class for stand-alone testing of composables.
 */
@Composable
fun ScrollableColumnExample() {
  var scrollState = rememberScrollState()
  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(
        state = scrollState,
        enabled=true,
        reverseScrolling = false
      )
  ) {
    repeat(20) {
      Text(
        text = "Item $it",
        modifier = Modifier
          .background(Color.Gray)
          .padding(16.dp)
      )
    }
  }
}

@Preview
@Composable
fun PreviewScrollableColumn() {
//  val scrollBarAdapter = rememberScrollbarAdapter(scrollState = rememberScrollbarState())
  Surface(
    modifier = Modifier
      .fillMaxHeight(0.5f)
//    ,color = MaterialTheme.colorScheme.secondaryContainer
  ) {
//    var scrollState = rememberScrollState()
    Column(
//      modifier = Modifier.verticalScroll(
//        scrollState
//      ),
      content = {
        ScrollableColumnExample()
      }
    )
  }
}
