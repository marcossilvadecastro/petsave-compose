package com.raywenderlich.android.petsave.common.presentation.navigation

import com.raywenderlich.android.petsave.R

sealed class BottomNavItem(
    val title: String,
    val icon : Int,
    val route : String
) {
    object NearYou : BottomNavItem("Near you", R.drawable.ic_animals_near_you_24dp, "near_you")
    object Search : BottomNavItem("Search", R.drawable.ic_search_24dp, "search")
}
