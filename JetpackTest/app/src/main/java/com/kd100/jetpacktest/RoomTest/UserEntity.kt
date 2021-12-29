package com.kd100.jetpacktest.RoomTest

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity //声明为实体类
data class UserEntity(var firstName: String, var lastName: String, var age: Int) {
    @PrimaryKey(autoGenerate = true)//设置为主键，自动生成值
    var id:Long = 0
}