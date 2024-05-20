package com.net0pyr.entity

import com.net0pyr.army.Chapter
import kotlinx.serialization.Serializable

@Serializable
data class Command(
    var name: String,
    var spaceMarine: SpaceMarine? = null,
    var chapter: Chapter? = null,
    var commandArgument: String? = null,
    var id: Int = -1
) {
    constructor(name: String, spaceMarine: SpaceMarine?, commandArgument: String?, id: Int) : this(name, spaceMarine, null, commandArgument, id) {
    }
    constructor(name: String, chapter: Chapter?, commandArgument: String?, id: Int) : this(name, null, chapter, commandArgument, id) {
    }
    constructor(name: String, commandArgument: String?, id: Int) : this(name, null, null, commandArgument, id)
}