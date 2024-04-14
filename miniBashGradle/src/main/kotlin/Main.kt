package com.net0pyr

import com.net0pyr.army.Chapter
import com.net0pyr.commands.History
import com.net0pyr.entity.SpaceMarine
import com.net0pyr.entity.SpaceMarineInTreeSet
import com.net0pyr.enums.AstartesCategory
import com.net0pyr.enums.MeleeWeapon
import com.net0pyr.location.Coordinates
import com.net0pyr.toolsForChanging.CommandHandler
import com.net0pyr.toolsForChanging.WorkWithFile
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

fun main() {
    var spaceMarineInTreeSet = SpaceMarineInTreeSet()
    spaceMarineInTreeSet.fill()

    val scannerMain = Scanner(System.`in`)
    try {
        val commandHandler = CommandHandler()
        while (true) {
            if (scannerMain.hasNextLine()) {
                val inputString = scannerMain.nextLine()
                if (inputString == "")
                    continue
                if (inputString.equals("exit", ignoreCase = true)) {
                    break
                }
                commandHandler.handler(inputString)
            }
        }
    } finally {
        scannerMain.close()
    }
}