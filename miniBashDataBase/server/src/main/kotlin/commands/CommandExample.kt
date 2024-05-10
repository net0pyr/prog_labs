package com.net0pyr.commands

import com.net0pyr.army.Chapter
import com.net0pyr.entity.SpaceMarine
import java.util.Objects

/**
 * Абстрактный класс описывает шаблон для команд
 * @author net0pyr
 */
abstract class CommandExample {
    abstract val commandDescription: String
    abstract fun <T> commandExecution(commandArgument: T, spaceMarine: SpaceMarine? = null, chapter: Chapter? = null, id: Int) : String?

    @JvmName("getCommandDescriptionExample")
    fun getCommandDescription(): String {
        return commandDescription
    }
}