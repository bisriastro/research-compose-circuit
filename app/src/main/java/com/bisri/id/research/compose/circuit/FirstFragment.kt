package com.bisri.id.research.compose.circuit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.findNavController

class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    Surface {
                        Column(modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Cyan)
                            .clickable {
                                val request = NavDeepLinkRequest.Builder
                                    .fromUri("circuit://screen/second".toUri())
                                    .build()

                                findNavController().navigate(request)
                            }
                        ) {
                            Text(
                                text = "Halaman fragment 1",
                                fontSize = 30.sp
                            )
                            Text(
                                text = "Navigasi ke fragment 2",
                                fontSize = 30.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
