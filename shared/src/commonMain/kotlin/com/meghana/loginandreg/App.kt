package com.meghana.loginandreg

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.meghana.loginandreg.navigation.Screen
import com.meghana.loginandreg.repository.UserRepository
import com.meghana.loginandreg.ui.screens.HomeScreen
import com.meghana.loginandreg.ui.screens.LoginScreen
import com.meghana.loginandreg.ui.screens.RegisterScreen
import com.meghana.loginandreg.ui.screens.SplashScreen
import com.meghana.loginandreg.ui.theme.AppTheme
import com.meghana.loginandreg.viewmodel.HomeViewModel
import com.meghana.loginandreg.viewmodel.LoginViewModel
import com.meghana.loginandreg.viewmodel.RegisterViewModel
import com.meghana.loginandreg.viewmodel.SplashViewModel

@Composable
fun App(userRepository: UserRepository) {
    AppTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Screen.Splash,
        ) {
            composable<Screen.Splash> {
                val viewModel = viewModel { SplashViewModel(userRepository) }
                SplashScreen(
                    viewModel = viewModel,
                    onNavigate = { navigation ->
                        when (navigation) {
                            SplashViewModel.SplashNavigation.ToLogin -> {
                                navController.navigate(Screen.Login) {
                                    popUpTo(Screen.Splash) { inclusive = true }
                                }
                            }
                            SplashViewModel.SplashNavigation.ToHome -> {
                                navController.navigate(Screen.Home) {
                                    popUpTo(Screen.Splash) { inclusive = true }
                                }
                            }
                        }
                    },
                )
            }

            composable<Screen.Login> {
                val viewModel = viewModel { LoginViewModel(userRepository) }
                LoginScreen(
                    viewModel = viewModel,
                    onNavigateToRegister = {
                        navController.navigate(Screen.Register)
                    },
                    onLoginSuccess = {
                        navController.navigate(Screen.Home) {
                            popUpTo(Screen.Login) { inclusive = true }
                        }
                    },
                )
            }

            composable<Screen.Register> {
                val viewModel = viewModel { RegisterViewModel(userRepository) }
                RegisterScreen(
                    viewModel = viewModel,
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onRegisterSuccess = {
                        navController.navigate(Screen.Login) {
                            popUpTo(Screen.Register) { inclusive = true }
                        }
                    },
                )
            }

            composable<Screen.Home> {
                val viewModel = viewModel { HomeViewModel(userRepository) }
                HomeScreen(
                    viewModel = viewModel,
                    onLogout = {
                        navController.navigate(Screen.Login) {
                            popUpTo(Screen.Home) { inclusive = true }
                        }
                    },
                )
            }
        }
    }
}
