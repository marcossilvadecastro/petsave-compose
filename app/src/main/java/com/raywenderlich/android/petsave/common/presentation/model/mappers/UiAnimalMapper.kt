package com.raywenderlich.android.petsave.common.presentation.model.mappers

import com.raywenderlich.android.petsave.common.domain.model.animal.Animal
import com.raywenderlich.android.petsave.common.presentation.model.UIAnimal
import javax.inject.Inject

class UiAnimalMapper @Inject constructor() : UiMapper<Animal, UIAnimal> {

    override fun mapToView(input: Animal): UIAnimal {
        return UIAnimal(
            type = input.type,
            id = input.id,
            name = input.name,
            photo = input.media.getFirstSmallestAvailablePhoto()
        )
    }
}