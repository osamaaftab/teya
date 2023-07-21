package com.osamaaftab.teya.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.osamaaftab.teya.data.ApiErrorHandle
import com.osamaaftab.teya.domain.model.FeedModel
import com.osamaaftab.teya.domain.usecase.GetAlbumUseCase
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class AlbumViewModelTest {

    @RelaxedMockK
    lateinit var getAlbumUseCase: GetAlbumUseCase

    private val dispatcher = TestCoroutineDispatcher()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var sut: AlbumViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this)
        sut = AlbumViewModel(getAlbumUseCase)
    }

    @Test
    fun `getAlbumOnSuccess WHEN feed is provided THEN verify progress bar live data value is false and feed data is posted to live data`() {
        val feedModel = mockk<FeedModel>(relaxed = true)

        sut.getAlbumUseCaseResponse().onSuccess(feedModel)

        val response = sut.getAlbumListLiveData().value
        val state = sut.getShowProgressLiveData().value
        assertEquals(false, state)
        assertEquals(feedModel.entry, response)
    }

    @Test
    fun `getAlbumOnError WHEN error is provided THEN verify progress bar live data value is false and error live data value is true`() {
        val apiErrorHandle = ApiErrorHandle()
        val throwable = mockk<Throwable>()
        apiErrorHandle.traceErrorException(throwable)

        sut.getAlbumUseCaseResponse()
            .onError(apiErrorHandle.traceErrorException(throwable))

        val state = sut.getShowProgressLiveData().value
        val drawable = sut.getShowErrorLiveData().value
        assertEquals(true, drawable)
        assertEquals(false, state)
    }

    @Test
    fun `loadAlbumList THEN verify progress bar live data value is true and invoke is called`() {
        sut.loadAlbumList()

        val state = sut.getShowProgressLiveData().value
        assertEquals(true, state)
        verify(exactly = 1) { getAlbumUseCase.invoke(any(), any(), any()) }
    }

    @Test
    fun `refreshAlbumList THEN verify invoke is called`() {
        sut.refreshAlbumList()

        verify(exactly = 1) { getAlbumUseCase.invoke(any(), any(), any()) }
    }
}