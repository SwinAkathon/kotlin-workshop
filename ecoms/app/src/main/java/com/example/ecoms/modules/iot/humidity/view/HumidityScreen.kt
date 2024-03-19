package com.example.ecoms.modules.iot.humidity.view

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.ecoms.AppConfig
import com.example.ecoms.common.ui.UIKit
import com.example.ecoms.common.ui.UIKit.ErrorItem
import com.example.ecoms.common.ui.UIKit.LabelledDivider
import com.example.ecoms.common.ui.UIKit.LoadingItem
import com.example.ecoms.common.ui.UIKit.LoadingView
import com.example.ecoms.common.ui.UIKit.SimplePageDivider
import com.example.ecoms.modules.iot.humidity.dao.HumidityPagingSource
import com.example.ecoms.ui.theme.EcomsTheme

private lateinit var myViewModel: HumidityViewModel
private val LTAG = "HumidityScreen"

@Composable
fun HumidityScreen(navController: NavController) {
  val surfaceHeight = 1f
  val context: Context = navController.context

  // initialise ViewModel (once)
  var viewModelOk by remember { mutableStateOf(true)}

  if (!::myViewModel.isInitialized) {
    //myViewModel = viewModel()
    myViewModel = viewModel(
      navController.getViewModelStoreOwner(navController.graph.id))
    //    Log.d(LTAG, "view model retrieved: ${myViewModel} (${myViewModel.hashCode()})")

    try {
      val pageSrc = HumidityPagingSource(context.filesDir.path)
      pageSrc.listen()
      myViewModel.setPagingDataSource(pageSrc)
    } catch (e: Exception) {
      viewModelOk = false
      notify(context, "Error: ${e.message}")
      e.printStackTrace()
    }
  }

  EcomsTheme {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .border(width = 1.dp, color = AppConfig.colorContentBorder)
        .padding(10.dp), contentAlignment = Alignment.TopCenter
    ) {
      // A surface container using the 'background' color from the theme
      Surface(
        modifier = Modifier
          .fillMaxWidth(surfaceHeight)
          .wrapContentHeight()
        ,color = MaterialTheme.colorScheme.secondaryContainer
      ) {
        Column {
          // title
          Text("Humidity",
            color = Color.Blue,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            fontSize = TextUnit(32f, TextUnitType.Sp))
          // content
          if (viewModelOk) HumidityItemsScreen(context, myViewModel)
        }
      }
    }
  }
}

@Composable
fun HumidityItemsScreen(context: Context,
                       viewModel: HumidityViewModel,
                       maxWidth: Float = 1f,
                       maxHeight: Float = 1f
) {
  // incrementally loads the item
  val pagingItems = viewModel.getFlow().collectAsLazyPagingItems()

  Row(modifier = Modifier.padding(bottom=5.dp)) {
    // use row to add padding to the next item on the same surface
    LazyColumn(
      // not yet supported:    modifier = Modifier.verticalScroll(scrollState, reverseScrolling = true),
      modifier = Modifier
        .fillMaxWidth(maxWidth)
        .fillMaxHeight(maxHeight)
//        .border(width=1.dp,color= Color.Red)
      ,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      items(pagingItems.itemCount) { index ->
        // add page divider
        if (index % viewModel.pagingConfig.pageSize == 0) {
          LabelledDivider(label = ((index / viewModel.pagingConfig.pageSize)+1).toString()
            , fontSize = 24.sp)
        }
        // display the item
        pagingItems[index]?.let {
          HumidityItem(humidity = it, onCheckedChange = { isChecked ->
            // todo: Handle humidity selection
            notify(context, it.toString())
          })
        }
      } // end items

      // handle load state to display progress
      pagingItems.apply {
        when {
          loadState.refresh is LoadState.Loading -> {
            // Display loading at the start (initial page)
            // usually takes longer than normal because initial load size is 3 * pageSize
            item { LoadingView() }
          }

          loadState.append is LoadState.Loading -> {
            // Display loading at the end of the list (while wating for more items)
            item { LoadingItem() }
          }

          loadState.refresh is LoadState.Error -> {
            val e = pagingItems.loadState.refresh as LoadState.Error
            item { ErrorItem(e.error) }
          }
        }
      } // end: handle load state
    }
  }
}

fun notify(context: Context, msg: String) {
  Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
}
