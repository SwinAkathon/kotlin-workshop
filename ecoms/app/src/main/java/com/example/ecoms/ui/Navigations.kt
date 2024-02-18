package com.example.ecoms.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ecoms.AppConfig
import com.example.ecoms.modules.dashboard.view.DashBoard
import com.example.ecoms.modules.favourites.view.FavouritesScreen
import com.example.ecoms.modules.product.view.ProductScreen
import com.example.ecoms.modules.profile.view.ProfileScreen
import com.example.ecoms.modules.search.view.SearchScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Navigation(navController: NavHostController, paddingValues: PaddingValues) {
  NavHost(
    navController = navController,
    startDestination = "Home",
    modifier = Modifier.padding(paddingValues)
  ) {
    composable("Home") {
      DashBoard(navController)
    }
    composable("Products") {
      ProductScreen(navController)
    }
    composable("Search") {
      SearchScreen(navController)
    }
    composable("Favourites") {
      FavouritesScreen(navController)
    }
    composable("Profile") {
      ProfileScreen(navController)
    }
  }
}

@Composable
fun TopNav(navController: NavHostController, drawerState: DrawerState) {
  val coroutineScope = rememberCoroutineScope()

  TopAppBar(
    title = {
      Text(text = AppConfig.appName,
        style = AppConfig.styleTitle,
        color = AppConfig.colorTitle)
    },
    backgroundColor = AppConfig.colorApp,
    navigationIcon = {
      IconButton(onClick = { togleDrawerMenu(drawerState, coroutineScope) }) {
        Icon(
          imageVector = Icons.Filled.Menu,
          contentDescription = "Menu",
          tint = AppConfig.colorDrawerMenu,
          modifier = Modifier.size(100.dp)
        )
      }
    }
  )
}

@Composable
fun DrawerMenu(navController: NavController, drawerState: DrawerState) {
  val coroutineScope = rememberCoroutineScope()

  val itemColors = NavigationDrawerItemDefaults.colors(
    unselectedContainerColor = Color.Transparent, // Background color when not selected
    unselectedTextColor = Color.Black, // Text color when not selected
    selectedContainerColor = Color.LightGray, // Background color when selected
    selectedTextColor = Color.Black // Text color when selected
  )

  val items = arrayOf("Products", "Search", "Favourites", "Profile")

  ModalDrawerSheet(drawerContainerColor = AppConfig.colorApp) {
    Text(AppConfig.appName, modifier = Modifier.padding(16.dp),
      style = AppConfig.styleDrawerMenuTitle,
      color = AppConfig.colorTitle)
    Divider()
    items.forEach { item ->
      NavigationDrawerItem(
        label = { Text(text = item) },
        selected = false,
//        shape = RectangleShape,
        colors = itemColors,
        onClick = {
          navController.navigate(item)
          togleDrawerMenu(drawerState, coroutineScope)
        }
      )
    }
  }
}

@Composable
fun BottomNav(navController: NavController) {
  val items = mapOf<String, ImageVector>(
    "Home" to Icons.Default.Home,
    "Search" to Icons.Default.Search,
    "Favourites" to Icons.Default.Favorite,
    "Profile" to Icons.Default.Person
    )

  BottomNavigation(
    backgroundColor = AppConfig.colorApp
  ) {
    items.forEach { (item, image) ->
      BottomNavigationItem(
        selected = navController.currentDestination?.route == item,
        onClick = { navController.navigate(item) },
        icon = { Icon(image, contentDescription = item,
          tint = AppConfig.colorBottomNav,
          modifier = Modifier.size(40.dp)) },
      )
    }
  }
}

fun togleDrawerMenu(drawerState: DrawerState, coroutineScope: CoroutineScope) {
  coroutineScope.launch {
    drawerState.apply {
      if (isClosed) open() else close()
    }
  }
}