package lydia.yuan.deliveryappdemo.composable

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun DeliveryApp() {
    val navController = rememberNavController()
    val startDestination = Screen.AddressAutoComplete.route


    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.AddressAutoComplete.route) {
            AddressAutoCompleteScreen()
        }
    }
}

