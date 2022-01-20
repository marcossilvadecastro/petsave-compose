package com.raywenderlich.android.petsave.common.domain.model.animal


import junit.framework.TestCase.assertEquals
import org.junit.Test

class PhotoTests {

    private val mediumPhoto = "mediumPhoto"
    private val fullPhoto = "fullPhoto"
    private val invalidPhoto = "" // what’s tested in Photo.isValidPhoto()

    @Test
    fun `get small available photo return has medium photo`(){

        val photo = Media.Photo(mediumPhoto, fullPhoto)
        val expectedValue = mediumPhoto

        val smallPhoto  = photo.getSmallestAvailablePhoto()

        assertEquals(smallPhoto, expectedValue)
    }

    @Test
    fun `get small available photo has no medium photo`(){

        val photo = Media.Photo(invalidPhoto, fullPhoto)
        val expectedValue = fullPhoto

        val smallPhoto  = photo.getSmallestAvailablePhoto()

        assertEquals(smallPhoto, expectedValue)
    }

    @Test
    fun `get small available photo has no photos`(){

        val photo = Media.Photo(invalidPhoto, invalidPhoto)
        val expectedValue = Media.Photo.EMPTY_PHOTO

        val smallPhoto  = photo.getSmallestAvailablePhoto()

        assertEquals(smallPhoto, expectedValue)
    }

}