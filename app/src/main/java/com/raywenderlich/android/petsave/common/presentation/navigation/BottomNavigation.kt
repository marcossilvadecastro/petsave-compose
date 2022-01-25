package com.raywenderlich.android.petsave.common.presentation.navigation

import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.raywenderlich.android.petsave.R

@Composable
fun BottomNavigation(navController: NavController) {

    val navItems = listOf(BottomNavItem.NearYou, BottomNavItem.Search)

    androidx.compose.material.BottomNavigation(
        backgroundColor = colorResource(id = android.R.color.white),
        contentColor = colorResource(id = R.color.colorPrimary)
    ) {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        navItems.forEach { navItem ->

            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = navItem.icon),
                        contentDescription = navItem.title
                    )
                },
                label = {
                    Text(text = navItem.title, fontSize = 9.sp)
                },
                selectedContentColor = colorResource(id = R.color.colorPrimaryDark),
                unselectedContentColor = colorResource(id = android.R.color.darker_gray),
                alwaysShowLabel = true,
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

