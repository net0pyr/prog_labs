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
 * Класс команды update id
 * @author net0pyr
 */
class Update : CommandExample() {
    /**Поле описания команды*/
    override val commandDescription = "update id: обновить значение элемента колллекции, id которого равен заданному"

    /** Метод исполнения команды
     * @param commandArgument аргумент команды
     */
    override fun <T> commandExecution(commandArgument: T) {
        var id: Long?
        try {
            id = commandArgument?.toString()?.toLong()
            try {
                val changingSpaceMarine = SpaceMarineInTreeSet.spaceMarines.find { it.getId() == id }
                SpaceMarineInTreeSet.spaceMarines.remove(changingSpaceMarine)
                if (Execute_script.executeCommandFlag) {
                    val spaceMarine = Execute_script.spaceMarine
                    if (id != null) {
                        if (spaceMarine != null) {
                            spaceMarine.setId(id)
                        }
                    }
                    if (spaceMarine != null) {
                        SpaceMarineInTreeSet.spaceMarines.add(spaceMarine)
                    }
                    Execute_script.executeCommandFlag = false
                    println("Элемент успешно обновлен")
                }
                try {
                    val readMarine = ReadNewMarine()
                    val spaceMarine = readMarine.readNewMarine()
                    if (id != null) {
                        if (spaceMarine != null) {
                            spaceMarine.setId(id)
                        }
                    }
                    if (spaceMarine != null) {
                        SpaceMarineInTreeSet.spaceMarines.add(spaceMarine)
                    }
                    println("Элемент успешно обновлен")
                } catch (e: InputMismatchException) {
                    println("\u001B[31mОшибка:\u001B[0m Неверный формат ввода")
                }
            } catch (e: NoSuchElementException) {
                println("\u001B[31mОшибка:\u001B[0m Не существует десантника с таким id")
            } catch (e: NullPointerException) {
                println("\u001B[31mОшибка:\u001B[0m Не существует десантника с таким id")
            }
        } catch (e: NumberFormatException) {
            println("\u001B[31mКоманда update не имеет таких аргуметов.\u001B[0m\nВоспользуйтесь командой help, чтобы получить дополнительную информацию")
        }
    }
}