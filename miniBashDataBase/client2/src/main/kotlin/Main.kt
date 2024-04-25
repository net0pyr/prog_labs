package com.net0pyr

import java.util.*

fun main() {
    Scanner(System.`in`).use { scanner ->
        val client = Client()
        client.start(scanner)
        scanner.close()
    }
}
