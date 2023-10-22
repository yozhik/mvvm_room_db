package com.rsd.roomvsrealm.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rsd.roomvsrealm.ui.viewmodels.MainScreenState

@Composable
fun MainScreen(
    state: MainScreenState,
    onPopulateRoomDatabase: () -> Unit,
    onSelectUsersFromRoomDatabase: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box() {
            Row {
                Column {
                    FilledTonalButton(
                        onClick = onPopulateRoomDatabase,
                        modifier = Modifier.wrapContentSize()
                    ) {
                        Text("Populate Room database")
                    }

                    FilledTonalButton(
                        onClick = onSelectUsersFromRoomDatabase,
                        modifier = Modifier.wrapContentSize()
                    ) {
                        Text("Select all users Room")
                    }
                }

                Text(
                    text = "Time ${state.insertOperationResult}",
                    modifier = Modifier.wrapContentSize()
                )
            }

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(64.dp)
                        .align(Alignment.Center),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
        }
    }
}