package com.bisri.id.research.compose.circuit.counterdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import com.bisri.id.research.compose.circuit.di.AppScope
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.parcelize.Parcelize

@Stable
@Parcelize
data class CounterDetailScreen(
    val id: Int = 0,
) : Screen {

    sealed interface State : CircuitUiState {
        data class Success(
            val event: (Event) -> Unit = {}
        ) : State
    }

    sealed interface Event : CircuitUiEvent {
        data object GoTo : Event
        data object Pop : Event
    }
}

class CounterDetailPresenter @AssistedInject constructor(
    @Assisted private val screen: CounterDetailScreen,
    @Assisted private val navigator: Navigator,
    @Assisted private val navController: NavController,
) : Presenter<CounterDetailScreen.State> {

    @Composable
    override fun present(): CounterDetailScreen.State {
        val id = screen.id
        return CounterDetailScreen.State.Success { event ->
            when (event) {
                CounterDetailScreen.Event.GoTo -> {
                    val request = NavDeepLinkRequest.Builder
                        .fromUri("circuit://screen/first".toUri())
                        .build()
                    navController.navigate(request)
                }

                CounterDetailScreen.Event.Pop -> {
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("isResultReceived", true)
                    navigator.pop()
                }
            }
        }
    }
}

@CircuitInject(CounterDetailScreen::class, AppScope::class)
@Composable
internal fun CounterDetail(
    state: CounterDetailScreen.State,
    modifier: Modifier = Modifier
) {
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
