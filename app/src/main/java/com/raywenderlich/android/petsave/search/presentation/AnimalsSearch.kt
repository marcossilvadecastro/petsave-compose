package com.raywenderlich.android.petsave.search.presentation

import android.R
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.raywenderlich.android.logging.Logger
import com.raywenderlich.android.petsave.animalsnearyou.presentation.AnimalItem
import com.raywenderlich.android.petsave.animalsnearyou.presentation.AnimalsNearYouEvent
import com.raywenderlich.android.petsave.common.presentation.component.GridListAnimals


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun AnimalsSearch(
    viewModel: SearchAnimalsViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.Top
    ) {

        viewModel.onEvent(SearchEvent.PrepareForSearch)

        val state = viewModel.state.value!!
        SearchBar { query ->
            viewModel.onEvent(SearchEvent.QueryInput(query))
        }

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

            DropDown(
                "Types",
                state.typeFilterValues.getContentIfNotHandled() ?: listOf(),
                onSelected = {
                    viewModel.onEvent(SearchEvent.TypeValueSelected(it))
                }
            )
            DropDown(
                "Age",
                state.ageFilterValues.getContentIfNotHandled() ?: listOf(),
                onSelected = {
                    viewModel.onEvent(SearchEvent.AgeValueSelected(it))
                }
            )
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )

        Box(modifier = Modifier.fillMaxSize()) {
            GridListAnimals(animals = state.searchResults) { animal, index ->

                AnimalItem(animalUI = animal)
            }
        }
    }
}

@Composable
private fun SearchBar(onSearch: (query: String) -> Unit) {

    val query = remember {
        mutableStateOf(TextFieldValue())
    }

    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = query.value,
        singleLine = true,
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
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch(query.value.text)
            }
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        )
    )
}

@ExperimentalMaterialApi
@Composable
fun DropDown(title: String, options: List<String>, onSelected: (item: String) -> Unit) {

    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options.firstOrNull()) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        },
        modifier = Modifier
            .wrapContentWidth(Alignment.Start)

    ) {

        TextField(
            label = { Text(title) },
            readOnly = true,
            value = selectedOptionText ?: "",
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
                        selectedOptionText?.takeIf {
                            it.isNotBlank()
                        }?.let(onSelected)
                    }
                ) {
                    Text(text = selectionOption)
                }
            }
        }
    }
}