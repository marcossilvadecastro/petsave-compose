package com.raywenderlich.android.petsave.common.presentation.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raywenderlich.android.petsave.animalsnearyou.presentation.AnimalItem
import com.raywenderlich.android.petsave.animalsnearyou.presentation.AnimalsNearYouEvent
import com.raywenderlich.android.petsave.common.presentation.model.UIAnimal

private const val ITEMS_PER_ROW = 2

object ListConstants {
    const val ITEMS_PER_ROW = 2
}

@ExperimentalFoundationApi
@Composable
fun GridListAnimals(
    animals: List<UIAnimal>,
    content: @Composable (UIAnimal, index: Int) -> Unit
) {

    Box(modifier = Modifier.fillMaxWidth()) {

        val listState = rememberLazyListState()

        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            cells = GridCells.Fixed(ListConstants.ITEMS_PER_ROW),
            state = listState
        ) {

            items(animals.size) { i ->
                content(animals[i], i)
            }
        }
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
fun ShowList() {
    GridListAnimals(
        animals = listOf(
            UIAnimal(1, "Hello", "", "Cat"),
            UIAnimal(2, "Kitty", "", "Cat")
        )
    ) { animal, index ->
        AnimalItem(animalUI = animal)
    }
}