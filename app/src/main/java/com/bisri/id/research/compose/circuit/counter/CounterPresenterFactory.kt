package com.bisri.id.research.compose.circuit.counter

import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.presenter.presenterOf
import com.slack.circuit.runtime.screen.Screen

class CounterPresenterFactory : Presenter.Factory {
    override fun create(
        screen: Screen,
        navigator: Navigator,
        context: CircuitContext,
    ): Presenter<*>? {
        return when (screen) {
            is CounterScreenState -> {
                presenterOf {
                    CounterPresenter(navigator)
                }
            }
            else -> null
        }
    }
}
