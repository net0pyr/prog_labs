package com.net0pyr.commands

import com.net0pyr.Server
import com.net0pyr.army.Chapter
import com.net0pyr.entity.SpaceMarine
import com.net0pyr.entity.SpaceMarineInTreeSet

/**
 * Класс команды show
 * @author net0pyr
 */
class Show : CommandExample() {
    /**Поле описания команды*/
    override val commandDescription = "show - Выводит все элементы коллекции в строковом представлении"

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
            "\u001B[31mКоманда show не имеет таких аргуметов.\u001B[0m\nВоспользуйтесь командой help, чтобы получить дополнительную информацию"
        } else {
            val outputString = StringBuilder()
            SpaceMarineInTreeSet.spaceMarines.asSequence().sortedBy { it.getName() }.forEach {
                outputString.append(it.toString())
                if (spaceMarine != null) {
                    if(Server.dataBase.getCreatorSpaceMarine(spaceMarine.getId())==id){
                        outputString.append(" - Ваш десантник")
                    }
                }
                outputString.append("\n")
            }
            if (SpaceMarineInTreeSet.spaceMarines.isEmpty()) {
                "Коллекция пустая"
            } else {
                outputString.toString()
            }
        }
    }
}