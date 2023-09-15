package com.musscoding.labstatisticscalculator.presentation.see_results.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.musscoding.labstatisticscalculator.R

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ShowParameterStatsDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    density: Density,
    text: String,
    statsPercentage: Float
) {
    AnimatedVisibility(
        visible = visible,
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
        Dialog(onDismissRequest = { onDismissRequest() }) {
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
                            text = stringResource(R.string.see_parameter_stats),
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
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = text,
                                fontWeight = FontWeight.Bold,
                                fontSize = 32.sp,
                                style = MaterialTheme.typography.displayLarge,
                                modifier = Modifier
                                    .align(CenterHorizontally)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = if (statsPercentage > 0)
                                    stringResource(
                                        R.string.le_est_plus_demande_ce_mois_avec_un_pourcentage,
                                        text
                                    ) +
                                            stringResource(
                                                R.string.de_que_le_mois_passe,
                                                statsPercentage
                                            )
                                else if (statsPercentage < 0) stringResource(
                                    R.string.le_est_moins_demande_ce_mois_avec_un_pourcentage,
                                    text
                                ) +
                                        stringResource(
                                            R.string.de_que_le_mois_passe,
                                            statsPercentage
                                        )
                                else stringResource(R.string.il_n_y_a_pas_de_donnee_de_mois_precedent),
                                fontWeight = FontWeight.Medium,
                                fontSize = 24.sp,
                                style = MaterialTheme.typography.bodyLarge
                                )
                        }

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
                        Button(
                            onClick = {
                                onDismissRequest()
                            },
                        ) {
                            Text(
                                text = stringResource(R.string.ok),
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
}
