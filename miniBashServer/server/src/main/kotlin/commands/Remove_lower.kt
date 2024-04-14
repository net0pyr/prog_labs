package com.net0pyr.commands

import com.net0pyr.WorkingWithCommand.CommandHandler
import com.net0pyr.entity.Command
import com.net0pyr.entity.SpaceMarineInTreeSet
import java.util.*


/**
 * Класс команды remove_lower
 * @author net0pyr
 */
class Remove_lower : CommandExample() {
    /**Поле описания команды*/
    override val commandDescription = "remove_lower: удалить из коллекции все элементы, меньшие, чем заданный"

    /** Метод исполнения команды
     * @param commandArgument аргумент команды
     */
    override fun <T> commandExecution(commandArgument: T): String? {
        if (commandArgument != null && commandArgument != "") {
            return "\u001B[31mКоманда remove_lower не имеет таких аргуметов.\u001B[0m\nВоспользуйтесь командой help, чтобы получить дополнительную информацию"
        } else {
            try {
                val spaceMarine = CommandHandler.spaceMarine
                if (spaceMarine != null)
                    SpaceMarineInTreeSet.spaceMarines.forEach {
                        if (spaceMarine > it) {
                            SpaceMarineInTreeSet.spaceMarines.remove(it)
                        }
                    }
                return "Удаление успешно завершено"
            } catch (e: InputMismatchException) {
                return "\u001B[31mОшибка:\u001B[0m Неверный формат ввода"
            }
        }
    }
}
