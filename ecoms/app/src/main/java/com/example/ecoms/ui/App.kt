package com.example.ecoms.ui

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

@Composable
fun App() {

  val navController = rememberNavController()
  val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

  ModalNavigationDrawer(
    drawerState = drawerState,
    drawerContent = { DrawerMenu(navController, drawerState) },
  ) {
    Scaffold(
      topBar = { TopNav(navController, drawerState) },
      content = { Navigation(navController, it) },
      bottomBar = { BottomNav(navController) }
    )
  }
}