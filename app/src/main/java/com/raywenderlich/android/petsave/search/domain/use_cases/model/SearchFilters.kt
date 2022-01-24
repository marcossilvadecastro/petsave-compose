package com.raywenderlich.android.petsave.search.domain.use_cases.model

data class SearchFilters(
    val ages: List<String>,
    val types: List<String>
)