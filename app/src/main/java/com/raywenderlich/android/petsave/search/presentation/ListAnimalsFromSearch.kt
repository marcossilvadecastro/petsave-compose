package com.raywenderlich.android.petsave.search.presentation

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.raywenderlich.android.petsave.animalsnearyou.presentation.AnimalItem
import com.raywenderlich.android.petsave.animalsnearyou.presentation.AnimalsNearYouEvent

private const val ITEMS_PER_ROW = 2

object ListConstants {
    const val ITEMS_PER_ROW = 2
}

@ExperimentalFoundationApi
@Composable
fun ListAnimalsFromSearch(
    viewModel: SearchAnimalsViewModel = hiltViewModel()
) {

   viewModel.onEvent(SearchEvent.PrepareForSearch)

    val state = viewModel.state.value!!
    Box(modifier = Modifier.fillMaxSize()) {

        val listState = rememberLazyListState()

        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            cells = GridCells.Fixed(ListConstants.ITEMS_PER_ROW),
            state = listState
        ) {

//            val animals = state.
//
//            val lastIndex = animals.lastIndex
//
//
//            items(animals.size) { i ->
//
//                if(i == lastIndex){
//                    viewModel.onEvent(AnimalsNearYouEvent.RequestMoreAnimals)
//                }
//                AnimalItem(animalUI = animals[i])
//            }
        }



        state.failure?.getContentIfNotHandled()?.let {
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

        if (state.searchingRemotely) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }


}