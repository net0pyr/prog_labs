package com.net0pyr.commands

import com.net0pyr.Server
import com.net0pyr.WorkingWithCommand.CommandHandler
import com.net0pyr.army.Chapter
import com.net0pyr.entity.SpaceMarine
import com.net0pyr.entity.SpaceMarineInTreeSet
import java.util.*

/**
 * Класс команды add_if_max
 * @author net0pyr
 */
class Add_if_max : CommandExample() {
    /**Поле описания команды*/
    override val commandDescription =
        "add_if_max - Добавить новый элемент в коллекцию, если его значение превышает знаение наибольшего"

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
            "\u001B[31mКоманда add_if_max не имеет таких аргуметов.\u001B[0m\nВоспользуйтесь командой help, чтобы получить дополнительную информацию"
        } else {
            try {
                var res = ""
                if (spaceMarine != null)
                    if (spaceMarine > SpaceMarineInTreeSet.spaceMarines.last()) {
                        val idSpaceMarine = Server.dataBase.add(spaceMarine,id)
                        spaceMarine.setId(idSpaceMarine)
                        SpaceMarineInTreeSet.spaceMarines.add(spaceMarine)
                        res  = "Элемент успешно добавлен"
                    } else {
                        res = "Элемент не максимальный"
                    }
                return res
            } catch (e: InputMismatchException) {
                "\u001B[31mОшибка:\u001B[0m Неверный формат ввода"
            }
        }
    }
}
