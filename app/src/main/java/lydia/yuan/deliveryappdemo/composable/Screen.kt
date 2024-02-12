package lydia.yuan.deliveryappdemo.composable


import androidx.navigation.NamedNavArgument

sealed class Screen(
    val route: String,
    val displayName: String = route,
    val navArguments: List<NamedNavArgument> = emptyList(),
) {
    data object NearestStoreScreen : Screen("Nearest Store")

    data object CarouselScreen : Screen("Carousel")

    data object AddressAutoCompleteScreen : Screen("Addr Search")

    data object AutoCompleteFragmentScreen : Screen("Addr Auto")
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