package com.net0pyr.commands

import com.net0pyr.entity.SpaceMarineInTreeSet

/**
 * Класс команды clear
 * @author net0pyr
 */
class Clear : CommandExample() {
    /**Поле описания команды*/
    override val commandDescription = "clear: очистить коллекцию"

    /** Метод исполнения команды
     * @param commandArgument аргумент команды
     */
    override fun <T> commandExecution(commandArgument: T): String? {
        return if (commandArgument != null && commandArgument != "") {
            "\u001B[31mКоманда clear не имеет таких аргуметов.\u001B[0m\nВоспользуйтесь командой help, чтобы получить дополнительную информацию"
        } else {
            SpaceMarineInTreeSet.spaceMarines.clear()
            "Коллекция очищена"
        }
    }
}