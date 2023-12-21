package com.bisri.id.research.compose.circuit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.bisri.id.research.compose.circuit.counter.Counter
import com.bisri.id.research.compose.circuit.counter.CounterPresenter
import com.bisri.id.research.compose.circuit.counter.CounterScreen
import com.bisri.id.research.compose.circuit.counterdetail.CounterDetail
import com.bisri.id.research.compose.circuit.counterdetail.CounterDetailPresenter
import com.bisri.id.research.compose.circuit.counterdetail.CounterDetailScreen
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import com.slack.circuit.overlay.ContentWithOverlays
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import com.slack.circuitx.android.AndroidScreen
import com.slack.circuitx.android.rememberAndroidScreenAwareNavigator
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

class SecondFragment : Fragment() {

    private val circuit: Circuit =
        Circuit.Builder()
            .addPresenterFactory(buildPresenterFactory())
            .addUiFactory(buildUiFactory())
            .build()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        val backstack = rememberSaveableBackStack {
                            push(CounterScreen)
                        }
                        val circuitNavigator = rememberCircuitNavigator(backstack)
                        val navigator = rememberAndroidScreenAwareNavigator(circuitNavigator, this@SecondFragment::goTo)
                        CircuitCompositionLocals(circuit) {
                            ContentWithOverlays {
                                NavigableCircuitContent(
                                    navigator = navigator,
                                    backstack = backstack
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun goTo(screen: AndroidScreen) {
        if (screen is FragmentNavigationScreen) {
            val request = NavDeepLinkRequest.Builder
                .fromUri("circuit://screen/first".toUri())
                .build()
            findNavController().navigate(request)
        }

        if (screen is ComposePopNavigationScreen) {
            val savedStateHandle = findNavController().currentBackStackEntry
                ?.savedStateHandle

            screen.bundle.map {
                savedStateHandle?.set(it.key, it.value)
            }
        }
    }

    private fun buildPresenterFactory(): Presenter.Factory {
        return Presenter.Factory { screen, navigator, _ ->
            when (screen) {
                is CounterScreen -> CounterPresenter(navigator, findNavController())
                is CounterDetailScreen -> CounterDetailPresenter(screen, navigator)
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

@Parcelize
data class FragmentNavigationScreen(
    val url: String = "",
) : AndroidScreen

@Parcelize
data class ComposePopNavigationScreen(
    val bundle: @RawValue Map<String, Any> = mapOf(),
) : AndroidScreen