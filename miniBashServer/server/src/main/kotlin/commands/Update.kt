package com.net0pyr.commands

import com.net0pyr.WorkingWithCommand.CommandHandler
import com.net0pyr.entity.SpaceMarineInTreeSet
import java.util.*

/**
 * Класс команды update id
 * @author net0pyr
 */
class Update : CommandExample() {
    /**Поле описания команды*/
    override val commandDescription = "update id: обновить значение элемента колллекции, id которого равен заданному"

    /** Метод исполнения команды
     * @param commandArgument аргумент команды
     */
    override fun <T> commandExecution(commandArgument: T): String? {
        val id: Long?
        try {
            id = commandArgument?.toString()?.toLong()
            try {
                val changingSpaceMarine = SpaceMarineInTreeSet.spaceMarines.find { it.getId() == id }
                SpaceMarineInTreeSet.spaceMarines.remove(changingSpaceMarine)
                try {
                    val spaceMarine = CommandHandler.spaceMarine
                    if (id != null) {
                        spaceMarine?.setId(id)
                    }
                    if (spaceMarine != null) {
                        SpaceMarineInTreeSet.spaceMarines.add(spaceMarine)
                    }
                    return "Элемент успешно обновлен"
                } catch (e: InputMismatchException) {
                    return "\u001B[31mОшибка:\u001B[0m Неверный формат ввода"
                }
            } catch (e: NoSuchElementException) {
                return "\u001B[31mОшибка:\u001B[0m Не существует десантника с таким id"
            } catch (e: NullPointerException) {
                return "\u001B[31mОшибка:\u001B[0m Не существует десантника с таким id"
            }
        } catch (e: NumberFormatException) {
            return "\u001B[31mКоманда update не имеет таких аргуметов.\u001B[0m\nВоспользуйтесь командой help, чтобы получить дополнительную информацию"
        }
    }
}