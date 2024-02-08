package lydia.yuan.deliveryappdemo.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun DeliveryApp() {
    val navController = rememberNavController()
    val startDestination = Screen.AddressAutoComplete.route
    val (currentRoute, setCurrentRoute) = remember { mutableStateOf(Screen.AddressAutoComplete.route) }


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
                composable(Screen.AddressAutoComplete.route) {
                    AddressAutoCompleteScreen()
                }
                composable(Screen.CarouselScreen.route) {
                    CarouselScreen()
                }
            }
        }
    }
}

