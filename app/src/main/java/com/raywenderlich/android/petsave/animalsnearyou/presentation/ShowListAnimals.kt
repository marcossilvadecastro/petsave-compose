package com.raywenderlich.android.petsave.animalsnearyou.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.raywenderlich.android.petsave.common.presentation.component.GridListAnimals

private const val ITEMS_PER_ROW = 2

object ListConstants {
    const val ITEMS_PER_ROW = 2
}

@ExperimentalFoundationApi
@Composable
fun ShowListAnimals(
    viewModel: AnimalsNearYouViewModel = hiltViewModel()
) {

    viewModel.onEvent(AnimalsNearYouEvent.RequestInitialAnimalsList)

    val state = viewModel.state
    Box(modifier = Modifier.fillMaxSize()) {

        val animals = state.value.animals
        GridListAnimals(animals = animals) { animal, index ->

            if (index == animals.lastIndex) {
                viewModel.onEvent(AnimalsNearYouEvent.RequestMoreAnimals)
            }
            AnimalItem(animalUI = animal)
        }

        state.value.failure?.getContentIfNotHandled()?.let {
            Text(
                text = it.message ?: "",
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }

        if (state.value.loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }


}