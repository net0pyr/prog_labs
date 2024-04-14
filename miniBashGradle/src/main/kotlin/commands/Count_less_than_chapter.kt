package com.net0pyr.commands

import com.net0pyr.army.Chapter
import com.net0pyr.entity.SpaceMarine
import com.net0pyr.entity.SpaceMarineInTreeSet
import com.net0pyr.enums.AstartesCategory
import com.net0pyr.enums.MeleeWeapon
import com.net0pyr.examples.CommandExample
import com.net0pyr.location.Coordinates
import java.util.*

/**
 * Класс команды count_less_than_chapter
 * @author net0pyr
 */
class Count_less_than_chapter : CommandExample() {
    /**Поле описания команды*/
    override val commandDescription =
        "count_less_than_chapter: вывести количество элементов, значение поля chapter которых меньше заданного"

    /** Метод исполнения команды
     * @param commandArgument аргумент команды
     */
    override fun <T> commandExecution(commandArgument: T) {
        if (commandArgument != null && commandArgument != "") {
            println("\u001B[31mКоманда count_less_than_chapter не имеет таких аргуметов.\u001B[0m\nВоспользуйтесь командой help, чтобы получить дополнительную информацию")
        } else if (Execute_script.executeCommandFlag) {
            var count = 0
            SpaceMarineInTreeSet.spaceMarines.forEach {
                if (Execute_script.chapter > it.getChapter()!!) {
                    count++
                }
            }
            println(count)
            Execute_script.executeCommandFlag = false
        } else {
            try {
                var scanner = Scanner(System.`in`)
                print("Название военной части: ")
                var chapterName = scanner.nextLine()
                print("Легион: ")
                var LegionName = scanner.nextLine()
                print("Количество десантников: ")
                var marinesCount = scanner.nextInt()
                scanner.nextLine()
                print("Название мира: ")
                var world = scanner.nextLine()

                val chapter = Chapter(chapterName, LegionName, marinesCount, world)
                var count = 0
                SpaceMarineInTreeSet.spaceMarines.forEach {
                    if (chapter > it.getChapter()!!) {
                        count++
                    }
                }
                println(count)

            } catch (e: InputMismatchException) {
                println("\u001B[31mОшибка:\u001B[0m Неверный формат ввода")
            }
        }
    }
}