package com.net0pyr.commands

import com.net0pyr.army.Chapter
import com.net0pyr.entity.SpaceMarine

class Execute_script : CommandExample() {
    /**Поле описания команды*/
    override val commandDescription = "execution_script file_name - считать и исполнить скрипт из указанного файла"

    /** Метод исполнения команды
     * @param commandArgument аргумент команды
     */
    override fun <T> commandExecution(
        commandArgument: T,
        spaceMarine: SpaceMarine?,
        chapter: Chapter?,
        id: Int
    ): String? {
        return null
    }
}