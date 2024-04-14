package com.net0pyr.commands

import com.net0pyr.examples.CommandExample
import com.net0pyr.toolsForChanging.CommandHandler
import java.io.File

/**
 * Класс команды help
 * @author net0pyr
 */
class Help : CommandExample() {
    /**Поле описания команды*/
    override val commandDescription = "help: Выводит справку по доступным командам"

    /** Метод исполнения команды
     * @param commandArgument аргумент команды
     */
    override fun <T> commandExecution(commandArgument: T) {
        if (commandArgument != null && commandArgument != "") {
            println("\u001B[31mКоманда help не имеет таких аргуметов.\u001B[0m\nВоспользуйтесь командой help, чтобы получить дополнительную информацию")
        } else {
            CommandHandler.commands.values.forEach { command ->
                println(command.getCommandDescription())
            }
        }
    }
}