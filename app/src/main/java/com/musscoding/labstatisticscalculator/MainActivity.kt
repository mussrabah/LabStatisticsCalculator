package com.musscoding.labstatisticscalculator

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.musscoding.labstatisticscalculator.navigation.Route
import com.musscoding.labstatisticscalculator.presentation.manage_paramaters.ManageParameterScreen
import com.musscoding.labstatisticscalculator.presentation.monthly_statistics_calculation.MonthlyStatisticsCalculation
import com.musscoding.labstatisticscalculator.presentation.see_results.SeeResultsScreen
import com.musscoding.labstatisticscalculator.presentation.statistics_menu.StatisticsMenuScreen
import com.musscoding.labstatisticscalculator.ui.theme.LabStatisticsCalculatorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LabStatisticsCalculatorTheme {
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        snackbarHost = {
                            SnackbarHost(snackbarHostState)
                        }
                    ) {values ->
                        NavHost(
                            navController = navController,
                            startDestination = Route.SETTINGS_SCREEN,
                            modifier = Modifier.padding(values)
                        ) {
                            composable(route = Route.SETTINGS_SCREEN) {
                                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                                StatisticsMenuScreen(
                                    onNextClick = { settingsIndex ->
                                        when(settingsIndex) {
                                            0 -> {
                                                navController.navigate(Route.START_MONTHLY_STATS)
                                            }
                                            1 -> {
                                                navController.navigate(Route.START_TRIMESTER_STATS)
                                            }
                                            2 -> {
                                                navController.navigate(Route.MANAGE_DISPO_PARAMS)
                                            }
                                            3 -> {
                                                navController.navigate(Route.SEE_RESULTS)
                                            }
                                        }
                                    }
                                )
                            }
                            composable(Route.START_MONTHLY_STATS) {
                                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                                MonthlyStatisticsCalculation(
                                    snackbarHostState = snackbarHostState,
                                    navigateUp = {
                                        navController.navigateUp()
                                    }
                                )
                            }
                            composable(Route.START_TRIMESTER_STATS) {

                            }
                            composable(Route.MANAGE_DISPO_PARAMS) {
                                ManageParameterScreen(
                                    snackbarHostState = snackbarHostState
                                )
                            }
                            composable(Route.SEE_RESULTS) {
                                SeeResultsScreen(
                                    snackbarHostState = snackbarHostState
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}