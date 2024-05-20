package com.net0pyr.commands

import com.net0pyr.WorkingWithCommand.CommandHandler
import com.net0pyr.entity.Command
import com.net0pyr.exceptions.MyIllegalArgumentException
import com.net0pyr.exceptions.RecursionException
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

class ExecuteScript {
    companion object {
        var commands = mutableListOf<Command>()
        var stack = mutableListOf<String>()
        var index = 0
    }

    fun commandExecution(commandArgument: String?, id: Int) {
        try {
            BufferedReader(InputStreamReader(FileInputStream(commandArgument))).use { reader ->
                if (stack.contains(commandArgument.toString())) {
                    throw RecursionException("\u001B[31mОшибка:\u001B[0m Рекурсивный вызов скрипта")
                }
                if (commandArgument != null) {
                    stack.add(commandArgument)
                }
                var line: String?
                val fileArray = mutableListOf<String>()
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
                val argumentsChapter =
                    mutableListOf("Название", "Легион", "Количество десантников", "Название мира")
                var flagEnd = -1
                for (i in 0 until fileArray.size) {
                    if (i < flagEnd)
                        continue
                    val thisLine = fileArray[i]
                    if (thisLine == "") {
                        continue
                    }
                    if (thisLine.contains("add") || thisLine.contains("update") || thisLine.contains("remove_lower")) {
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
                        val command = Command(thisLine, marine.getSpaceMarine(argumentArray), "", id)
                        commands.add(command)
                    } else if (thisLine.contains("chapter")) {
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
                        val command = Command(thisLine, marine.getChapter(argumentArray), "", id)
                        commands.add(command)
                    } else {
                        val commandHandler = CommandHandler()
                        commandHandler.execute(thisLine, id)
                        commands.add(Json.decodeFromString<Command>(commandHandler.execute(thisLine, id)))
                    }
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