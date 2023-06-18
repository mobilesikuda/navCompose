package ru.sikuda.mobile.nav1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.sikuda.mobile.nav1.ui.theme.Nav1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Nav1Theme {

                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyNavHost(navHostController = navController)
                }
            }
        }
    }
}

object NavRoute {
    val SCREEN_A = "ScreenA"
    val SCREEN_B = "ScreenB"
    val SCREEN_C = "ScreenC"
}

@Composable
fun MyNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = NavRoute.SCREEN_A
    ) {
        val routeWithArguments = "${NavRoute.SCREEN_B}/{argument}"
        val routeWithArgumentsC = "${NavRoute.SCREEN_C}/{visible}"

        composable(NavRoute.SCREEN_A) {
            ScreenA {
                navHostController.navigate("${NavRoute.SCREEN_B}/Hakuna Matata")
            }
        }
        composable(
            routeWithArguments,
            arguments = listOf(navArgument(name = "argument") {})
        ) { navEntry ->
            ScreenB(navEntry.arguments?.getString("argument")) {
                navHostController.navigate("${NavRoute.SCREEN_C}/false")
            }
        }
        composable(
            routeWithArgumentsC,
            arguments = listOf(navArgument("visible") {
                type = NavType.BoolType
            }),
        ) {
            ScreenC(it.arguments?.getBoolean("visible")) {
                navHostController.navigate(NavRoute.SCREEN_A) {
                    popUpTo(NavRoute.SCREEN_A) { inclusive = true }
                }
            }
        }

    }
}
