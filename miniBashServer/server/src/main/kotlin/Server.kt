package com.net0pyr

import com.net0pyr.WorkingWithCommand.CommandHandler
import com.net0pyr.commands.Save
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel
import java.util.*

class Server {
    private val port = 12345
    private val selector = Selector.open()

    fun start() {
        val serverChannel = ServerSocketChannel.open()
        serverChannel.socket().bind(InetSocketAddress(port))
        serverChannel.configureBlocking(false)
        serverChannel.register(selector, SelectionKey.OP_ACCEPT)

        Thread {
            BufferedReader(InputStreamReader(System.`in`)).use { reader ->
                while (true) {
                    if (reader.ready()) {
                        val line = reader.readLine()
                        if (line == "save") {
                            val save = Save()
                            save.commandExecution(null)
                        }
                    }
                }
            }
        }.start()


        while (true) {

            selector.select(50)
            val keys = selector.selectedKeys()
            keys.forEach { key ->
                when {
                    key.isAcceptable -> acceptClient(serverChannel)
                    key.isReadable -> readFromClient(key)
                }
                keys.remove(key)
            }

        }
    }

    private fun acceptClient(serverChannel: ServerSocketChannel) {
        val clientChannel = serverChannel.accept()
        clientChannel.configureBlocking(false)
        clientChannel.register(selector, SelectionKey.OP_READ)
    }

    private fun readFromClient(key: SelectionKey) {
        val clientChannel = key.channel() as SocketChannel
        val buffer = ByteBuffer.allocate(4096)
        val bytesRead = clientChannel.read(buffer)

        if (bytesRead == -1) {
            clientChannel.close()
            val save = Save()
            save.commandExecution(null)
            return
        }

        val data = Arrays.copyOfRange(buffer.array(), 0, bytesRead)
        val inputString = String(data)

        val commandHandler = CommandHandler()
        val outputString = commandHandler.execute(inputString).trimEnd('\n')
        CommandHandler.spaceMarine = null
        CommandHandler.chapter = null

        val outputBuffer = ByteBuffer.wrap(outputString.toByteArray())
        clientChannel.write(outputBuffer)
        buffer.clear()
    }
}
