package com.net0pyr.commands

import com.net0pyr.army.Chapter
import com.net0pyr.entity.SpaceMarine
import com.net0pyr.entity.SpaceMarineInTreeSet
import com.net0pyr.enums.AstartesCategory
import com.net0pyr.enums.MeleeWeapon
import com.net0pyr.examples.CommandExample
import com.net0pyr.location.Coordinates
import java.util.*

/**
 * Класс команды remove_by_id id
 * @author net0pyr
 */
class Remove_by_id : CommandExample() {
    /**Поле описания команды*/
    override val commandDescription = "remove_by_id id: удалить элемент из коллекции по его id"

    /** Метод исполнения команды
     * @param commandArgument аргумент команды
     */
    override fun <T> commandExecution(commandArgument: T) {
        var id: Long?
        try {
            id = commandArgument?.toString()?.toLong()
            try {
                val changingSpaceMarine = SpaceMarineInTreeSet.spaceMarines.find { it.getId() == id }
                SpaceMarineInTreeSet.spaceMarines.remove(changingSpaceMarine)
            } catch (e: NoSuchElementException) {
                println("\u001B[31mОшибка:\u001B[0m Не существует десантника с таким id")
            } catch (e: NullPointerException) {
                println("\u001B[31mОшибка:\u001B[0m Не существует десантника с таким id")
            }
        } catch (e: NumberFormatException) {
            println("\u001B[31mКоманда remove_by_id не имеет таких аргуметов.\u001B[0m\nВоспользуйтесь командой help, чтобы получить дополнительную информацию")
        }
    }
}