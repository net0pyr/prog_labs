package com.net0pyr.entity

import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.time.LocalDateTime
import java.util.*

/**
 * Класс описывающий коллекцию в которой хранятся десантники
 * @author net0pyr
 */
class SpaceMarineInTreeSet {
    companion object {
        /** коллекция с десантниками*/
        var spaceMarines: TreeSet<SpaceMarine> = TreeSet()

        /** время создания коллекции*/
        lateinit var creationTime: LocalDateTime
    }

    /** Метод заполнения коллекции */
    fun fill() {
        creationTime = LocalDateTime.now()
        val inputStreamReader = InputStreamReader(FileInputStream(System.getenv("FILE_PATH")))
        val reader = BufferedReader(inputStreamReader)
        val jsonString = reader.use { it.readText() }
        val spaceMarinesInFile = Json.decodeFromString<List<SpaceMarine>>(jsonString)
        spaceMarinesInFile.forEach {
            spaceMarines.add(it)
        }
    }
}