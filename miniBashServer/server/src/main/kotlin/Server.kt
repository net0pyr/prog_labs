package com.net0pyr

import com.net0pyr.WorkingWithCommand.CommandHandler
import com.net0pyr.commands.Save
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
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

    companion object {
        val logger: Logger? = LogManager.getLogger(Server::class.java)
    }

    fun start() {
        val serverChannel = ServerSocketChannel.open()
        serverChannel.socket().bind(InetSocketAddress(port))
        serverChannel.configureBlocking(false)
        serverChannel.register(selector, SelectionKey.OP_ACCEPT)
        logger?.info("Сервер начал работу")
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
        logger?.info("Получено новое подключение")
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
            logger?.info("Клиент отключился")
            return
        }

        val data = Arrays.copyOfRange(buffer.array(), 0, bytesRead)
        val inputString = String(data)
        logger?.info("Получен новый запрос: {}", inputString)

        val commandHandler = CommandHandler()
        val outputString = commandHandler.execute(inputString).trimEnd('\n')
        CommandHandler.spaceMarine = null
        CommandHandler.chapter = null

        logger?.info("Отправлен ответ: {}", outputString)
        val outputBuffer = ByteBuffer.wrap(outputString.toByteArray())
        clientChannel.write(outputBuffer)
        buffer.clear()
    }
}
