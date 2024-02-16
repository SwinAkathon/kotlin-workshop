package com.example.ecoms.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ecoms.AppConfig
import com.example.ecoms.modules.dashboard.view.HomeScreen
import com.example.ecoms.modules.favourites.view.FavouritesScreen
import com.example.ecoms.modules.profile.view.ProfileScreen
import com.example.ecoms.modules.search.view.SearchScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Navigation(navController: NavHostController, paddingValues: PaddingValues) {
  NavHost(
    navController = navController,
    startDestination = "home",
    modifier = Modifier.padding(paddingValues)
  ) {
    composable("home") {
      HomeScreen(navController)
    }
    composable("search") {
      SearchScreen(navController)
    }
    composable("favourites") {
      FavouritesScreen(navController)
    }
    composable("profile") {
      ProfileScreen(navController)
    }
  }
}

@Composable
fun TopNav(navController: NavHostController, drawerState: DrawerState) {
  val coroutineScope = rememberCoroutineScope()

  TopAppBar(
    title = {
      Text(text = AppConfig.props.appName)
    },
    backgroundColor = AppConfig.props.appColor,
    navigationIcon = {
      IconButton(onClick = { togleDrawerMenu(drawerState, coroutineScope) }) {
        Icon(
          imageVector = Icons.Filled.Menu,
          contentDescription = "Menu"
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

  ModalDrawerSheet(drawerContainerColor = AppConfig.props.appColor) {
    Text(AppConfig.props.appName, modifier = androidx.compose.ui.Modifier.padding(16.dp))
    Divider()
    NavigationDrawerItem(
      label = { Text(text = "Search") },
      selected = false,
      shape = RectangleShape,
      colors = itemColors,
      onClick = {
        navController.navigate("search")
        togleDrawerMenu(drawerState, coroutineScope)
      }
    )
    NavigationDrawerItem(
      label = { Text(text = "Favourites") },
      selected = false,
      shape = RectangleShape,
      colors = itemColors,
      onClick = {
        navController.navigate("favourites")
        togleDrawerMenu(drawerState, coroutineScope)
      }
    )
    NavigationDrawerItem(
      label = { Text(text = "Profile") },
      selected = false,
      shape = RectangleShape,
      colors = itemColors,
      onClick = {
        navController.navigate("profile")
        togleDrawerMenu(drawerState, coroutineScope)
      }
    )
    // ...other drawer items
  }
}

fun togleDrawerMenu(drawerState: DrawerState, coroutineScope: CoroutineScope) {
  coroutineScope.launch {
    drawerState.apply {
      if (isClosed) open() else close()
    }
  }
}


@Composable
fun BottomNav(navController: NavController) {
  BottomNavigation(
    backgroundColor = AppConfig.props.appColor
  ) {
    BottomNavigationItem(
      selected = navController.currentDestination?.route == "home",
      onClick = { navController.navigate("home") },
      icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
//      label = { Text("Home") }
    )
    BottomNavigationItem(
      selected = navController.currentDestination?.route == "search",
      onClick = { navController.navigate("search") },
      icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
//      label = { Text("Search") }
    )
    BottomNavigationItem(
      selected = navController.currentDestination?.route == "favourites",
      onClick = { navController.navigate("favourites") },
      icon = { Icon(Icons.Default.Favorite, contentDescription = "Favourites") },
//      label = { Text("Favourites") }
    )
    BottomNavigationItem(
      selected = navController.currentDestination?.route == "profile",
      onClick = { navController.navigate("profile") },
      icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
//      label = { Text("Profile") }
    )
  }
}
