package com.raywenderlich.android.petsave.common

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.raywenderlich.android.petsave.R
import com.raywenderlich.android.petsave.animalsnearyou.presentation.ListAnimalsNearYou
import com.raywenderlich.android.petsave.common.presentation.navigation.BottomNavItem
import com.raywenderlich.android.petsave.common.presentation.navigation.BottomNavigation
import com.raywenderlich.android.petsave.common.presentation.navigation.TopAppBar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContent {

            val navController = rememberNavController()
            Scaffold(
                topBar = { TopAppBar(navController = navController) },
                bottomBar = { BottomNavigation(navController = navController) }
            ) {

                NavHost(navController = navController, startDestination = BottomNavItem.NearYou.route) {

                    composable(BottomNavItem.NearYou.route) {

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = 8.dp)
                        ){
                            AnimalsNearYourContent()
                        }
                    }

                    composable(BottomNavItem.Search.route) {
                       TODO()
                    }
                }
            }
        }
    }


    @ExperimentalFoundationApi
    @Composable
    fun AnimalsNearYourContent() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 64.dp)
        ){
            ListAnimalsNearYou()
        }
    }
}


