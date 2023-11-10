package com.bisri.id.research.compose.circuit.counter

import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen

// Unfortunately can't make this multiplatform by itself because plugin.parcelize doesn't play nice
// in multiplatform android library projects
interface CounterScreenState : Screen {
    data class State(
        val count: Int,
        val eventSink: (Event) -> Unit = {},
    ) : CircuitUiState

    sealed interface Event : CircuitUiEvent {
        data class GoTo(val screen: Screen) : Event
        object Increment : Event
        object Decrement : Event
    }
}
