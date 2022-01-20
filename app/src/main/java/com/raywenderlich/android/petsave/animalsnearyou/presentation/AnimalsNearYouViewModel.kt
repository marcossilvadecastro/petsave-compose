package com.raywenderlich.android.petsave.animalsnearyou.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raywenderlich.android.logging.Logger
import com.raywenderlich.android.petsave.animalsnearyou.domain.GetAnimals
import com.raywenderlich.android.petsave.animalsnearyou.domain.RequestNextPageOfAnimals
import com.raywenderlich.android.petsave.common.domain.model.NetworkException
import com.raywenderlich.android.petsave.common.domain.model.NetworkUnavailableException
import com.raywenderlich.android.petsave.common.domain.model.NoMoreAnimalsException
import com.raywenderlich.android.petsave.common.domain.model.animal.Animal
import com.raywenderlich.android.petsave.common.domain.model.pagination.Pagination
import com.raywenderlich.android.petsave.common.presentation.Event
import com.raywenderlich.android.petsave.common.presentation.model.mappers.UiAnimalMapper
import com.raywenderlich.android.petsave.common.utils.DispatchersProvider
import com.raywenderlich.android.petsave.common.utils.createExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AnimalsNearYouViewModel @Inject constructor(
    private val getAnimals: GetAnimals,
    private val requestNextPageOfAnimals: RequestNextPageOfAnimals,
    private val uiAnimalMapper: UiAnimalMapper,
    private val dispatchersProvider: DispatchersProvider,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    private val _state = mutableStateOf(AnimalsNearYouViewState())
    val state: State<AnimalsNearYouViewState> get() = _state

    private var currentPage = 0

    init {
        _state.value = AnimalsNearYouViewState()
        subscribeToAnimalUpdates()
    }

    private fun subscribeToAnimalUpdates() {
        getAnimals()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onNewAnimalList(it) },
                { onFailure(it) }
            )
            .addTo(compositeDisposable)
    }

    private fun onNewAnimalList(animals: List<Animal>) {
        Logger.d("Got more animals!")

        val animalsNearYou = animals.map { uiAnimalMapper.mapToView(it) }

        val currentSet = state.value!!.animals.toSet()
        val newAnimals = animalsNearYou.subtract(currentSet)
        val updatedList = currentSet + newAnimals

        _state.value = state.value!!.copy( loading = false, animals = updatedList.toList())
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun onEvent(event : AnimalsNearYouEvent) {
        when(event) {
            AnimalsNearYouEvent.RequestInitialAnimalsList -> loadAnimals()
            AnimalsNearYouEvent.RequestMoreAnimals -> loadNextAnimalPage()
        }
    }

    private fun loadAnimals(){
        if(_state.value!!.animals.isEmpty()){
            loadNextAnimalPage()
        }
    }

    private fun loadNextAnimalPage() {
        val errorMessage = "Failed to fetch nearby animals"
        val exceptionHandler =
            viewModelScope.createExceptionHandler(errorMessage) {
                onFailure(it)
            }

        viewModelScope.launch(exceptionHandler) {
            val pagination = withContext(dispatchersProvider.io()) {
                Logger.d("Requesting more animals")
                requestNextPageOfAnimals(++currentPage)
            }

            onPaginationInfoObtained(pagination)
        }
    }

    private fun onPaginationInfoObtained(pagination: Pagination) {
        currentPage = pagination.currentPage
    }

    private fun onFailure(failure: Throwable) {
        when (failure) {
            is NetworkException,
            is NetworkUnavailableException -> {
                _state.value = state.value!!.copy(
                    loading = false,
                    failure = Event(failure)
                )
            }
            is NoMoreAnimalsException -> {
                _state.value = state.value!!.copy(
                    noMoreAnimalsNearby = true,
                    failure = Event(failure)
                )
            }
        }
    }


}