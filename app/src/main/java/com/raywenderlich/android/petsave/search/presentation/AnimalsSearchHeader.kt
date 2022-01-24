package com.raywenderlich.android.petsave.search.presentation

import android.R
import android.widget.Space
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.raywenderlich.android.petsave.animalsnearyou.presentation.ListAnimalsNearYou


@ExperimentalFoundationApi
@ExperimentalMaterialApi
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

        SearchBar(query)

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DropDown(listOf("Option 1", "Option 2"))
            DropDown(listOf("Option 3", "Option 3"))
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )

        ListAnimalsNearYou()
    }
}

@Composable
private fun SearchBar(query: MutableState<TextFieldValue>) {
    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = query.value,
        onValueChange = {
            query.value = it
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_menu_search),
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
}

@ExperimentalMaterialApi
@Composable
fun DropDown(options: List<String>) {

    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options.first()) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        },
        modifier = Modifier
            .wrapContentWidth(Alignment.Start)

    ) {

        TextField(
            readOnly = true,
            value = selectedOptionText,
            onValueChange = { },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
                .widthIn(1.dp, Dp.Infinity)
                .background(Color.Transparent)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        selectedOptionText = selectionOption
                        expanded = false
                    }
                ) {
                    Text(text = selectionOption)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Preview
@Composable
fun PreviewAnimalsSearchHeader() {
    AnimalsSearchHeader()
}
