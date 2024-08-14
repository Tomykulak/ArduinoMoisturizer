package tomykulak.gardenmoisturizer.ui.screens.homepage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import tomykulak.gardenmoisturizer.model.UiState
import tomykulak.gardenmoisturizer.model.moistureSensor.MoistureData
@RootNavGraph(start = true)
@Destination
@Composable
fun HomepageScreen(
    navigator: DestinationsNavigator
) {
    val viewModel = hiltViewModel<HomepageViewModel>()
     val uiState: MutableState<UiState<MoistureData, HomepageErrors>> = rememberSaveable { mutableStateOf(UiState()) }

    uiState.value = viewModel.moistureSensorUIState.value

    val paddingValues = PaddingValues()

    HomepageScreenContent(
        paddingValues = paddingValues,  // Pass paddingValues explicitly
        uiState = uiState.value,
        navigator = navigator,
        viewModel = viewModel
    )
}


@Composable
fun HomepageScreenContent(
    paddingValues: PaddingValues,
    uiState: UiState<MoistureData, HomepageErrors>,
    navigator: DestinationsNavigator,
    viewModel: HomepageViewModel
) {
    Column(
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        // Create a local immutable reference to the data property
        val moistureData = uiState.data

        // Check if the data is available
        if (moistureData != null) {
            // Extract the int and float values from MoistureData
            val moistureInt = moistureData.int
            val moistureFloat = moistureData.float

            // Display the values
            Text(text = "Moisture Int: $moistureInt")
            Text(text = "Moisture Float: $moistureFloat")
        } else {
            // If there's an error or no data, display an appropriate message
            Text(text = "Loading data or no data available.")
        }
    }
}

