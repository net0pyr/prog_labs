package com.net0pyr.commands

import com.net0pyr.army.Chapter
import com.net0pyr.entity.SpaceMarine
import com.net0pyr.entity.SpaceMarineInTreeSet
import com.net0pyr.enums.AstartesCategory
import com.net0pyr.enums.MeleeWeapon
import com.net0pyr.examples.CommandExample
import com.net0pyr.location.Coordinates
import com.net0pyr.toolsForChanging.ReadNewMarine
import java.io.FileInputStream
import java.io.IOException
import java.util.*

/**
 * Класс команды add
 * @author net0pyr
 */
class Add : CommandExample() {
    /**Поле описания команды*/
    override val commandDescription = "add: Добавить новый элемент в коллекцию"

    /** Метод исполнения команды
     * @param commandArgument аргумент команды
     */
    override fun <T> commandExecution(commandArgument: T) {
        if (commandArgument != null && commandArgument != "") {
            println("\u001B[31mКоманда add не имеет таких аргуметов.\u001B[0m\nВоспользуйтесь командой help, чтобы получить дополнительную информацию")
        } else if (Execute_script.executeCommandFlag) {
            SpaceMarineInTreeSet.spaceMarines.add(Execute_script.spaceMarine)
            Execute_script.executeCommandFlag = false
            println("Элемент успешно добавлен")
        } else {
            try {
                val readMarine = ReadNewMarine()
                val spaceMarine = readMarine.readNewMarine()
                if (spaceMarine != null) {
                    SpaceMarineInTreeSet.spaceMarines.add(spaceMarine)
                    println("Элемент успешно добавлен")
                }
            } catch (e: InputMismatchException) {
                println("\u001B[31mОшибка:\u001B[0m Неверный формат ввода")
            }
        }
    }
}