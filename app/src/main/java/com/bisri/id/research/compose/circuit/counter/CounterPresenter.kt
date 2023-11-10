package com.bisri.id.research.compose.circuit.counter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.screen.Screen

@Composable
fun CounterPresenter(navigator: Navigator): CounterScreenState.State {
    var count by remember { mutableStateOf(0) }

    return CounterScreenState.State(count) { event ->
        when (event) {
            is CounterScreenState.Event.GoTo -> navigator.goTo(event.screen)
            is CounterScreenState.Event.Increment -> count++
            is CounterScreenState.Event.Decrement -> count--
        }
    }
}