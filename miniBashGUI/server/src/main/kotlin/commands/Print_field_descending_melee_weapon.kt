package com.net0pyr.commands

import com.net0pyr.army.Chapter
import com.net0pyr.entity.SpaceMarine
import com.net0pyr.entity.SpaceMarineInTreeSet


/**
 * Класс команды print_field_descending_melee_weapon
 * @author net0pyr
 */
class Print_field_descending_melee_weapon : CommandExample() {
    /**Поле описания команды*/
    override val commandDescription =
        "print_field_descending_melee_weapon - вывести значения поля meleeWeapon всех элементов в порядке убывания"

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
            "\u001B[31mКоманда print_field_descending_melee_weapon не имеет таких аргуметов.\u001B[0m\nВоспользуйтесь командой help, чтобы получить дополнительную информацию"
        } else {
            val descendingIterator = SpaceMarineInTreeSet.spaceMarines.descendingIterator()
            val outputString = StringBuilder()
            while (descendingIterator.hasNext()) {
                outputString.append(descendingIterator.next().meleeWeapon.toString()+"\n")
            }
            outputString.toString()
        }
    }
}