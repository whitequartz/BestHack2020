package com.godelsoft.besthack

import android.graphics.Color
import java.lang.Integer.max
import java.lang.Integer.min
import java.util.*

enum class DeviceType(private val displayName: String) {
    SYSTEM("Системный блок"),
    MONITOR("Монитор"),
    KEYBOARD("Клавиатура"),
    MOUSE("Мышь"),
    HEADPHONES("Наушники"),
    CAMERA("Веб-камера"),
    MICROPHONE("Микрофон"),
    WIFI("WI-FI роутер");

    override fun toString(): String {
        return displayName
    }
}
class Device(
    val type: DeviceType,
    val model: String,
    val cost: Int,
    val buyTime: Calendar,
    val validTime: Long
) {
    fun getProgress(): Int {
        return 100 - min(max(0, ((Calendar.getInstance().timeInMillis - buyTime.timeInMillis).toDouble() / (getInvalidDate().timeInMillis - buyTime.timeInMillis).toDouble() * 100).toInt()), 100)
    }

    fun getProgressColor(): Int {
       val progress = getProgress().toFloat() / 100f
        if (progress == 1f) return Color.rgb(0f, 200f / 255f, 0f)
        return if (progress < 0.5) {
            Color.rgb(255, (progress * 2 * 200).toInt(), 0)
        } else {
            Color.rgb(((1 - progress) * 2 * 255).toInt(),200, 0)
        }
    }

    fun getInvalidDate(): Calendar {
        return Calendar.getInstance().apply {
            set(buyTime.get(Calendar.YEAR),buyTime.get(Calendar.MONTH),buyTime.get(Calendar.DAY_OF_MONTH))
            add(Calendar.SECOND, (validTime / 1000L).toInt())
        }
    }
}