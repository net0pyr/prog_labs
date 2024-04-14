package com.net0pyr.commands

import com.net0pyr.entity.SpaceMarineInTreeSet
import com.net0pyr.examples.CommandExample
import java.io.File


/**
 * Класс команды print_field_descending_melee_weapon
 * @author net0pyr
 */
class Print_field_descending_melee_weapon : CommandExample() {
    /**Поле описания команды*/
    override val commandDescription =
        "print_field_descending_melee_weapon: вывести значения поля meleeWeapon всех элементов в порядке убывания"

    /** Метод исполнения команды
     * @param commandArgument аргумент команды
     */
    override fun <T> commandExecution(commandArgument: T) {
        if (commandArgument != null && commandArgument != "") {
            println("\u001B[31mКоманда print_field_descending_melee_weapon не имеет таких аргуметов.\u001B[0m\nВоспользуйтесь командой help, чтобы получить дополнительную информацию")
        } else {
            val descendingIterator = SpaceMarineInTreeSet.spaceMarines.descendingIterator()
            while (descendingIterator.hasNext()) {
                println(descendingIterator.next().getMeleeWeapon())
            }
        }
    }
}