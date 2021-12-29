package com.kd100.jetpacktest.RoomTest

import androidx.room.*

@Dao
interface UserDao {

    @Insert
    fun insertUser(user: UserEntity): Long //自动将生成的主键ID返回

    @Update
    fun updateUser(newUser:UserEntity)

    @Query("select * from UserEntity")
    fun loadAllUsers():List<UserEntity>

    @Query("select * from UserEntity where firstName = :mFirstName")
    fun loadByFirstName(mFirstName:String):List<UserEntity>

    @Delete
    fun deleteUser(user: UserEntity)

    @Query("delete from UserEntity where lastName = :mLastName")
    fun deleteUserByLastName(mLastName: String):Int

}