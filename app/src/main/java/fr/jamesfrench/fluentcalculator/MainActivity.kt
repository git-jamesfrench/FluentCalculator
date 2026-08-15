package fr.jamesfrench.fluentcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import fr.jamesfrench.fluentcalculator.pages.CalculatorStandard
import fr.jamesfrench.fluentcalculator.ui.theme.C
import fr.jamesfrench.fluentcalculator.ui.theme.FluentCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            FluentCalculatorTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Box( // Background
                        modifier = Modifier
                            .fillMaxSize()
                            .background(C.colors.background)
                            .padding(innerPadding)
                            .background(Color.Red)
                    ) {
                        CalculatorStandard(innerPadding)
                    }
                }
            }
        }
    }
}