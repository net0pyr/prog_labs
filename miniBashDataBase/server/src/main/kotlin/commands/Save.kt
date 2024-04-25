package com.net0pyr.commands

import com.net0pyr.WorkingWithCommand.WorkWithFile
import com.net0pyr.entity.SpaceMarineInTreeSet
import java.io.FileWriter

/**
 * Класс команды save
 * @author net0pyr
 */
class Save : CommandExample() {
    /**Поле описания команды*/
    override val commandDescription = "save: сохранить коллекцию в файл"

    /** Метод исполнения команды
     * @param commandArgument аргумент команды
     */
    override fun <T> commandExecution(commandArgument: T): String? {
        val writer = FileWriter(System.getenv("FILE_PATH"))
        writer.write("[\n]")
        writer.close()
        val workWithFile = WorkWithFile()
        if (SpaceMarineInTreeSet.spaceMarines.isNotEmpty()) {
            SpaceMarineInTreeSet.spaceMarines.forEach {
                workWithFile.writingToFile(it)
            }
        }
        println("Коллекция успешно сохранена")
        return null
    }
}