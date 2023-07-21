package com.osamaaftab.teya

import android.util.Log
import android.view.KeyEvent
import androidx.appcompat.widget.SearchView
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressKey
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.osamaaftab.teya.MockTestRunner.Companion.MOCK_WEB_SERVER_PORT
import com.osamaaftab.teya.common.OkHttp3IdlingResource
import com.osamaaftab.teya.common.RecyclerViewItemCountAssertion
import com.osamaaftab.teya.common.SuccessDispatcher
import com.osamaaftab.teya.common.typeSearchViewText
import com.osamaaftab.teya.presentation.ui.activity.AlbumListActivity
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.inject
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
class AlbumListTestActivity : KoinTest {

    private var mockWebServer = MockWebServer()
    private val client: OkHttpClient by inject()
    private var okHttp3IdlingResource: OkHttp3IdlingResource? = null

    @Before
    fun setup() {
        mockWebServer.start(MOCK_WEB_SERVER_PORT)
        Log.i(TAG, "Server is running on port number:$MOCK_WEB_SERVER_PORT")
        okHttp3IdlingResource = OkHttp3IdlingResource.create(OK_HTTP, client)
        IdlingRegistry.getInstance().register(okHttp3IdlingResource)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
        Log.i(TAG, "Server has been shut down")
        IdlingRegistry.getInstance().unregister(okHttp3IdlingResource)
    }

    @Test
    fun onFetchAlbumListSuccess() {
        mockWebServer.dispatcher = SuccessDispatcher()
        launchActivity<AlbumListActivity>()
        onView(withId(R.id.indeterminateBar)).check(matches(not(isDisplayed())))
        onView(withId(R.id.albumRecyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun onFetchAlbumListFails() {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse().throttleBody(1024, 5, TimeUnit.SECONDS)
            }
        }
        launchActivity<AlbumListActivity>()
        onView(withId(R.id.indeterminateBar)).check(matches(not(isDisplayed())))
        onView(withId(R.id.statusLbl)).check(matches(isDisplayed()))
    }

    @Test
    fun onFetchAlbumListSuccessAndSearch() {
        mockWebServer.dispatcher = SuccessDispatcher()
        launchActivity<AlbumListActivity>()
        onView(withId(R.id.search)).perform(click())
        onView(isAssignableFrom(SearchView::class.java)).perform(typeSearchViewText("Ed"))
            .perform(pressKey(KeyEvent.KEYCODE_ENTER))
        onView(withId(R.id.albumRecyclerView)).check(RecyclerViewItemCountAssertion(6))
    }

    companion object {
        private const val OK_HTTP = "okhttp"
        private val TAG = AlbumListActivity::class.java.simpleName
    }
}