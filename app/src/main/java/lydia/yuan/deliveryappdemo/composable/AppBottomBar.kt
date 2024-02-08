package lydia.yuan.deliveryappdemo.composable

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Recycling
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

fun navigationWithDestinationPreCheck(
    setCurrentRoute: (String) -> Unit,
    navController: NavController,
    destination: String,
    navBackStackEntry: NavBackStackEntry?
) {
    if (navBackStackEntry?.destination?.route != destination) {
        navController.navigate(destination)
        setCurrentRoute(destination)
    }
}

@Composable
fun AppBottomBar(navController: NavController, currentRoute: String, setCurrentRoute: (String) -> Unit) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val icons = listOf(
        Icons.Default.Place,
        Icons.Default.Recycling,
    )

    val routes = listOf(
        Screen.NearestStoreScreen.route,
        Screen.CarouselScreen.route,
    )

    val zippedList = icons.zip(routes)

    NavigationBar {
        zippedList.forEachIndexed { _, (icon, route) ->
            NavigationBarItem(
                icon = { Icon(icon, contentDescription = route) },
                label = { Text(route) },
                selected = currentRoute == route,
                onClick = {
                    navigationWithDestinationPreCheck(
                        setCurrentRoute,
                        navController,
                        route,
                        navBackStackEntry
                    )
                }
            )
        }
    }
}

