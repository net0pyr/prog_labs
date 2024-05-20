package com.net0pyr.entity

import com.net0pyr.army.Chapter
import kotlinx.serialization.Serializable

@Serializable
data class Command(
    private var name: String,
    private var spaceMarine: SpaceMarine? = null,
    private var chapter: Chapter? = null,
    private var commandArgument: String? = null,
    private var id: Int = -1
) {
    constructor(name: String, spaceMarine: SpaceMarine?, commandArgument: String?, id: Int) : this(name, spaceMarine, null, commandArgument, id) {
    }
    constructor(name: String, chapter: Chapter?, commandArgument: String?, id: Int) : this(name, null, chapter, commandArgument, id) {
    }
    constructor(name: String, commandArgument: String?, id: Int) : this(name, null, null, commandArgument, id)
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
    fun getId():Int {
        return id
    }
}