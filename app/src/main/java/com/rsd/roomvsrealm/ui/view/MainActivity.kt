package com.rsd.roomvsrealm.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.rsd.roomvsrealm.data.TestRoomDatabase
import com.rsd.roomvsrealm.ui.theme.MyApplicationTheme
import com.rsd.roomvsrealm.ui.viewmodels.MainScreenState
import com.rsd.roomvsrealm.ui.viewmodels.UserViewModel

class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            TestRoomDatabase::class.java,
            "users.db"
        ).build()
    }
    private val viewModel by viewModels<UserViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return UserViewModel(db.userDao()) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                val state by viewModel.state.collectAsState()
                MainScreen(
                    state = state,
                    onPopulateRoomDatabase = viewModel::populateDataToDb,
                    onSelectUsersFromRoomDatabase = viewModel::getUsersFromDb,
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        MainScreen(MainScreenState(
            "23s",
            isLoading = false
        ),
            onPopulateRoomDatabase = {},
            onSelectUsersFromRoomDatabase = {})
    }
}