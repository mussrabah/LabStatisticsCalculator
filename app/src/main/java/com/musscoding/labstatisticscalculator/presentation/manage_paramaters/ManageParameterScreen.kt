package com.musscoding.labstatisticscalculator.presentation.manage_paramaters

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.musscoding.labstatisticscalculator.R
import com.musscoding.labstatisticscalculator.presentation.manage_paramaters.components.AddParameterDialog
import com.musscoding.labstatisticscalculator.presentation.manage_paramaters.components.EditParameterDialog
import com.musscoding.labstatisticscalculator.presentation.manage_paramaters.components.ManageParameterCore
import com.musscoding.labstatisticscalculator.util.DialogTypes
import com.musscoding.labstatisticscalculator.util.UiEvents

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageParameterScreen(
    modifier: Modifier = Modifier,
    viewModel: ManageParameterViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState
) {
    var addParamDialogVisibility by remember {
        mutableStateOf(false)
    }
    var editParamDialogVisibility by remember {
        mutableStateOf(false)
    }
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {event ->
            when(event) {
                is UiEvents.ShowDialog -> {
                    when(event.dialogType) {
                        is DialogTypes.AddParameterDialog -> {
                            addParamDialogVisibility = true
                        }
                        is DialogTypes.EditParameterDialog -> {
                            editParamDialogVisibility = true
                        }

                        else -> {}
                    }
                }
                is UiEvents.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        event.message.asString(context)
                    )
                }
                else -> {}
            }
        }
    }
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(ManageParameterEvents.OnShowAddParameterDialog)
                }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_parameter)
                )
            }
        }
    ) {paddingValues ->
        ManageParameterCore(
            parameters = state.parameters,
            onClickParameter = {parameter ->
                viewModel.onEvent(ManageParameterEvents.OnShowEditParameterDialog(parameter))
            },
            padding = paddingValues
        )
    }

    val density = LocalDensity.current
    AddParameterDialog(
        visible = addParamDialogVisibility,
        onDismissRequest = {
                           addParamDialogVisibility = false
        },
        onSaveParameter = {
            viewModel.onEvent(
                ManageParameterEvents
                    .AddParameter(parameterName = it)
            )
            addParamDialogVisibility = false
        },
        density = density
    )
    if (state.clickedOnParameter != null)
        EditParameterDialog(
            visible = editParamDialogVisibility,
            onDismissRequest = {
                               editParamDialogVisibility = false
            },
            onUpdateParameter = {
                                viewModel
                                    .onEvent(
                                        ManageParameterEvents.OnUpdateParameter(
                                        parameter = state.clickedOnParameter,
                                        newName = it
                                        )
                                    )
                editParamDialogVisibility = false
            },
            onDeleteParameter = {
                                viewModel
                                    .onEvent(
                                        ManageParameterEvents
                                            .OnDeleteParameter(state.clickedOnParameter)
                                    )
                editParamDialogVisibility = false
            },
            density = density,
            text = state.clickedOnParameter.name
        )
    /*
    AnimatedVisibility(
        visible = addParamDialogVisibility,
        enter = slideInVertically {
        // Slide in from 40 dp from the top.
        with(density) { -40.dp.roundToPx() }
    } + expandVertically(
        // Expand from the top.
        expandFrom = Alignment.Top
    ) + fadeIn(
        // Fade in with the initial alpha of 0.3f.
        initialAlpha = 0.3f
    ),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        Dialog(onDismissRequest = { addParamDialogVisibility = false }) {
            var parameterNameValue by remember {
                mutableStateOf("")
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.tertiaryContainer)

            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.onTertiaryContainer)
                            .animateEnterExit(
                                // Slide in/out the inner box.
                                enter = slideInVertically(),
                                exit = slideOutVertically(
                                    targetOffsetY = { fullHeight ->
                                        fullHeight / 3
                                    }
                                )
                            ),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Enter a new parameter",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(vertical = 10.dp),
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        TextField(
                            value = parameterNameValue,
                            onValueChange = {
                                parameterNameValue = it
                            },
                            placeholder = {
                                Text(text = "Input a parameter name", fontSize = 24.sp)
                            },
                            label = {
                                Text(text = "name", fontSize = 18.sp)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 10.dp)
                            .animateEnterExit(
                                // Slide in/out the inner box.
                                enter = slideInHorizontally(),
                                exit = slideOutHorizontally(
                                    targetOffsetX = { fullWidth ->
                                        fullWidth
                                    })
                            ),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            //modifier = Modifier.align(Alignment.CenterHorizontally),
                            onClick = {
                                      addParamDialogVisibility = false
                            },
                            shape = RoundedCornerShape(10.dp),
                            color = Color.Transparent,
                            border = BorderStroke(
                                Dp.Hairline,
                                MaterialTheme.colorScheme.onSecondary
                            ),
                            tonalElevation = 5.dp
                        ) {
                            Text(
                                text = "Cancel",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Medium,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier
                                    .widthIn(120.dp)
                                    .padding(vertical = 8.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                        Button(
                            onClick = { 
                                viewModel.onEvent(
                                    ManageParameterEvents
                                        .AddParameter(parameterName = parameterNameValue)
                                )
                                addParamDialogVisibility = false
                            }
                        ) {
                            Text(
                                text = "Save",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }

     */
}