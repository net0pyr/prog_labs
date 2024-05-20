package com.net0pyr.commands

import com.net0pyr.army.Chapter
import com.net0pyr.entity.SpaceMarine
import com.net0pyr.entity.SpaceMarineInTreeSet


/**
 * Класс команды filter_contains_name name
 * @author net0pyr
 */
class Filter_contains_name : CommandExample() {
    /**Поле описания команды*/
    override val commandDescription =
        "filter_contains_name name - вывести элементы, значения поля name которых содержит заданную подстроку"

    /** Метод исполнения команды
     * @param commandArgument аргумент команды
     */
    override fun <T> commandExecution(
        commandArgument: T,
        spaceMarine: SpaceMarine?,
        chapter: Chapter?,
        id: Int
    ): String? {
        return if (commandArgument == null || commandArgument == "") {
            "\u001B[31mКоманда filter_contains_name не имеет таких аргуметов.\u001B[0m\nВоспользуйтесь командой help, чтобы получить дополнительную информацию"
        } else {
            var findFlag = false
            val outputString = StringBuilder()
            SpaceMarineInTreeSet.spaceMarines.forEach {
                if (it.name?.contains(commandArgument.toString()) == true) {
                    outputString.append(it.toString()+"\n")
                    findFlag = true
                }
            }
            if (!findFlag) {
                "Совпадений не найдено"
            } else {
                outputString.toString()
            }
        }
    }
}