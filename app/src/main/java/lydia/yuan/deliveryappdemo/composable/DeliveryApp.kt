package lydia.yuan.deliveryappdemo.composable

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.libraries.places.api.Places
import lydia.yuan.deliveryappdemo.BuildConfig
import lydia.yuan.deliveryappdemo.viewmodel.LocationViewModel

class MyApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()

        Places.initializeWithNewPlacesApiEnabled(applicationContext, BuildConfig.MAPS_API_KEY)

        container = DefaultAppContainer(this)
    }
}

@Composable
fun DeliveryApp(locationViewModel: LocationViewModel = viewModel(factory = LocationViewModel.Factory)) {
    val navController = rememberNavController()
    val startDestination = Screen.NearestStoreScreen.route
    val (currentRoute, setCurrentRoute) = remember { mutableStateOf(Screen.NearestStoreScreen.route) }

    val currentLocationData = locationViewModel.currentLocation.collectAsState()
    val currentLocation = currentLocationData.value

    val predictionData = locationViewModel.predictions.collectAsState()
    val predictions = predictionData.value

    Scaffold(
        topBar = {
            AppTopBar(navController)
        },
        bottomBar = {
            AppBottomBar(
                navController = navController,
                currentRoute = currentRoute,
                setCurrentRoute = setCurrentRoute
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            NavHost(navController = navController, startDestination = startDestination) {
                composable(Screen.NearestStoreScreen.route) {
                    NearestStoreScreen(currentLocation = currentLocation, setCurrentLocation = locationViewModel::updateLocation)
                }
                composable(Screen.CarouselScreen.route) {
                    CarouselScreen()
                }
                composable(Screen.AddressAutoCompleteScreen.route) {
                    AddressAutoCompleteScreen(onSearchAddressChange = locationViewModel::getPredictions, predictions = predictions)
                }
                composable(Screen.AutoCompleteFragmentScreen.route) {
                    AutoCompleteFragmentScreen()
                }
                composable(Screen.TestFCMScreen.route) {
                    TestFCMScreen(onNavigateToTestImageCachingScreen = {
                        navController.navigate(Screen.TestImageCachingWithAsyncImageScreen.route)
                    })
                }
                composable(Screen.TestImageCachingWithAsyncImageScreen.route) {
                    TestImageCachingWithAsyncImageScreen()
                }
            }
        }
    }
}

