package com.net0pyr.commands

import com.net0pyr.WorkingWithCommand.CommandHandler
import com.net0pyr.entity.SpaceMarineInTreeSet
import java.util.*

/**
 * Класс команды add_if_max
 * @author net0pyr
 */
class Add_if_max : CommandExample() {
    /**Поле описания команды*/
    override val commandDescription =
        "add_if_max: Добавить новый элемент в коллекцию, если его значение превышает знаение наибольшего"

    /** Метод исполнения команды
     * @param commandArgument аргумент команды
     */
    override fun <T> commandExecution(commandArgument: T): String? {
        return if (commandArgument != null && commandArgument != "") {
            "\u001B[31mКоманда add_if_max не имеет таких аргуметов.\u001B[0m\nВоспользуйтесь командой help, чтобы получить дополнительную информацию"
        } else {
            try {
                val spaceMarine = CommandHandler.spaceMarine
                if (spaceMarine != null)
                    if (spaceMarine > SpaceMarineInTreeSet.spaceMarines.last()) {
                        SpaceMarineInTreeSet.spaceMarines.add(spaceMarine)
                    }
                "Элемент успешно добавлен"
            } catch (e: InputMismatchException) {
                "\u001B[31mОшибка:\u001B[0m Неверный формат ввода"
            }
        }
    }
}
