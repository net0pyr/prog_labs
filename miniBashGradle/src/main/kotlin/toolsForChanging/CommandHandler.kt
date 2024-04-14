package com.net0pyr.toolsForChanging

import com.net0pyr.commands.*
import com.net0pyr.examples.CommandExample

/**
 * Класс описывает исполнение команды по вводимой строке
 * @author net0pyr
 */
class CommandHandler() {
    companion object {
        var commands: HashMap<String, CommandExample> = HashMap()
    }

    init {
        commands["add"] = Add()
        commands["add_if_max"] = Add_if_max()
        commands["clear"] = Clear()
        commands["count_lass_than_chapter"] = Count_less_than_chapter()
        commands["execute_script"] = Execute_script()
        commands["filter_contains_name"] = Filter_contains_name()
        commands["help"] = Help()
        commands["history"] = History()
        commands["info"] = Info()
        commands["print_field_descending_melee_weapon"] = Print_field_descending_melee_weapon()
        commands["remove_by_id"] = Remove_by_id()
        commands["remove_lower"] = Remove_lower()
        commands["save"] = Save()
        commands["show"] = Show()
        commands["update"] = Update()
    }

    /** Метод выбора команды и ее исполнения
     * @param inputString строка прочитанная из консоли
     */

    fun handler(inputString: String) {
        var inputCommand = StringBuilder("")
        var commandArgument = StringBuilder("")
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
        History.history.add(inputCommand.toString())
        if (History.history.size > 10) {
            History.history.removeAt(0)
        }

        var command = commands.get(inputCommand.toString())
        if (command != null) {
            command.commandExecution(commandArgument.toString())
        } else {
            println("Нет такой команды, воспользуйтесь командой help, чтобы посмотреть доступные команды.")
        }
    }
}