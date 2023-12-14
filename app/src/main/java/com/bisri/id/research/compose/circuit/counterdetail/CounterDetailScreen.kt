package com.bisri.id.research.compose.circuit.counterdetail

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bisri.id.research.compose.circuit.counter.CounterScreen
import com.bisri.id.research.compose.circuit.di.AppScope
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.parcelize.Parcelize

@Stable
@Parcelize
data class CounterDetailScreen(
    val id: Int = 0,
) : Screen {

    sealed interface State : CircuitUiState {
        data object Success : State
    }
}

class CounterDetailPresenter @AssistedInject constructor(
    @Assisted private val screen: CounterDetailScreen,
) : Presenter<CounterDetailScreen.State> {

    @Composable
    override fun present(): CounterDetailScreen.State {
        val id = screen.id
        return CounterDetailScreen.State.Success
    }
}

@CircuitInject(CounterDetailScreen::class, AppScope::class)
@Composable
internal fun CounterDetail(
    state: CounterDetailScreen.State,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .size(100.dp)
            .background(Color.Red)
    ) {

    }
}
