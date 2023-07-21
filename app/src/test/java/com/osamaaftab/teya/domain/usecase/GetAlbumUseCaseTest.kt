package com.osamaaftab.teya.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.osamaaftab.teya.data.ApiErrorHandle
import com.osamaaftab.teya.domain.model.ResponseModel
import com.osamaaftab.teya.domain.repository.AlbumRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class GetAlbumUseCaseTest {

    @MockK
    lateinit var albumRepository: AlbumRepository

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = TestCoroutineDispatcher()

    private lateinit var sut: GetAlbumUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this)
        val apiErrorHandle = mockk<ApiErrorHandle>()
        sut = GetAlbumUseCase(albumRepository, apiErrorHandle)
    }

    @Test
    fun `run WHEN limit is 100 THEN verify the feed object`(): Unit =
        runBlocking {
            val responseModel = mockk<ResponseModel>(relaxed = true)
            every { runBlocking { albumRepository.getAlbum(100) } } returns (responseModel)

            val feedModel = sut.run(100)

            assertEquals(feedModel, responseModel.feed)
        }


    @Test(expected = IllegalArgumentException::class)
    fun `run WHEN limit is null THEN throw illegal argument exception`(): Unit =
        runBlocking {
            val responseModel = mockk<ResponseModel>(relaxed = true)
            every { runBlocking { albumRepository.getAlbum(100) } } returns (responseModel)

            val feedModel = sut.run(null)

            assertEquals(feedModel, responseModel.feed)
        }
}