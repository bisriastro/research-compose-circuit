package com.bisri.id.research.compose.circuit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.bisri.id.research.compose.circuit.counter.Counter
import com.bisri.id.research.compose.circuit.counter.CounterPresenter
import com.bisri.id.research.compose.circuit.counter.CounterScreen
import com.bisri.id.research.compose.circuit.counterdetail.CounterDetail
import com.bisri.id.research.compose.circuit.counterdetail.CounterDetailPresenter
import com.bisri.id.research.compose.circuit.counterdetail.CounterDetailScreen
import com.bisri.id.research.compose.circuit.ui.theme.ResearchComposeCircuitTheme
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import com.slack.circuit.overlay.ContentWithOverlays
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui

class MainActivity : ComponentActivity() {

    private val circuit: Circuit =
        Circuit.Builder()
            .addPresenterFactory(buildPresenterFactory())
            .addUiFactory(buildUiFactory())
            .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ResearchComposeCircuitTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val backstack = rememberSaveableBackStack {
                        push(CounterScreen)
                    }
                    val circuitNavigator = rememberCircuitNavigator(backstack)
                    CircuitCompositionLocals(circuit) {
                        ContentWithOverlays {
                            NavigableCircuitContent(
                                navigator = circuitNavigator,
                                backstack = backstack
                            )
                        }
                    }
                }
            }
        }
    }

    private fun buildPresenterFactory(): Presenter.Factory {
        return Presenter.Factory { screen, navigator, _ ->
            when (screen) {
                is CounterScreen -> CounterPresenter(navigator)
                is CounterDetailScreen -> CounterDetailPresenter(screen)
                else -> null
            }
        }
    }

    private fun buildUiFactory(): Ui.Factory {
        return Ui.Factory { screen, _ ->
            when (screen) {
                is CounterScreen -> ui<CounterScreen.State> { state, modifier ->
                    Counter(state, modifier)
                }
                is CounterDetailScreen -> ui<CounterDetailScreen.State> { state, modifier ->
                    CounterDetail(state, modifier)
                }
                else -> null
            }
        }
    }
}
