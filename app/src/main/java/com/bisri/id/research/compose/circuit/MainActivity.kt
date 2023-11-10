package com.bisri.id.research.compose.circuit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.bisri.id.research.compose.circuit.counter.AndroidCounterScreen
import com.bisri.id.research.compose.circuit.counter.CounterPresenterFactory
import com.bisri.id.research.compose.circuit.counter.CounterScreenFactory
import com.bisri.id.research.compose.circuit.ui.theme.ResearchComposeCircuitTheme
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.CircuitContent

class MainActivity : ComponentActivity() {

    private val circuit: Circuit =
        Circuit.Builder()
            .addPresenterFactory(CounterPresenterFactory())
            .addUiFactory(CounterScreenFactory())
            .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ResearchComposeCircuitTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CircuitCompositionLocals(circuit) {
                        CircuitContent(AndroidCounterScreen)
                    }
                }
            }
        }
    }
}
