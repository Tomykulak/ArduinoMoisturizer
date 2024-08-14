package tomykulak.gardenmoisturizer.ui.elements

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BaseScreen(
    topBarText: String?,
    onBackClick: (() -> Unit)? = null,
    showSidePadding: Boolean = true,
    drawFullScreenContent: Boolean = false,
    placeholderScreenContent: PlaceholderScreenContent? = null,
    showLoading: Boolean = false,
    navigator: DestinationsNavigator? = null,
    floatingActionButton: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable (paddingValues: PaddingValues) -> Unit) {

    Scaffold(
        contentColor = Color.Black,
        containerColor = Color.White,
        floatingActionButton = floatingActionButton,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.CenterHorizontally)
                    ) {
                        if(topBarText != null) {
                            Text(
                                text = topBarText,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier
                                    .padding(start = 0.dp)
                                    .weight(5.5f)
                            )
                        }
                    }
                },
                actions = actions,
            )
        }
    ) {
        if (placeholderScreenContent != null){
            PlaceHolderScreen(
                modifier = Modifier.padding(it),
                content = placeholderScreenContent)
        } else if (showLoading){
            LoadingScreen(modifier = Modifier.padding(it))
        } else {
            if (!drawFullScreenContent) {
                LazyColumn(modifier = Modifier.padding(it)) {
                    item {
                        Column(
                            verticalArrangement = Arrangement.Top,
                            modifier = Modifier
                                .padding(if (!showSidePadding) 16.dp else 16.dp)
                        ) {
                            content(it)
                        }
                    }
                }
            } else {
                content(it)
            }
        }
    }

}
