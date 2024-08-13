package tomykulak.gardenmoisturizer.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
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
/*Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
*/