package com.net0pyr.WorkingWithCommand

import com.net0pyr.entity.SpaceMarine
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileWriter
import java.io.InputStreamReader
import java.util.concurrent.locks.ReentrantLock
import java.util.concurrent.locks.ReentrantReadWriteLock

/**
 * Класс описывает работу с фалом
 * @author net0pyr
 */
class WorkWithFile() {

    companion object {
        val lock = ReentrantReadWriteLock()
    }

    /** Метод записи нового десантника в файл
     * @param spaceMarine новый десантник
     */
    fun writingToFile(spaceMarine: SpaceMarine) {
        lock.writeLock().lock()
        try {
            val inputStreamReader = InputStreamReader(FileInputStream(System.getenv("FILE_PATH")))
            val reader = BufferedReader(inputStreamReader)
            var fileArray = mutableListOf<String>()
            var line: String?
            var existFlag = false
            while (reader.readLine().also { line = it } != null) {
                fileArray.add(line!!)
                if (line != "]" && line != "" && line != "[") existFlag = true
            }
            for (i in fileArray.size - 1 downTo 0) {
                if (fileArray[i] == "]")
                    break;
                fileArray.removeLast()
            }
            fileArray.removeLast()
            if (existFlag) {
                fileArray[fileArray.size - 1] += ","
            }
            val jsonString = Json.encodeToString(spaceMarine)
            fileArray.add(jsonString)
            fileArray.add("]")
            val writer = FileWriter(System.getenv("FILE_PATH"))
            fileArray.forEach {
                writer.write("$it\n")
            }
            writer.close()
        } finally {
            lock.writeLock().unlock()
        }
    }

    /** Метод удаления десантника по его id
     * @param id новый десантник
     */
    fun removeFromFile(id: Long) {
        val inputStreamReader = InputStreamReader(FileInputStream(System.getenv("FILE_PATH")))
        val reader = BufferedReader(inputStreamReader)
        var fileArray = mutableListOf<String>()
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            fileArray.add(line!!)
        }
        var numberStartLine = 0
        for (newLine in fileArray) {
            if (Regex("\"id\":$id").containsMatchIn(newLine)) {
                break
            }
            numberStartLine++
        }
        if (numberStartLine + 1 < fileArray.size) {
            if (fileArray[numberStartLine + 1] == "]") {
                fileArray[numberStartLine - 1] = fileArray[numberStartLine - 1].dropLast(1)
            }
        }
        val writer = FileWriter(System.getenv("FILE_PATH"))
        for ((lineNumber, newLine) in fileArray.withIndex()) {
            if (lineNumber != numberStartLine) {
                writer.write("$newLine\n")
            }
        }
        writer.close()
    }

    /** Метод полечение десантника по его id
     * @param id новый десантник
     */
    fun getFromFile(id: Long): SpaceMarine? {
        val inputStreamReader = InputStreamReader(FileInputStream(System.getenv("FILE_PATH")))
        val reader = BufferedReader(inputStreamReader)
        val jsonString = reader.use { it.readText() }
        val spaceMarines = Json.decodeFromString<List<SpaceMarine>>(jsonString)
        spaceMarines.forEach {
            if (it.id == id) {
                return (it)
            }
        }
        return (null)
    }
}