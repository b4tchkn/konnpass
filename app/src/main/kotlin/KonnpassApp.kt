import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.github.b4tchkn.konnpass.ui.screen.EventDetailScreen
import io.github.b4tchkn.konnpass.ui.screen.HomeScreen

@Composable
fun KonnpassApp() {
    val navController = rememberNavController()
    KonnpassNavHost(navController = navController)
}

@Composable
fun KonnpassNavHost(
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onEventPressed = {
                    navController.navigate("event_detail/${it.title}")
                },
            )
        }
        composable(
            "event_detail/{data}",
            arguments = listOf(
                navArgument("data") {
                    type = NavType.StringType
                },
            ),
        ) {
            EventDetailScreen(it.arguments?.getString("data"))
        }
    }
}
