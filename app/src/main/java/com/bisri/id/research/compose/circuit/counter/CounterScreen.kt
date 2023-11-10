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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.parcelize.Parcelize

@Parcelize
object AndroidCounterScreen : CounterScreenState

@Composable
fun CounterScreen(state: CounterScreenState.State, modifier: Modifier = Modifier) {
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
                onClick = { state.eventSink(CounterScreenState.Event.Increment) }
            ) {
                Text(text = "Increment")
            }
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = { state.eventSink(CounterScreenState.Event.Decrement) }
            ) {
                Text(text = "Decrement")
            }
        }
    }
}
