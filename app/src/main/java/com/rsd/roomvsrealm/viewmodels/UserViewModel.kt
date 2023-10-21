package com.rsd.roomvsrealm.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rsd.roomvsrealm.data.User
import com.rsd.roomvsrealm.data.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

class UserViewModel(
    private val userDao: UserDao,
) : ViewModel() {

    @OptIn(ExperimentalTime::class)
    fun populateDataToDb() {
        Log.d("RSD", "populateDataToDb")
        val time = measureTimedValue {
            for (i: Int in 1..1000) {
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        userDao.insertAll(User(i, "name $i", "surname $i", i * 2))
                    }
                }
            }
        }
        Log.d("RSD", "populateDataToDb: $time")
    }

    @OptIn(ExperimentalTime::class)
    fun getUsersFromDb() {
        Log.d("RSD", "getUsersFromDb")
        val time = measureTimedValue {

            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    val res = userDao.getAll()
                }
            }
        }
        Log.d("RSD", "getUsersFromDb: $time")
    }
}