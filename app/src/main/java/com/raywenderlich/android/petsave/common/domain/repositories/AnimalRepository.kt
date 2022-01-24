package com.raywenderlich.android.petsave.common.domain.repositories

import com.raywenderlich.android.petsave.common.domain.model.animal.Animal
import com.raywenderlich.android.petsave.common.domain.model.animal.details.Age
import com.raywenderlich.android.petsave.common.domain.model.animal.details.AnimalWithDetails
import com.raywenderlich.android.petsave.common.domain.model.pagination.PaginatedAnimals
import com.raywenderlich.android.petsave.common.domain.model.search.SearchParameters
import com.raywenderlich.android.petsave.search.domain.use_cases.model.SearchResults
import io.reactivex.Flowable

interface AnimalRepository {

    fun getAnimals(): Flowable<List<Animal>>
    suspend fun requestMoreAnimals(pageToLoad: Int, numberOfItems: Int): PaginatedAnimals
    suspend fun storeAnimals(animals: List<AnimalWithDetails>)
    suspend fun getAnimalTypes(): List<String>
    fun getAnimalAges(): List<Age>
    fun searchCachedAnimalsBy(searchParameters: SearchParameters): Flowable<SearchResults>
    suspend fun searchAnimalsRemotely(
        pageToLoad: Int,
        searchParameters: SearchParameters,
        numberOfItems: Int
    ): PaginatedAnimals
}