package com.bisri.id.research.compose.circuit.counter

import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui

class CounterScreenFactory : Ui.Factory {
    override fun create(screen: Screen, context: CircuitContext): Ui<*>? {
        return when (screen) {
            is AndroidCounterScreen -> ui<CounterScreenState.State> { state, modifier ->
                CounterScreen(state, modifier)
            }
            else -> null
        }
    }
}
