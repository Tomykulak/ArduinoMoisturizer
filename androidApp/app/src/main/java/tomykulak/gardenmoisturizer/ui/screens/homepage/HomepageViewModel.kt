package tomykulak.gardenmoisturizer.ui.screens.homepage

import tomykulak.gardenmoisturizer.R
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tomykulak.gardenmoisturizer.architecture.BaseViewModel
import tomykulak.gardenmoisturizer.architecture.CommunicationResult
import tomykulak.gardenmoisturizer.communication.moistureSensor.MoistureSensorRemoteRepositoryImpl
import tomykulak.gardenmoisturizer.model.UiState
import tomykulak.gardenmoisturizer.model.moistureSensor.MoistureData
import javax.inject.Inject

@HiltViewModel
class HomepageViewModel @Inject constructor(
    private val moistureSensorRemoteRepositoryImpl: MoistureSensorRemoteRepositoryImpl
) : BaseViewModel()
{

    init {
        startPeriodicDataRefresh()
    }

    val moistureSensorUIState: MutableState<UiState<MoistureData, HomepageErrors>> = mutableStateOf(UiState())

    private fun startPeriodicDataRefresh() {
        viewModelScope.launch {while (true) {
            loadMoistureSensor()
            delay(5000)
            }
        }
    }

    private suspend fun loadMoistureSensor() {
        try {
            val result = withContext(Dispatchers.IO) {
                moistureSensorRemoteRepositoryImpl.getMoistureData()
            }
            when (result) {
                is CommunicationResult.ConnectionError -> {
                    moistureSensorUIState.value = UiState(
                        loading = false,
                        data = null,
                        errors = HomepageErrors(R.string.no_internet_connection)
                    )
                }
                is CommunicationResult.Error -> {
                    moistureSensorUIState.value = UiState(
                        loading = false,
                        data = null,
                        errors = HomepageErrors(R.string.failed_to_load_the_list)
                    )
                    Log.d("LoadingError", result.toString())
                }
                is CommunicationResult.Exception -> {
                    moistureSensorUIState.value = UiState(
                        loading = false,
                        data = null,
                        errors = HomepageErrors(R.string.unknown_error)
                    )
                }
                is CommunicationResult.Success -> {
                    if (result.data.percentage > 0) {
                        moistureSensorUIState.value = UiState(
                            loading = false,
                            data = result.data,
                            errors = null
                        )
                    } else {
                        moistureSensorUIState.value = UiState(
                            loading = false,
                            data = null,
                            errors = HomepageErrors(R.string.list_is_empty)
                        )
                    }
                }
            }
        } catch (e: Exception) {
            // This will catch any unexpected exceptions
            moistureSensorUIState.value = UiState(
                loading = false,
                data = null,
                errors = HomepageErrors(R.string.no_internet_connection)
            )
            Log.e("Exception", "Error loading data: ${e.localizedMessage}")
        }
    }


}