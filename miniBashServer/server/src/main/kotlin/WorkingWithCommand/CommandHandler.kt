package com.net0pyr.WorkingWithCommand

import com.net0pyr.army.Chapter
import com.net0pyr.commands.*
import com.net0pyr.entity.Command
import com.net0pyr.entity.SpaceMarine
import com.net0pyr.entity.SpaceMarineInTreeSet
import kotlinx.serialization.json.Json
import java.io.FileWriter

class CommandHandler {
    companion object {
        var spaceMarine: SpaceMarine? = null
        var chapter: Chapter? = null
        var commands: HashMap<String, CommandExample> = HashMap()
    }

    init {
        commands["add"] = Add()
        commands["add_if_max"] = Add_if_max()
        commands["clear"] = Clear()
        commands["count_lass_than_chapter"] = Count_less_than_chapter()
        commands["filter_contains_name"] = Filter_contains_name()
        commands["execute_script"] = Execute_script()
        commands["help"] = Help()
        commands["history"] = History()
        commands["info"] = Info()
        commands["print_field_descending_melee_weapon"] = Print_field_descending_melee_weapon()
        commands["remove_by_id"] = Remove_by_id()
        commands["remove_lower"] = Remove_lower()
        commands["show"] = Show()
        commands["update"] = Update()
    }

    fun execute(inputString: String): String {
        lateinit var outputString: String
        val command = Json.decodeFromString<Command>(inputString)
        val commandInMap = commands.get(command.getName())
        spaceMarine = command.getSpaceMarine()
        chapter = command.getChapter()
        if (commandInMap != null) {
            outputString = commandInMap.commandExecution(command.getCommandArgument()).toString()
        } else {
            outputString = "Нет такой команды, воспользуйтесь командой help, чтобы посмотреть доступные команды."
        }
        return outputString
    }
}