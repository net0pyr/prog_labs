package com.net0pyr.commands

import com.net0pyr.Server
import com.net0pyr.army.Chapter
import com.net0pyr.entity.SpaceMarine
import com.net0pyr.entity.SpaceMarineInTreeSet
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

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
            var outputString = ""
                SpaceMarineInTreeSet.spaceMarines.asSequence().sortedBy { it.name }.forEach {
                    outputString += Json.encodeToString(it)
//                if (spaceMarine != null) {
//                    if(Server.dataBase.getCreatorSpaceMarine(spaceMarine.id)==id){
//                        outputString.append(" - Ваш десантник")
//                    }
//                }
                    outputString += ("\n")
                }
            if(outputString!="") {
                outputString
            } else {
                "null"
            }
        }
    }
}