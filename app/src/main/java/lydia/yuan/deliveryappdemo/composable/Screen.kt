package lydia.yuan.deliveryappdemo.composable


import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

sealed class Screen(
    val route: String,
    val displayName: String = route,
    val navArguments: List<NamedNavArgument> = emptyList(),
) {
    data object AddressAutoComplete : Screen("addressAutoComplete")


}


fun getRouteDisplayName(route: String?): String {
    if (route == null) {
        return "Delivery App Demo by Lydia Yuan"
    }
    val routeSegments = route.split("/")
    return if (routeSegments.isNotEmpty()) {
        routeSegments[0]
    } else {
        route
    }
}