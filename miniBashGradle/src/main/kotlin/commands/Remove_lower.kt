package com.net0pyr.commands

import com.net0pyr.army.Chapter
import com.net0pyr.entity.SpaceMarine
import com.net0pyr.entity.SpaceMarineInTreeSet
import com.net0pyr.enums.AstartesCategory
import com.net0pyr.enums.MeleeWeapon
import com.net0pyr.examples.CommandExample
import com.net0pyr.location.Coordinates
import com.net0pyr.toolsForChanging.ReadNewMarine
import java.util.*


/**
 * Класс команды remove_lower
 * @author net0pyr
 */
class Remove_lower : CommandExample() {
    /**Поле описания команды*/
    override val commandDescription = "remove_lower: удалить из коллекции все элементы, меньшие, чем заданный"

    /** Метод исполнения команды
     * @param commandArgument аргумент команды
     */
    override fun <T> commandExecution(commandArgument: T) {
        if (commandArgument != null && commandArgument != "") {
            println("\u001B[31mКоманда remove_lower не имеет таких аргуметов.\u001B[0m\nВоспользуйтесь командой help, чтобы получить дополнительную информацию")
        } else {
            try {
                val readMarine = ReadNewMarine()
                val spaceMarine = readMarine.readNewMarine()
                if (spaceMarine != null)
                    SpaceMarineInTreeSet.spaceMarines.forEach {
                        if (spaceMarine > it) {
                            SpaceMarineInTreeSet.spaceMarines.remove(it)
                        }
                    }
            } catch (e: InputMismatchException) {
                println("\u001B[31mОшибка:\u001B[0m Неверный формат ввода")
            }
        }
    }
}
