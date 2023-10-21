package com.rsd.roomvsrealm.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class TestRoomDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}