package com.net0pyr.commands

import com.net0pyr.WorkingWithCommand.CommandHandler
import com.net0pyr.army.Chapter
import com.net0pyr.entity.Command
import com.net0pyr.entity.SpaceMarine

/**
 * Класс команды help
 * @author net0pyr
 */
class Help : CommandExample() {
    /**Поле описания команды*/
    override val commandDescription = "help - Выводит справку по доступным командам"

    /** Метод исполнения команды
     * @param commandArgument аргумент команды
     */
    override fun <T> commandExecution(
        commandArgument: T,
        spaceMarine: SpaceMarine?,
        chapter: Chapter?,
        id: Int
    ): String? {
        return if (commandArgument != null && commandArgument != "") {
            "\u001B[31mКоманда help не имеет таких аргуметов.\u001B[0m\nВоспользуйтесь командой help, чтобы получить дополнительную информацию"
        } else {
            val outputString = StringBuilder()
            CommandHandler.commands.values.forEach { command ->
                outputString.append(command.getCommandDescription()+"\n")
            }
            outputString.toString()
        }
    }
}