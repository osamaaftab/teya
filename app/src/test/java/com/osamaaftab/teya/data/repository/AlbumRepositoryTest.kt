package com.osamaaftab.teya.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.osamaaftab.teya.data.source.remote.AlbumServices
import com.osamaaftab.teya.domain.model.ResponseModel
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

class AlbumRepositoryTest {

    @MockK
    lateinit var albumServices: AlbumServices

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = TestCoroutineDispatcher()

    private lateinit var sut: AlbumRepositoryImp


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this)
        sut = AlbumRepositoryImp(albumServices)
    }

    @Test
    fun `getAlbumAsync WHEN limit is 100 THEN verify the response`(): Unit =
        runBlocking {
            val responseModel = mockk<ResponseModel>(relaxed = true)
            every {
                runBlocking { albumServices.getAlbumAsync(100).await() }
            } returns (responseModel)

            val feedModel = sut.getAlbum(100)

            assertEquals(feedModel, responseModel)
        }
}