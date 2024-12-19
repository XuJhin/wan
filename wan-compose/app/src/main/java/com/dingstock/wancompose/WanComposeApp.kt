package com.dingstock.wancompose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dingstock.wancompose.ui.theme.WanComposeTheme
import com.dingstock.wancompose.ui.views.ArticleList
import com.dingstock.wancompose.ui.views.detail.DetailScreen

@Composable
fun WanComposeApp(viewModel: MainViewModel) {
    WanComposeTheme { // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            NavHost(
                navController = rememberNavController(),
                startDestination = "main",
            ) {
                composable(route = "main") {
                    ArticleList(viewModel.pager)
                }
                composable(route = "detail") {
                    DetailScreen()
                }
            }
        }
    }
}