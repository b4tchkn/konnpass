import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.github.b4tchkn.konnpass.ui.screen.EventDetailScreen
import io.github.b4tchkn.konnpass.ui.screen.HomeScreen
import io.github.b4tchkn.konnpass.ui.screen.eventDetailScreenParamId
import io.github.b4tchkn.konnpass.ui.screen.eventDetailScreenRoute
import io.github.b4tchkn.konnpass.ui.screen.navigateToEventDetailScreen

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
                onEventPressed = navController::navigateToEventDetailScreen,
            )
        }
        composable(
            eventDetailScreenRoute,
            arguments = listOf(
                navArgument(eventDetailScreenParamId) {
                    type = NavType.StringType
                },
            ),
        ) {
            EventDetailScreen(
                eventId = it.arguments?.getString(eventDetailScreenParamId),
                onBackPressed = navController::popBackStack,
            )
        }
    }
}
