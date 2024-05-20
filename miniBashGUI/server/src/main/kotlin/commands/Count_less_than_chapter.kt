package com.net0pyr.commands

import com.net0pyr.WorkingWithCommand.CommandHandler
import com.net0pyr.army.Chapter
import com.net0pyr.entity.SpaceMarine
import com.net0pyr.entity.SpaceMarineInTreeSet
import java.util.*

/**
 * Класс команды count_less_than_chapter
 * @author net0pyr
 */
class Count_less_than_chapter : CommandExample() {
    /**Поле описания команды*/
    override val commandDescription =
        "count_less_than_chapter - вывести количество элементов, значение поля chapter которых меньше заданного"

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
            "\u001B[31mКоманда count_less_than_chapter не имеет таких аргуметов.\u001B[0m\nВоспользуйтесь командой help, чтобы получить дополнительную информацию"
        } else {
            try {
                var count = 0
                SpaceMarineInTreeSet.spaceMarines.forEach {
                    if (chapter != null) {
                        if (chapter > it.getChapter()!!) {
                            count++
                        }
                    }
                }
                "$count"

            } catch (e: InputMismatchException) {
                "\u001B[31mОшибка:\u001B[0m Неверный формат ввода"
            }
        }
    }
}