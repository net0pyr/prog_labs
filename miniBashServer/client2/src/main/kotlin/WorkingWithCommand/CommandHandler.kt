package com.net0pyr.WorkingWithCommand

import com.net0pyr.entity.Command
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CommandHandler {
    fun execute(inputString: String): String {
        val inputCommand = StringBuilder("")
        val commandArgument = StringBuilder("")
        var argumentFlag = false
        var commandFlag = false
        for (letter in inputString) {
            if (letter != ' ' && !commandFlag) {
                commandFlag = true
            }
            if (letter == ' ' && commandFlag) {
                argumentFlag = true
                continue
            }
            if (argumentFlag) {
                commandArgument.append(letter)
            }
            if (commandFlag && !argumentFlag) {
                inputCommand.append(letter)
            }
        }
        lateinit var outputString: String
        val commandWithSpaceMarine = listOf("add", "add_if_max", "remove_lower", "update")
        val commandWithChapter = listOf("count_less_than_chapter")
        if (commandWithSpaceMarine.contains(inputCommand.toString())) {
            val readNewObject = ReadNewObject()
            val spaceMarine = readNewObject.readNewMarine()
            val command = Command(inputString, spaceMarine,commandArgument.toString())
            outputString = Json.encodeToString(command)
        } else if (commandWithChapter.contains(inputCommand.toString())) {
            val readNewObject = ReadNewObject()
            val chapter = readNewObject.readNewChapter()
            val command = Command(inputString, chapter, commandArgument.toString())
            outputString = Json.encodeToString(command)
        } else if (inputCommand.toString()=="execute_script") {

        } else {
            val command = Command(inputString, commandArgument.toString())
            outputString = Json.encodeToString(command)
        }
        return outputString
    }
}