package com.net0pyr.entity

import java.time.LocalDateTime
import com.net0pyr.enums.*
import com.net0pyr.location.*
import com.net0pyr.army.*
import com.net0pyr.exceptions.MyIllegalArgumentException
import kotlinx.serialization.Serializable

/**
 * Класс описывает космических десантников
 * @property name имя десантника
 * @property coordinates координаты десантника
 * @property health количество здоровья у десантника
 * @property height рост десантника
 * @property category вид войск
 * @property meleeWeapon вид оружия
 * @property chapter отряд
 * @property id
 * @author net0pyr
 */
@Serializable
class SpaceMarine(
    var name: String?,
    var coordinates: Coordinates?,
    var health: Double?,
    var height: Int? = null,
    var category: AstartesCategory?,
    var meleeWeapon: MeleeWeapon? = null,
    var chapter: Chapter? = null,
    var id: Long = 0,
    var creator: Int = 0
) : Comparable<SpaceMarine> {
    @Serializable(with = LocalDateTimeSerializer::class)
    /**Поле даты создания*/
    private val creationDate: LocalDateTime = LocalDateTime.now()

    init {
        fun checkHealth(health: Double?) {
            if (health == null) {
                throw MyIllegalArgumentException("Пехотинец прошел жестокую подготовку и был воспитан войном. Его здоровье не может быть нулевым.")
            } else if (health <= 0) {
                throw MyIllegalArgumentException("Пехотинец прошел жестокую подготовку и был воспитан войном. Его здоровье не может быть отрицательным.")
            }
        }
        try {
            checkHealth(health!!);
        } catch (e: MyIllegalArgumentException) {
            println("Ошибка: ${e.message}")
        }

        fun checkName(name: String?) {
            if (name == "" || name == null) {
                throw MyIllegalArgumentException("Пехотинецу нужно имя, иначе его не смогут опознать после ранения.")
            }
        }
        try {
            checkName(name);
        } catch (e: MyIllegalArgumentException) {
            println("Ошибка: ${e.message}")
        }

        fun checkCoordinates(coordinates: Coordinates?) {
            if (coordinates == null) {
                throw MyIllegalArgumentException("Пехотинец славно служит империуму, нельзя выкидывать его в космос! Задайте координаты его корабля.")
            }
        }
        try {
            checkCoordinates(coordinates);
        } catch (e: MyIllegalArgumentException) {
            println("Ошибка: ${e.message}")
        }

        fun checkCategory(category: AstartesCategory?) {
            if (category == null) {
                throw MyIllegalArgumentException("Пехотинец определен как дезертир и не числится в составе армии, пока не будет задан вид войск, к которым он принадлежит! Задайте вид войск, к которым принадлежит пехотинец.")
            }
        }
        try {
            checkCategory(category);
        } catch (e: MyIllegalArgumentException) {
            println("Ошибка: ${e.message}")
        }
    }

    /** Метод переопределяющий компоратор
     * @param SpaceMarine объект с которым сравниваем
     */
    override fun compareTo(other: SpaceMarine): Int {
        return this.id.compareTo(other.id)
    }

    override fun toString(): String {
        return "Имя - $name, id - $id"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SpaceMarine

        if (health != other.health) return false
        if (height != other.height) return false

        return true
    }

    override fun hashCode(): Int {
        var result = health?.hashCode() ?: 0
        result = 31 * result + (height ?: 0)
        return result
    }


}