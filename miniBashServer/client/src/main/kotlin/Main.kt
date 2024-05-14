package com.net0pyr

import com.net0pyr.WorkingWithCommand.CommandHandler
import java.util.*

fun main() {
    CommandHandler.listWithMarine.addAll(listOf("add", "add_if_max", "remove_lower", "update"))
    CommandHandler.listWithChapter.addAll(listOf("count_less_than_chapter"))
    Scanner(System.`in`).use { scanner ->
        val client = Client()
        client.start(scanner)
        scanner.close()
    }
}
