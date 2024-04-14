package com.net0pyr.commands

import com.net0pyr.examples.CommandExample
import java.io.File

/**
 * Класс команды history
 * @author net0pyr
 */
class History : CommandExample() {
    /**Поле описания команды*/
    override val commandDescription = "history: вывести последние 6 команд"

    companion object {
        /**Статическое поле хранящее историю команд*/
        var history = mutableListOf<String>()
    }

    /** Метод исполнения команды
     * @param commandArgument аргумент команды
     */
    override fun <T> commandExecution(commandArgument: T) {
        if (commandArgument != null && commandArgument != "") {
            println("\u001B[31mКоманда history не имеет таких аргуметов.\u001B[0m\nВоспользуйтесь командой help, чтобы получить дополнительную информацию")
        } else {
            var linesCount = 0
            for (i in maxOf(0, History.history.size - 7) until History.history.size - 1) {
                if (linesCount == 6) {
                    break
                }
                println(History.history[i])
                linesCount++
            }
            if (history.size == 1) {
                println("История пустая")
            }
        }
    }
}