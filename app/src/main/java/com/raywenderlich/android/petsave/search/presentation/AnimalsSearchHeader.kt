package com.raywenderlich.android.petsave.search.presentation

import android.widget.Space
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun AnimalsSearchHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.Top
    ) {

        val query = remember {
            mutableStateOf(TextFieldValue())
        }

        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = query.value,
            onValueChange = {
                query.value = it
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_search),
                    contentDescription = "search"
                )
            },
            textStyle = MaterialTheme.typography.body1,
            label = {
                Text(
                    text = "Search for names",
                    style = MaterialTheme.typography.body2
                )
            },
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

        }


    }
}

@Preview
@Composable
fun PreviewAnimalsSearchHeader() {
    AnimalsSearchHeader()
}
