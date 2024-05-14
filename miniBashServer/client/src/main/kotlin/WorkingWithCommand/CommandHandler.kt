package com.net0pyr.WorkingWithCommand

import com.net0pyr.commands.ExecuteScript
import com.net0pyr.entity.Command
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CommandHandler {
    companion object {
        var executeScriptFlag = false
        var listWithMarine = mutableListOf<String>()
        var listWithChapter = mutableListOf<String>()
    }

    fun execute(inputString: String): String {
        //val inputCommand = StringBuilder("")
        val listCommand = inputString.trim().split(" ").toList()
        val inputCommand = listCommand[0]
        var commandArgument = ""
        if(listCommand.size>1) {
            for(i in 1..<listCommand.size) {
                commandArgument+=listCommand[i]
            }
        }
        //val commandArgument = StringBuilder("")
//        var argumentFlag = false
//        var commandFlag = false
//        for (letter in inputString) {
//            if (letter != ' ' && !commandFlag) {
//                commandFlag = true
//            }
//            if (letter == ' ' && commandFlag) {
//                argumentFlag = true
//                continue
//            }
//            if (argumentFlag) {
//                commandArgument.append(letter)
//            }
//            if (commandFlag && !argumentFlag) {
//                inputCommand.append(letter)
//            }
//        }
        lateinit var outputString: String
        val commandWithSpaceMarine = listWithMarine.toList()
        val commandWithChapter = listWithChapter.toList()
        if (commandWithSpaceMarine.contains(inputCommand)) {
            val readNewObject = ReadNewObject()
            val spaceMarine = readNewObject.readNewMarine()
            val command = Command(inputCommand, spaceMarine, commandArgument)
            outputString = Json.encodeToString(command)
        } else if (commandWithChapter.contains(inputCommand)) {
            val readNewObject = ReadNewObject()
            val chapter = readNewObject.readNewChapter()
            val command = Command(inputCommand, chapter, commandArgument)
            outputString = Json.encodeToString(command)
        } else if (inputCommand == "execute_script") {
            executeScriptFlag = true
            val executeScript = ExecuteScript()
            executeScript.commandExecution(commandArgument)
            outputString = "executeScript"
        } else {
            val command = Command(inputCommand, commandArgument)
            outputString = Json.encodeToString(command)
        }
        return outputString
    }
}