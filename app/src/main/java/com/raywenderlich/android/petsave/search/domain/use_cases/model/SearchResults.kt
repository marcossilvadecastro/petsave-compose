package com.raywenderlich.android.petsave.search.domain.use_cases.model

import com.raywenderlich.android.petsave.common.domain.model.animal.Animal
import com.raywenderlich.android.petsave.common.domain.model.search.SearchParameters

data class SearchResults(
    val animals: List<Animal>,
    val searchParameters: SearchParameters
)