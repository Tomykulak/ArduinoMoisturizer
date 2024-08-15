package tomykulak.gardenmoisturizer.ui.screens.homepage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import tomykulak.gardenmoisturizer.ui.elements.*
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import tomykulak.gardenmoisturizer.R

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
    BaseScreen(
        topBarText = "Garden Moisture",
        drawFullScreenContent = true,
        showLoading = uiState.value.loading,
        placeholderScreenContent =
        if (uiState.value.errors != null)
            PlaceholderScreenContent(null, stringResource(id = uiState.value.errors!!.communicationError))
        else
            null
        ,
        navigator = navigator
    ) {
        HomepageScreenContent(
            paddingValues = paddingValues,
            uiState = uiState.value
        )
    }

}


@Composable
fun HomepageScreenContent(
    paddingValues: PaddingValues,
    uiState: UiState<MoistureData, HomepageErrors>,
) {
    LazyColumn(modifier = Modifier
        .padding(paddingValues = paddingValues)
        .fillMaxSize(),
        //verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uiState.data != null){
            val moistureData = uiState.data
            item {
                FlowerImageContainer(
                    moistureData
                )
            }
        } else {
            item {
                Text(text = "No data available.")
            }
        }
    }
}

