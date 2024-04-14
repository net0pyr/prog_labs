package com.net0pyr.commands

import com.net0pyr.entity.SpaceMarineInTreeSet
import com.net0pyr.examples.CommandExample
import java.util.NoSuchElementException

/**
 * Класс команды filter_contains_name name
 * @author net0pyr
 */
class Filter_contains_name : CommandExample() {
    /**Поле описания команды*/
    override val commandDescription =
        "filter_contains_name name: вывести элементы, значения поля name которых содержит заданную подстроку"

    /** Метод исполнения команды
     * @param commandArgument аргумент команды
     */
    override fun <T> commandExecution(commandArgument: T) {
        if (commandArgument == null || commandArgument == "") {
            println("\u001B[31mКоманда filter_contains_name не имеет таких аргуметов.\u001B[0m\nВоспользуйтесь командой help, чтобы получить дополнительную информацию")
        } else {
            var findFlag = false
            SpaceMarineInTreeSet.spaceMarines.forEach {
                if (it.getName()?.contains(commandArgument.toString()) == true) {
                    println(it.toString())
                    findFlag = true
                }
            }
            if (!findFlag) {
                println("Совпадений не найдено")
            }
        }
    }
}