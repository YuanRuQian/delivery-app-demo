package lydia.yuan.deliveryappdemo.composable

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import lydia.yuan.deliveryappdemo.utils.haversine

// TODO: https://github.com/android/platform-samples/blob/main/samples/location/src/main/java/com/example/platform/location/currentLocation/CurrentLocationScreen.kt
@SuppressLint("MissingPermission")
@Composable
fun CurrentLocationScreen(setCurrentLocation: (Location) -> Unit) {
    val permissions = listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )
    PermissionBox(
        permissions = permissions,
        requiredPermissions = listOf(permissions.first()),
        onGranted = {
            CurrentLocationContent(
                usePreciseLocation = it.contains(Manifest.permission.ACCESS_FINE_LOCATION),
                setCurrentLocation = setCurrentLocation
            )
        },
    )
}


@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION],
)
@Composable
fun CurrentLocationContent(usePreciseLocation: Boolean, setCurrentLocation: (Location) -> Unit) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val locationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }


    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = {
                //To get more accurate or fresher device location use this method
                scope.launch(Dispatchers.IO) {
                    val priority = if (usePreciseLocation) {
                        Priority.PRIORITY_HIGH_ACCURACY
                    } else {
                        Priority.PRIORITY_BALANCED_POWER_ACCURACY
                    }
                    val result = locationClient.getCurrentLocation(
                        priority,
                        CancellationTokenSource().token,
                    ).await()
                    result?.let { fetchedLocation ->
                        run {
                            setCurrentLocation(fetchedLocation)
                            Log.d(
                                "CurrentLocationContent",
                                "Current location is \n" + "lat : ${fetchedLocation.latitude}\n" +
                                        "long : ${fetchedLocation.longitude}\n" + "fetched at ${System.currentTimeMillis()}"
                            )
                        }
                    }
                }
            },
        ) {
            Text(text = "Get current location")
        }
    }
}

@Composable
fun NearestStoreScreen(currentLocation: Location, setCurrentLocation: (Location) -> Unit) {
    val utah = LatLng(40.8876432, -111.87893)
    val texas = LatLng(29.756796, -95.8858556)
    val northCarolina = LatLng(35.595997, -78.7748381)

    val storesLocation = listOf(utah, texas, northCarolina)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(
                currentLocation.latitude,
                currentLocation.longitude
            ), 2f
        )
    }

    val (nearestStore, setNearestStore) = remember {
        mutableStateOf<LatLng?>(null)
    }

    LaunchedEffect(key1 = currentLocation) {
        val currentNearestStore = storesLocation.minByOrNull {
            haversine(
                currentLocation.latitude,
                currentLocation.longitude,
                it.latitude,
                it.longitude
            )
        }

        if (currentNearestStore != null) {
            cameraPositionState.animate(
                update = CameraUpdateFactory.newCameraPosition(
                    CameraPosition(
                        LatLng((currentLocation.latitude + currentNearestStore.latitude)/2, (currentLocation.longitude + currentNearestStore.longitude)/2),
                        6f,
                        0f,
                        0f
                    )
                ),
                durationMs = 1000
            )
        }

        Log.d(
            "NearestStoreScreen", "Current location is \n" + "lat : ${currentLocation.latitude}\n" +
                    "long : ${currentLocation.longitude}\n" + "fetched at ${System.currentTimeMillis()}"
        )


        setNearestStore(currentNearestStore)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CurrentLocationScreen(setCurrentLocation = setCurrentLocation)
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {


            if (nearestStore != null) {
                Marker(
                    state = MarkerState(
                        position = nearestStore
                    ),
                    title = "Nearest Store"
                )
                Marker(
                    state = MarkerState(
                        position = LatLng(
                            currentLocation.latitude,
                            currentLocation.longitude
                        )
                    ),
                    title = "Current Location",
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
                )
            } else {
                storesLocation.forEach { location ->
                    Marker(
                        state = MarkerState(
                            position = location
                        )
                    )
                }
            }
        }
    }
}
