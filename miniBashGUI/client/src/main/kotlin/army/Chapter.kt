package com.net0pyr.army

import com.net0pyr.exceptions.MyIllegalArgumentException
import kotlinx.serialization.Serializable

/**
 * Класс описывает отряд, в котором состоит солдат
 * @property name название отряда
 * @property parentLegion легионг, к которому принадлежит
 * @property marineCount количество десантников в отряда
 * @property world название мира
 * @author net0pyr
 */
@Serializable
class Chapter(
    var name: String?,
    var parentLegion: String? = null,
    var marineCount: Int?,
    var world: String?
) : Comparable<Chapter> {
    init {
        fun checkName(name: String?) {
            if (name == "" || name == null) {
                throw MyIllegalArgumentException("Отряду нужно имя! Пехотинецы должны знать как зовут их семью.")
            }
        }
        try {
            checkName(name);
        } catch (e: MyIllegalArgumentException) {
            println("Ошибка: ${e.message}")
        }

        fun checkMarineCount(marineCount: Int?) {
            if (marineCount == null || marineCount == 0) {
                throw MyIllegalArgumentException("Отряд не можт существовать без солдат! Введите количество пехотинцев.")
            } else if (marineCount < 0) {
                throw MyIllegalArgumentException("Не стоит вычитать потери отряда. Количество пехотинцев должно быть больше нуля.")
            } else if (marineCount > 1000) {
                throw MyIllegalArgumentException("Отряд больше тысячи может поднять бунт, таких нет в армии. Введите корректное количество пехотинцев.")
            }
        }
        try {
            checkMarineCount(marineCount);
        } catch (e: MyIllegalArgumentException) {
            println("Ошибка: ${e.message}")
        }
    }

    /** Метод переопределяющий компоратор
     * @param Chapter объект с которым сравниваем
     */
    override fun compareTo(other: Chapter): Int {
        return (this.name.hashCode() + this.marineCount!! - other.name.hashCode() - other.marineCount!!).toInt()
    }
}