package com.net0pyr.commands

import com.net0pyr.entity.SpaceMarineInTreeSet
import com.net0pyr.examples.CommandExample
import java.io.File

/**
 * Класс команды show
 * @author net0pyr
 */
class Show : CommandExample() {
    /**Поле описания команды*/
    override val commandDescription = "show: Выводит все элементы коллекции в строковом представлении"

    /** Метод исполнения команды
     * @param commandArgument аргумент команды
     */
    override fun <T> commandExecution(commandArgument: T) {
        if (commandArgument != null && commandArgument != "") {
            println("\u001B[31mКоманда show не имеет таких аргуметов.\u001B[0m\nВоспользуйтесь командой help, чтобы получить дополнительную информацию")
        } else {
            if (SpaceMarineInTreeSet.spaceMarines.isEmpty()) {
                println("Коллекция пустая")
            }
            SpaceMarineInTreeSet.spaceMarines.forEach {
                println(it.toString())
            }
        }
    }
}