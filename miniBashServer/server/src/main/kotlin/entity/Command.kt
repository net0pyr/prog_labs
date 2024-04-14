package com.net0pyr.entity

import com.net0pyr.army.Chapter
import kotlinx.serialization.Serializable

@Serializable
data class Command(
    private var name: String,
    private var spaceMarine: SpaceMarine? = null,
    private var chapter: Chapter? = null,
    private var commandArgument: String? = null
) {
    constructor(name: String, spaceMarine: SpaceMarine?, commandArgument: String?) : this(name, spaceMarine, null, commandArgument) {
    }
    constructor(name: String, chapter: Chapter?, commandArgument: String?) : this(name, null, chapter, commandArgument) {
    }
    constructor(name: String, commandArgument: String?) : this(name, null, null, commandArgument)
    fun getName():String {
        return name
    }
    fun getSpaceMarine():SpaceMarine? {
        return spaceMarine
    }
    fun getChapter():Chapter? {
        return chapter
    }
    fun getCommandArgument():String? {
        return commandArgument
    }
}