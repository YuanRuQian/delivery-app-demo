package lydia.yuan.deliveryappdemo.viewmodel

import android.location.Location
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LocationViewModel : ViewModel() {
    private val _currentLocation = MutableStateFlow(Location(""))
    val currentLocation: StateFlow<Location> get() = _currentLocation

    fun updateLocation(location: Location) {
        _currentLocation.value = location
    }
}