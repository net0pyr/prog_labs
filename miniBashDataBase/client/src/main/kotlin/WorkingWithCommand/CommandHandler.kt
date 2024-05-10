package com.net0pyr.WorkingWithCommand

import com.net0pyr.commands.ExecuteScript
import com.net0pyr.entity.Command
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CommandHandler {
    companion object {
        var executeScriptFlag = false
        val commandWithSpaceMarine = mutableListOf<String>()
        val commandWithChapter = mutableListOf<String>()
    }

    fun execute(inputString: String, id: Int): String {
        var commandArgument = ""
        val input = inputString.trim(' ').split(' ')
        val inputCommand = input[0]
        for(i in 1..<input.size) {
            commandArgument+=input[i]
        }
        lateinit var outputString: String
        if (commandWithSpaceMarine.contains(inputCommand)) {
            val readNewObject = ReadNewObject()
            val spaceMarine = readNewObject.readNewMarine()
            val command = Command(inputCommand, spaceMarine, commandArgument, id)
            outputString = Json.encodeToString(command)
        } else if (commandWithChapter.contains(inputCommand)) {
            val readNewObject = ReadNewObject()
            val chapter = readNewObject.readNewChapter()
            val command = Command(inputCommand, chapter, commandArgument, id)
            outputString = Json.encodeToString(command)
        } else if (inputCommand == "execute_script") {
            executeScriptFlag = true
            val executeScript = ExecuteScript()
            executeScript.commandExecution(commandArgument, id)
            outputString = "executeScript"
        } else {
            val command = Command(inputCommand, commandArgument, id)
            outputString = Json.encodeToString(command)
        }
        return outputString
    }
}