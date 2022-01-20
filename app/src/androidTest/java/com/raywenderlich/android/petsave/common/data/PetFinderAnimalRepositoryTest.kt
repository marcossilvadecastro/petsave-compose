package com.raywenderlich.android.petsave.common.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.raywenderlich.android.petsave.common.data.api.PetFinderApi
import com.raywenderlich.android.petsave.common.data.api.model.mappers.ApiAnimalMapper
import com.raywenderlich.android.petsave.common.data.api.model.mappers.ApiPaginationMapper
import com.raywenderlich.android.petsave.common.data.api.utils.FakeServer
import com.raywenderlich.android.petsave.common.data.cache.Cache
import com.raywenderlich.android.petsave.common.data.cache.PetSaveDatabase
import com.raywenderlich.android.petsave.common.data.cache.RoomCache
import com.raywenderlich.android.petsave.common.data.di.CacheModule
import com.raywenderlich.android.petsave.common.data.di.PreferencesModule
import com.raywenderlich.android.petsave.common.data.preferences.FakePreferences
import com.raywenderlich.android.petsave.common.data.preferences.Preferences
import com.raywenderlich.android.petsave.common.domain.repositories.AnimalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.threeten.bp.Instant
import retrofit2.Retrofit
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(PreferencesModule::class, CacheModule::class)
class PetFinderAnimalRepositoryTest {

    private val fakeServer = FakeServer()
    private lateinit var repository: AnimalRepository
    private lateinit var api: PetFinderApi
    private lateinit var cache: Cache

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var database: PetSaveDatabase

    @Inject
    lateinit var retrofitBuilder: Retrofit.Builder

    @Inject
    lateinit var apiAnimalMapper: ApiAnimalMapper

    @Inject
    lateinit var apiPaginationMapper: ApiPaginationMapper

    @BindValue
    @JvmField
    val preferences: Preferences = FakePreferences()

    @Module
    @InstallIn(SingletonComponent::class)
    object TestCacheModule {
        @Provides
        fun provideRoomDatabase(): PetSaveDatabase { // 2
            return Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().context,
                PetSaveDatabase::class.java
            ).allowMainThreadQueries()
                .build()
        }
    }


    @Before
    fun setup() {
        fakeServer.start() // 1

        preferences.deleteTokenInfo()
        preferences.putToken("validToken")
        preferences.putTokenExpirationTime(
            Instant.now().plusSeconds(3600).epochSecond
        )
        preferences.putTokenType("Bearer")
        hiltRule.inject() // 3

        cache = RoomCache(animalsDao = database.animalsDao(), organizationsDao = database.organizationsDao())

        api = retrofitBuilder
            .baseUrl(fakeServer.baseEndpoint)
            .build()
            .create(PetFinderApi::class.java)

        repository = PetFinderAnimalRepository(
            api,
            cache,
            apiAnimalMapper,
            apiPaginationMapper
        )
    }

    @After
    fun tearDown() {
        fakeServer.shutdown()
    }

    @Test
    fun getAnimals_Success() = runBlocking {
        val expectedAnimalId = 124L
        fakeServer.setHappyPathDispatcher()

        val paginatedAnimals = repository.requestMoreAnimals(1, 100)

        val animal = paginatedAnimals.animals.first()
        MatcherAssert.assertThat("Success animal", animal.id, `is`(expectedAnimalId))
    }

    @Test
    fun storeAnimals_Success() {
        val expectedAnimalId = 124L

        runBlocking {
            fakeServer.setHappyPathDispatcher()
            val paginatedAnimals = repository.requestMoreAnimals(1,100)
            val animal = paginatedAnimals.animals.first()

            repository.storeAnimals(listOf(animal))
        }

        val testObserver = repository.getAnimals().test()
        testObserver.assertNoErrors()
        testObserver.assertNotComplete()
        testObserver.assertValue { it.first().id == expectedAnimalId }
    }
}