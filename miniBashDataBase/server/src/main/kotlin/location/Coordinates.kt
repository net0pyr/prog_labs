package com.net0pyr.location

import com.net0pyr.exceptions.MyIllegalArgumentException
import kotlinx.serialization.Serializable

/**
 * Класс описывает координаты
 * @property x координата X
 * @property y координата Y
 * @author net0pyr
 */
@Serializable
class Coordinates(private var x: Float?, private var y: Double?) {
    init {
        fun checkXY(x: Float?, y: Double?) {
            if (x == null || y == null) {
                throw MyIllegalArgumentException("Пехотинец был потерян в космосе! Пожалейте его семью - сообщите нам координаты.")
            } else if (x > 938 || y <= -841) {
                throw MyIllegalArgumentException("Пехотинец не мог зайти так далеко! Сообщите его точные координаты.")
            }
        }
        try {
            checkXY(x, y);
        } catch (e: MyIllegalArgumentException) {
            println("Ошибка: ${e.message}")
        }
    }

}