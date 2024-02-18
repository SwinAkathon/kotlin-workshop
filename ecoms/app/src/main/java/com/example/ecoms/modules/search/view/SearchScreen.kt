package com.example.ecoms.modules.search.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.ecoms.AppConfig
import com.example.ecoms.common.ui.KHeadingField

@Composable
fun SearchScreen(navController: NavController) {
  Column(
    modifier = Modifier.padding(AppConfig.paddingContent)
      .fillMaxWidth()
  ) {
    KHeadingField(
      value = "Search",
      modifier = Modifier.align(Alignment.CenterHorizontally)
    )
  }
}