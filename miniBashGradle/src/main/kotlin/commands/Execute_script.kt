package com.net0pyr.commands

import com.net0pyr.army.Chapter
import com.net0pyr.entity.SpaceMarine
import com.net0pyr.examples.CommandExample
import com.net0pyr.exceptions.MyIllegalArgumentException
import com.net0pyr.exceptions.RecursionException
import com.net0pyr.toolsForChanging.CommandHandler
import com.net0pyr.toolsForChanging.ObjectFromList
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader


/**
 * Класс команды execution_script file_name
 * @author net0pyr
 */
class Execute_script : CommandExample() {
    companion object {
        lateinit var spaceMarine: SpaceMarine
        lateinit var chapter: Chapter
        var executeCommandFlag = false
        var stack = mutableListOf<String>()
    }

    /**Поле описания команды*/
    override val commandDescription = "execution_script file_name: считать и исполнить скрипт из указанного файла"

    /** Метод исполнения команды
     * @param commandArgument аргумент команды
     */
    override fun <T> commandExecution(commandArgument: T) {
        try {
            BufferedReader(InputStreamReader(FileInputStream(commandArgument.toString()))).use { reader ->
                if (stack.contains(commandArgument.toString())) {
                    throw RecursionException("\u001B[31mОшибка:\u001B[0m Рекурсивный вызов скрипта")
                }
                if (commandArgument != null) {
                    stack.add(commandArgument.toString())
                }
                var line: String?
                var fileArray = mutableListOf<String>()
                while (reader.readLine().also { line = it } != null) {
                    line?.let { fileArray.add(it) }
                }
                val arguments = mutableListOf(
                    "Имя",
                    "Здоровье",
                    "Рост",
                    "Координаты местонахождения",
                    "x",
                    "y",
                    "Категория война",
                    "Вид оружия",
                    "Военная часть",
                    "Название",
                    "Легион",
                    "Количество десантников",
                    "Название мира"
                )
                var flagEnd = -1
                for (i in 0 until fileArray.size) {
                    if (i < flagEnd)
                        continue
                    val thisLine = fileArray[i]
                    if (thisLine == "") {
                        continue
                    }
                    if (thisLine.contains("add") || thisLine.contains("update")) {
                        var argumentArray = mutableListOf<String>()
                        var indexArgument = 0
                        for (j in i + 1 until fileArray.size) {
                            if (indexArgument == arguments.size) {
                                break
                            }
                            val newline = fileArray[j]
                            if (newline.contains(":") == false) {
                                throw MyIllegalArgumentException("$j")
                            }
                            var field = StringBuilder()
                            var startFlag = false
                            var finishFlag = false
                            newline.forEach {
                                if (!startFlag && it != ' ') {
                                    startFlag = true
                                }
                                if (startFlag && it == ':') {
                                    finishFlag = true
                                }
                                if (!finishFlag && startFlag) {
                                    field.append(it)
                                }
                            }
                            if (arguments[indexArgument] == field.toString()) {
                                argumentArray.add(newline)
                            } else {
                                throw MyIllegalArgumentException("$j")
                            }
                            indexArgument++
                        }
                        flagEnd = i + indexArgument + 1
                        val marine = ObjectFromList()
                        spaceMarine = marine.getSpaceMarine(argumentArray)
                        executeCommandFlag = true
                    }
                    val argumentsChapter =
                        mutableListOf("Название", "Легион", "Количество десантников", "Название мира")
                    if (thisLine.contains("chapter")) {
                        var argumentArray = mutableListOf<String>()
                        var indexArgument = 0
                        for (j in i + 1 until fileArray.size) {
                            if (indexArgument == argumentsChapter.size) {
                                break
                            }
                            val newline = fileArray[j]
                            if (newline.contains(":") == false) {
                                throw MyIllegalArgumentException("$j")
                            }
                            var field = StringBuilder()
                            var startFlag = false
                            var finishFlag = false
                            newline.forEach {
                                if (!startFlag && it != ' ') {
                                    startFlag = true
                                }
                                if (startFlag && it == ':') {
                                    finishFlag = true
                                }
                                if (!finishFlag && startFlag) {
                                    field.append(it)
                                }
                            }
                            if (argumentsChapter[indexArgument] == field.toString()) {
                                argumentArray.add(newline)
                            } else {
                                throw MyIllegalArgumentException("$j")
                            }
                            indexArgument++
                        }
                        flagEnd = i + indexArgument + 1
                        val marine = ObjectFromList()
                        chapter = marine.getChapter(argumentArray)
                        executeCommandFlag = true
                    }
                    val commandHandler = CommandHandler()
                    commandHandler.handler(thisLine)
                }
                stack.remove(commandArgument.toString())
            }
        } catch (e: NullPointerException) {
            println("\u001B[31mОшибка:\u001B[0m Нет скрипта с таким названием")
        } catch (e: RecursionException) {
            println("\u001B[31mОшибка:\u001B[0m Рекурсивный вызов скрипта")
        } catch (e: MyIllegalArgumentException) {
            println("\u001B[31mОшибка ввода в строчке номер $e\u001B[0m")
        }
    }
}