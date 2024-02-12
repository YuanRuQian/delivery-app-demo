package lydia.yuan.deliveryappdemo.viewmodel

import android.location.Location
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.PlaceTypes
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import lydia.yuan.deliveryappdemo.composable.MyApplication
import lydia.yuan.deliveryappdemo.network.GooglePlacesRepository
import lydia.yuan.deliveryappdemo.network.GooglePrediction
import lydia.yuan.deliveryappdemo.network.Resource
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class LocationViewModel @Inject constructor(private val placesClient: PlacesClient) : ViewModel() {
    private val _currentLocation = MutableStateFlow(Location(""))
    val currentLocation: StateFlow<Location> get() = _currentLocation

    val isLoading = mutableStateOf(false)

    private val _predictions = MutableStateFlow<List<AutocompletePrediction>>(emptyList())
    val predictions: StateFlow<List<AutocompletePrediction>> get() = _predictions

    fun getPredictions(input: String) {
        viewModelScope.launch {
            getAddressPredictions(inputString = input).let {
                _predictions.value = it
            }
        }
    }

    suspend fun getAddressPredictions(
        sessionToken: AutocompleteSessionToken = AutocompleteSessionToken.newInstance(),
        inputString: String,
        location: LatLng? = null
    ) = suspendCoroutine<List<AutocompletePrediction>> {
        Log.d("LocationViewModel", "getAddressPredictions: $inputString")

        placesClient.findAutocompletePredictions(
            FindAutocompletePredictionsRequest.builder()
                .setOrigin(location)
                // Call either setLocationBias() OR setLocationRestriction().
                // .setLocationBias(bounds)
                // .setLocationRestriction(bounds)
                .setCountries("US")
                .setTypesFilter(listOf(PlaceTypes.ADDRESS))
                .setSessionToken(sessionToken)
                .setQuery(inputString)
                .build()
        ).addOnCompleteListener { completedTask ->
            if (completedTask.exception != null) {
                Log.e("LocationViewModel", "getAddressPredictions: ${completedTask.exception?.stackTraceToString()}")
                // Log.e(this@GooglePlacesApi.toString(), completedTask.exception?.stackTraceToString().orEmpty())
                it.resume(listOf())
            } else {
                Log.d("LocationViewModel", "getAddressPredictions: ${completedTask.result.autocompletePredictions}")
                it.resume(completedTask.result.autocompletePredictions)
            }
        }
    }

//    fun getPredictions(address: String) {
//        viewModelScope.launch {
//            isLoading.value = true
//            val response = placesRepository.getPredictions(input = address)
//            when(response){
//                is Resource.Success -> {
//                    _predictions.value = response.data?.predictions!!
//                }
//
//                else -> {}
//            }
//
//            isLoading.value = false
//        }
//    }
//
//    fun onSearchAddressChange(address: String){
//        Log.d("LocationViewModel", "onSearchAddressChange: $address")
//        getPredictions(address)
//    }

    fun updateLocation(location: Location) {
        _currentLocation.value = location
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication)
                LocationViewModel(placesClient = application.container.placesClient)
            }
        }
    }
}