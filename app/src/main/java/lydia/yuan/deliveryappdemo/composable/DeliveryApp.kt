package lydia.yuan.deliveryappdemo.composable

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import lydia.yuan.deliveryappdemo.ui.theme.DeliveryAppDemoTheme
import java.lang.reflect.Modifier

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

