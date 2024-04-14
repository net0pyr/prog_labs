package com.net0pyr.commands

import com.net0pyr.entity.SpaceMarineInTreeSet
import com.net0pyr.examples.CommandExample
import java.util.NoSuchElementException

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
    override fun <T> commandExecution(commandArgument: T) {
        if (commandArgument != null && commandArgument != "") {
            println("\u001B[31mКоманда clear не имеет таких аргуметов.\u001B[0m\nВоспользуйтесь командой help, чтобы получить дополнительную информацию")
        } else {
            SpaceMarineInTreeSet.spaceMarines.clear()
        }
    }
}