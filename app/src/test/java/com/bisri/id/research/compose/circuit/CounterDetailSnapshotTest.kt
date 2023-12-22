package com.bisri.id.research.compose.circuit

import androidx.activity.ComponentActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bisri.id.research.compose.circuit.counter.Counter
import com.bisri.id.research.compose.circuit.counter.CounterScreen
import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.RoborazziRule
import com.github.takahirom.roborazzi.captureRoboImage
import com.github.takahirom.roborazzi.captureScreenRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [33], qualifiers = RobolectricDeviceQualifiers.Pixel5)
class CounterDetailSnapshotTest {

    @get:Rule
    val composeTestRule =
        createAndroidComposeRule<ComponentActivity>()

    @get:Rule
    val roborazziRule = RoborazziRule(
        composeRule = composeTestRule,
        captureRoot = composeTestRule.onRoot(),
        options = RoborazziRule.Options(outputDirectoryPath = "src/test/snapshots/images")
    )

    @OptIn(ExperimentalRoborazziApi::class)
    @Test
    fun `sample compose`() {
        composeTestRule.setContent {
            MaterialTheme {
                Text("Halaman fragment 1")
            }
        }

        captureScreenRoboImage()
    }

    @Test
    fun `show loading state`() {
        composeTestRule.setContent {
            MaterialTheme {
                Counter(state = CounterScreen.State.Success(count = 5))
            }
        }

        composeTestRule.onRoot().captureRoboImage()
        // or
//        composeTestRule.onRoot().captureRoboImage(
//            filePath = "src/test/screenshots/$directoryName/success.png",
//            roborazziOptions = RoborazziOptions(
//                recordOptions =
//                RoborazziOptions.RecordOptions(resizeScale = 0.5)
//            )
//        )
    }
}
