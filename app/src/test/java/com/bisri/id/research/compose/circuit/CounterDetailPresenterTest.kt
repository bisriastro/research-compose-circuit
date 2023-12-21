package com.bisri.id.research.compose.circuit

import com.bisri.id.research.compose.circuit.counterdetail.CounterDetailPresenter
import com.bisri.id.research.compose.circuit.counterdetail.CounterDetailScreen
import com.slack.circuit.test.FakeNavigator
import com.slack.circuit.test.test
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CounterDetailPresenterTest {

    private val navigator = FakeNavigator()

    @Test
    fun `fetch counter detail`() {
        val presenter = CounterDetailPresenter(
            screen = CounterDetailScreen(1),
            navigator = navigator
        )

        runTest {
            presenter.test {
                assertEquals(CounterDetailScreen.State.Loading, awaitItem())
                val result = awaitItem() as CounterDetailScreen.State.Success
                assertEquals(1, result.id)
            }
        }
    }
}
