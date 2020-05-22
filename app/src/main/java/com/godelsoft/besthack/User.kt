package com.godelsoft.besthack

import java.util.*
import kotlin.collections.ArrayList

class User(
    var name: String,
    val type: UserType
) {
    var devices: ArrayList<Device> = arrayListOf()
    companion object {
        private val userTest = User("testName", UserType.WORKER).apply {
            devices.addAll(arrayListOf(
                Device(DeviceType.SYSTEM, "ASUS ROG 22-81", 74000, Calendar.getInstance().apply { set(2019, 10, 27) }, 700.daysToMillis()),
                Device(DeviceType.MONITOR, "AOC 23\"", 14000, Calendar.getInstance().apply { set(2019, 10, 27) }, 264.daysToMillis()),
                Device(DeviceType.MONITOR, "AOC 27\"", 17000, Calendar.getInstance().apply { set(2019, 10, 27) }, 124.daysToMillis()),
                Device(DeviceType.KEYBOARD, "ASUS Claymore", 10000, Calendar.getInstance().apply { set(2019, 10, 27) }, 424.daysToMillis()),
                Device(DeviceType.MOUSE, "Zowie EC2-A", 5000, Calendar.getInstance().apply { set(2019, 10, 27) }, 600.daysToMillis()),
                Device(DeviceType.HEADPHONES, "Razer Kraken", 7500, Calendar.getInstance().apply { set(2019, 10, 27) }, 500.daysToMillis()),
                Device(DeviceType.CAMERA, "LG 270", 3000, Calendar.getInstance().apply { set(2019, 10, 27) }, 365.daysToMillis()),
                Device(DeviceType.MICROPHONE, "YETI", 74000, Calendar.getInstance().apply { set(2019, 10, 27) }, 200.daysToMillis()),
                Device(DeviceType.WIFI, "Xiaomi A8", 74000, Calendar.getInstance().apply { set(2019, 10, 27) }, 900.daysToMillis())
            ))
        }
        val current: User
        get() {
            //TODO user from DB
            return userTest
        }
    }
}

enum class UserType(val displayName: String) {
    WORKER("Работник"),
    SUPPORT("Тех. поддержка"),
    TEAM_LEAD("Тимлид");

    override fun toString(): String {
        return displayName
    }
}