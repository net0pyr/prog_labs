package com.net0pyr

import java.nio.channels.ClosedChannelException
import java.util.*

fun main() {
    Scanner(System.`in`).use { scanner ->
        val client = Client()
        var connectionFlag = false
        while (!connectionFlag) {
            try {
                client.start(scanner)
                connectionFlag = true
            } catch (_: ClosedChannelException) {
            }
        }
        scanner.close()
    }
}
