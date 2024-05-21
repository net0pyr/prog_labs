package com.net0pyr

import com.net0pyr.army.Chapter
import com.net0pyr.commands.History
import com.net0pyr.entity.SpaceMarine
import com.net0pyr.entity.SpaceMarineInTreeSet
import com.net0pyr.enums.AstartesCategory
import com.net0pyr.enums.MeleeWeapon
import com.net0pyr.location.Coordinates
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.concurrent.locks.ReentrantReadWriteLock


fun main() {
    Class.forName("org.postgresql.Driver");
    val lock = ReentrantReadWriteLock()
//    val inputStreamReader = InputStreamReader(FileInputStream("Marines.json"))
//    val reader = BufferedReader(inputStreamReader)
//    val jsonString = reader.use { it.readText() }
//    println(jsonString)
    val server = Server()
    Server.dataBase.fill(lock)
    server.start()
}