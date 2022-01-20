package com.raywenderlich.android.petsave.common.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.raywenderlich.android.petsave.R


@Composable
fun TopAppBar(navController: NavHostController) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.colorPrimary))
            .padding(vertical = 16.dp)
            .padding(start = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start

    ) {
        Text(text = "Animals near you", color = Color.White, fontSize = 16.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold)
    }
}