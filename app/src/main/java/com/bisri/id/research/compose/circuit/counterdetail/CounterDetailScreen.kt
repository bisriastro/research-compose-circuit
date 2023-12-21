package com.bisri.id.research.compose.circuit.counterdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.bisri.id.research.compose.circuit.ComposePopNavigationScreen
import com.bisri.id.research.compose.circuit.FragmentNavigationScreen
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.coroutines.delay
import kotlinx.parcelize.Parcelize

@Stable
@Parcelize
data class CounterDetailScreen(
    val id: Int = 0,
) : Screen {

    sealed interface State : CircuitUiState {
        data object Loading : State
        data class Success(
            val id: Int = 0,
            val event: (Event) -> Unit = {}
        ) : State
    }

    sealed interface Event : CircuitUiEvent {
        data object GoTo : Event
        data object Pop : Event
    }
}

class CounterDetailPresenter(
    private val screen: CounterDetailScreen,
    private val navigator: Navigator,
) : Presenter<CounterDetailScreen.State> {

    @Composable
    override fun present(): CounterDetailScreen.State {
        val id = screen.id

        val state by produceState(false) {
            delay(2 * 1000)
            value = true
        }

        return when {
            state.not() -> {
                CounterDetailScreen.State.Loading
            }
            else -> {
                CounterDetailScreen.State.Success(id) { event ->
                    when (event) {
                        CounterDetailScreen.Event.GoTo -> {
                            navigator.goTo(FragmentNavigationScreen("circuit://screen/first"))
                        }

                        CounterDetailScreen.Event.Pop -> {
                            val bundle = hashMapOf<String, Any>()
                            bundle["isResultReceived"] = true
                            navigator.goTo(ComposePopNavigationScreen(bundle))
                            navigator.pop()
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun CounterDetail(
    state: CounterDetailScreen.State,
    modifier: Modifier = Modifier
) {
    if (state is CounterDetailScreen.State.Loading) {
        Box(modifier.fillMaxSize()) {
            Column(Modifier.align(Alignment.Center)) {
                CircularProgressIndicator()
            }
        }
    }

    if (state is CounterDetailScreen.State.Success) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.Black)
        ) {
            Text(
                text = "Halaman fragment 2 compose screen 2",
                fontSize = 30.sp,
                color = Color.White
            )
            Text(
                modifier = Modifier.clickable {
                    state.event(CounterDetailScreen.Event.GoTo)
                },
                text = "Navigasi ke fragment 1",
                fontSize = 30.sp,
                color = Color.White
            )
            Text(
                modifier = Modifier.clickable {
                    state.event(CounterDetailScreen.Event.Pop)
                },
                text = "Kembali ke halaman fragment 2 compose screen 1",
                fontSize = 30.sp,
                color = Color.White
            )
        }
    }
}
