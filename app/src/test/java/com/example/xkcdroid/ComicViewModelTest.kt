package com.example.xkcdroid

import com.example.xkcdroid.data.ComicRepository
import com.example.xkcdroid.data.model.Comic
import com.example.xkcdroid.ui.comic.ComicViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ComicViewModelTest {
    private lateinit var repository: ComicRepository
    private lateinit var viewModel: ComicViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadLatestComic WHEN success THEN uiState is updated with comic`() =
        runTest {
            val testComic = Comic(
                id = 4,
                title = "Test Comic",
                imageUrl = "",
                altText = "",
                transcriptText = "",
                day = "3",
                month = "11",
                year = "2023"
            )
            coEvery { repository.getLatestComic() } returns Result.success(testComic)

            viewModel = ComicViewModel(repository)

            testDispatcher.scheduler.advanceUntilIdle()

            val finalState = viewModel.uiState.value
            assertEquals(false, finalState.isLoading)
            assertEquals(null, finalState.error)
            assertEquals(testComic, finalState.comic)
            assertEquals(true, finalState.isLatestComic)
        }
}