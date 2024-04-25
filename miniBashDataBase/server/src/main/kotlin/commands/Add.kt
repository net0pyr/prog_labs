package com.net0pyr.commands

import com.net0pyr.WorkingWithCommand.CommandHandler
import com.net0pyr.entity.SpaceMarineInTreeSet
import java.util.*

/**
 * Класс команды add
 * @author net0pyr
 */
class Add : CommandExample() {
    /**Поле описания команды*/
    override val commandDescription = "add: Добавить новый элемент в коллекцию"

    /** Метод исполнения команды
     * @param commandArgument аргумент команды
     */
    override fun <T> commandExecution(commandArgument: T): String? {
        return if (commandArgument != null && commandArgument != "") {
            "\u001B[31mКоманда add не имеет таких аргуметов.\u001B[0m\nВоспользуйтесь командой help, чтобы получить дополнительную информацию"
        } else {
            try {
                val spaceMarine = CommandHandler.spaceMarine
                if (spaceMarine != null) {
                    SpaceMarineInTreeSet.spaceMarines.add(spaceMarine)
                    "Элемент успешно добавлен"
                } else {
                    null
                }
            } catch (e: InputMismatchException) {
                "\u001B[31mОшибка:\u001B[0m Неверный формат ввода"
            }
        }
    }
}