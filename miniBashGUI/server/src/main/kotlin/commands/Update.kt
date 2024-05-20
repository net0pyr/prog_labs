package com.net0pyr.commands

import com.net0pyr.Server
import com.net0pyr.WorkingWithCommand.CommandHandler
import com.net0pyr.army.Chapter
import com.net0pyr.entity.SpaceMarine
import com.net0pyr.entity.SpaceMarineInTreeSet
import java.util.*

/**
 * Класс команды update id
 * @author net0pyr
 */
class Update : CommandExample() {
    /**Поле описания команды*/
    override val commandDescription = "update id - обновить значение элемента колллекции, id которого равен заданному"

    /** Метод исполнения команды
     * @param commandArgument аргумент команды
     */
    override fun <T> commandExecution(
        commandArgument: T,
        spaceMarine: SpaceMarine?,
        chapter: Chapter?,
        id: Int
    ): String? {
        val idSpaceMarine: Long?
        return try {
            idSpaceMarine = commandArgument?.toString()?.toLong()
            try {
                var outputString = ""
                outputString = if (idSpaceMarine?.let { Server.dataBase.getCreatorSpaceMarine(it) } != id) {
                    "Вы не можете менять чужих десантников"
                } else {
                    try {
                        if (spaceMarine != null) {
                            Server.dataBase.updateSpaceMarine(spaceMarine,id)
                        }
                        var changingSpaceMarine = SpaceMarineInTreeSet.spaceMarines.find { it.id == idSpaceMarine }
                        SpaceMarineInTreeSet.spaceMarines.remove(changingSpaceMarine)
                        if (spaceMarine != null) {
                            changingSpaceMarine = spaceMarine
                            changingSpaceMarine.id = (idSpaceMarine)
                            SpaceMarineInTreeSet.spaceMarines.add(changingSpaceMarine)
                        }
                        "Десантник успешно изменен"
                    } catch (e: InputMismatchException) {
                        "\u001B[31mОшибка:\u001B[0m Неверный формат ввода"
                    }
                }
                outputString
            } catch (e: NoSuchElementException) {
                "\u001B[31mОшибка:\u001B[0m Не существует десантника с таким id"
            } catch (e: NullPointerException) {
                "\u001B[31mОшибка:\u001B[0m Не существует десантника с таким id"
            }
        } catch (e: NumberFormatException) {
            "\u001B[31mКоманда update не имеет таких аргуметов.\u001B[0m\nВоспользуйтесь командой help, чтобы получить дополнительную информацию"
        }
    }
}