package com.net0pyr.commands

import com.net0pyr.entity.SpaceMarine
import com.net0pyr.entity.SpaceMarineInTreeSet
import com.net0pyr.examples.CommandExample
import com.net0pyr.toolsForChanging.WorkWithFile
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileWriter
import java.io.InputStreamReader

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
    override fun <T> commandExecution(commandArgument: T) {
        if (commandArgument != null && commandArgument != "") {
            println("\u001B[31mКоманда save не имеет таких аргуметов.\u001B[0m\nВоспользуйтесь командой help, чтобы получить дополнительную информацию")
        } else {
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
        }
    }
}