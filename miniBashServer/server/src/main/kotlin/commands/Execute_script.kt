package com.net0pyr.commands

class Execute_script : CommandExample() {
    /**Поле описания команды*/
    override val commandDescription = "execution_script file_name: считать и исполнить скрипт из указанного файла"

    /** Метод исполнения команды
     * @param commandArgument аргумент команды
     */
    override fun <T> commandExecution(commandArgument: T): String? {
        return null
    }
}