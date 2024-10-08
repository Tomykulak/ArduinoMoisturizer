package tomykulak.gardenmoisturizer.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

import tomykulak.gardenmoisturizer.ui.screens.homepage.NavGraphs
import tomykulak.gardenmoisturizer.ui.theme.GardenMoisturizerTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GardenMoisturizerTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}