package com.rsd.roomvsrealm.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rsd.roomvsrealm.data.User
import com.rsd.roomvsrealm.data.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

class UserViewModel(
    private val userDao: UserDao,
) : ViewModel() {

    private val _state = MutableStateFlow(MainScreenState())
    val state = _state
    private var job: Job? = null

    @OptIn(ExperimentalTime::class)
    fun populateDataToDb() {
        Log.d("RSD", "populateDataToDb")
        _state.value = _state.value.copy(isLoading = true)

        val listOfUsers = mutableListOf<User>()
        for (i: Int in 1..10000) {
            listOfUsers.add(User(i, "name $i", "surname $i", i * 2))
        }

        job?.cancel()
        job = viewModelScope.launch {
            val time = measureTimedValue {
                withContext(Dispatchers.IO) {
                    if (isActive) {
                        userDao.insertAll(listOfUsers)
                    }
                }
            }
            _state.value = _state.value.copy(
                insertOperationResult = "${time.duration.inWholeMilliseconds} ms",
                isLoading = false
            )
            Log.d("RSD", "populateDataToDb: ${time.duration.inWholeMilliseconds} ms")
        }
    }

    @OptIn(ExperimentalTime::class)
    fun getUsersFromDb() {
        Log.d("RSD", "getUsersFromDb")
        _state.value = _state.value.copy(isLoading = true)
        job?.cancel()
        job = viewModelScope.launch {
            val time = measureTimedValue {
                withContext(Dispatchers.IO) {
                    if (isActive) {
                        userDao.getAll()
                    }
                }
            }
            _state.value = _state.value.copy(
                insertOperationResult = "${time.duration.inWholeMilliseconds} ms",
                isLoading = false
            )
            Log.d("RSD", "getUsersFromDb: ${time.duration.inWholeMilliseconds} ms")
        }
    }
}