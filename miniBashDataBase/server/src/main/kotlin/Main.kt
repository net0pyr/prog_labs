package com.net0pyr

import com.net0pyr.entity.SpaceMarineInTreeSet
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.concurrent.locks.ReentrantReadWriteLock


fun main() {
    Class.forName("org.postgresql.Driver");
    val spaceMarineInTreeSet = SpaceMarineInTreeSet()
    val lock = ReentrantReadWriteLock()
    spaceMarineInTreeSet.fill(lock)
//    val inputStreamReader = InputStreamReader(FileInputStream("Marines.json"))
//    val reader = BufferedReader(inputStreamReader)
//    val jsonString = reader.use { it.readText() }
//    println(jsonString)
    val server = Server()
    server.start()
}