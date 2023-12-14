package com.bisri.id.research.compose.circuit.counter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bisri.id.research.compose.circuit.counterdetail.CounterDetailScreen
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

@Parcelize
data object CounterScreen : Screen {
    sealed interface State : CircuitUiState {
        data class Success(
            val count: Int = 0,
            val eventSink: (Event) -> Unit = {},
        ) : State
    }

    sealed interface Event : CircuitUiEvent {
        data class GoTo(val screen: Screen) : Event
        data object Increment : Event
        data object Decrement : Event
    }
}

class CounterPresenter @AssistedInject constructor(
    @Assisted private val navigator: Navigator,
): Presenter<CounterScreen.State> {

    @Composable
    override fun present(): CounterScreen.State {
        var count by remember { mutableIntStateOf(0) }

        return CounterScreen.State.Success(count) { event ->
            when (event) {
                is CounterScreen.Event.GoTo -> navigator.goTo(event.screen)
                CounterScreen.Event.Increment -> count++
                CounterScreen.Event.Decrement -> count--
            }
        }
    }
}

@CircuitInject(CounterScreen::class, AppScope::class)
@Composable
fun Counter(
    state: CounterScreen.State,
    modifier: Modifier = Modifier
) {
    if (state is CounterScreen.State.Success) {
        val color = if (state.count >= 0) Color.Unspecified else MaterialTheme.colorScheme.error
        Box(modifier.fillMaxSize()) {
            Column(Modifier.align(Alignment.Center)) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Count: ${state.count}",
                    style = MaterialTheme.typography.displayLarge,
                    color = color
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = { state.eventSink(CounterScreen.Event.Increment) }
                ) {
                    Text(text = "Increment")
                }
                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = { state.eventSink(CounterScreen.Event.Decrement) }
                ) {
                    Text(text = "Decrement")
                }
                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = { state.eventSink(CounterScreen.Event.GoTo(CounterDetailScreen(1))) }
                ) {
                    Text(text = "Navigation to detail screen")
                }
            }
        }
    }
}
