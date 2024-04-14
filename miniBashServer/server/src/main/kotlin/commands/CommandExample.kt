package com.net0pyr.commands

import java.util.Objects

/**
 * Абстрактный класс описывает шаблон для команд
 * @author net0pyr
 */
abstract class CommandExample {
    abstract val commandDescription: String
    abstract fun <T> commandExecution(commandArgument: T) : String?

    @JvmName("getCommandDescriptionExample")
    fun getCommandDescription(): String {
        return commandDescription
    }
}