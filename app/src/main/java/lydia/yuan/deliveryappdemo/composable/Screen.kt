package lydia.yuan.deliveryappdemo.composable


import androidx.navigation.NamedNavArgument

sealed class Screen(
    val route: String,
    val displayName: String = route,
    val navArguments: List<NamedNavArgument> = emptyList(),
) {
    data object AddressAutoComplete : Screen("Address Auto Complete")

    data object CarouselScreen : Screen("Carousel")
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