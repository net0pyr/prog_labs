package com.net0pyr.commands

import com.net0pyr.army.Chapter
import com.net0pyr.entity.SpaceMarine

/**
 * Класс команды history
 * @author net0pyr
 */
class History : CommandExample() {
    /**Поле описания команды*/
    override val commandDescription = "history - вывести последние 6 команд"

    companion object {
        /**Статическое поле хранящее историю команд*/
        var history = mutableMapOf<Int,MutableList<String>>()
    }

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
            "\u001B[31mКоманда history не имеет таких аргуметов.\u001B[0m\nВоспользуйтесь командой help, чтобы получить дополнительную информацию"
        } else {
            var linesCount = 0
            var outputString = ""
            history[id]?.forEach() {
                outputString += it+"\n"
            }
            if (history[id]?.size == 0) {
                "История пустая"
            } else {
                outputString
            }
        }
    }
}