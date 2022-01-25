package com.raywenderlich.android.petsave.common.data.cache.daos

import androidx.room.*
import com.raywenderlich.android.petsave.common.data.cache.model.cachedanimal.*
import io.reactivex.Flowable

@Dao
abstract class AnimalsDao {

    @Transaction
    @Query("SELECT * FROM animals")
    abstract fun getAllAnimals(): Flowable<List<CachedAnimalAggregate>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAnimalAggregate(
        animal: CachedAnimalWithDetails,
        photos: List<CachedPhoto>,
        videos: List<CachedVideo>,
        tags: List<CachedTag>
    )

    suspend fun insertAnimalsWithDetails(animalAggregates: List<CachedAnimalAggregate>) {

        for (animal in animalAggregates) {
            insertAnimalAggregate(
                animal = animal.animal,
                photos = animal.photos,
                videos = animal.videos,
                tags = animal.tags
            )
        }
    }

    @Query("SELECT DISTINCT type FROM animals")
    abstract suspend fun getAllTypes(): List<String>

    @Transaction
    @Query(
        """
    SELECT * FROM animals
      WHERE name LIKE '%' || :name || '%' AND
      AGE LIKE '%' || :age || '%'
      AND type LIKE '%' || :type || '%'
  """
    )
    abstract fun searchAnimalsBy(
        name: String,
        age: String,
        type: String
    ): Flowable<List<CachedAnimalAggregate>>
}