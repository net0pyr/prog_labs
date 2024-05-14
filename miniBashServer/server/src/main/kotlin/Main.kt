package com.net0pyr

import com.net0pyr.entity.SpaceMarineInTreeSet
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader


fun main() {
    val spaceMarineInTreeSet = SpaceMarineInTreeSet()
    spaceMarineInTreeSet.fill( )
    val server = Server()
    server.start()
}