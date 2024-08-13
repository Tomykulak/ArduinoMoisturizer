package tomykulak.gardenmoisturizer.ui.screens.homepage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination(start=true)
@Composable
fun HomepageScreen(
    navigator: DestinationsNavigator
){
    HomepageScreenContent()
}

@Composable
fun HomepageScreenContent(
    // paddingValues: PaddingValues,
){
    Column {
        Text("Yay");
    }
}