package com.bisri.id.research.compose.circuit

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.bisri.id.research.compose.circuit.databinding.ActivityMainBinding

class MainActivity : FragmentActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
    }
}
