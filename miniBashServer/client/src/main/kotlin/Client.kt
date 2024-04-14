package com.net0pyr

import com.net0pyr.WorkingWithCommand.CommandHandler
import com.net0pyr.commands.ExecuteScript
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.SocketChannel
import java.util.*

class Client {
    private val host = "localhost"
    private val port = 12345
    private val selector = Selector.open()
    private val buffer = ByteBuffer.allocate(4096)
    private var isConnected = false
    fun start(scanner: Scanner) {
        val clientChannel = SocketChannel.open()
        clientChannel.configureBlocking(false)
        clientChannel.connect(InetSocketAddress(host, port))
        clientChannel.register(selector, SelectionKey.OP_CONNECT)

        while (true) {
            selector.select()
            val keys = selector.selectedKeys()
            val keyIterator = keys.iterator()
            while (keyIterator.hasNext()) {
                val key = keyIterator.next()
                keyIterator.remove()
                when {
                    key.isConnectable -> connectToServer(clientChannel, scanner)
                    key.isReadable -> readFromServer(clientChannel, scanner)
                }
                if (!isConnected) {
                    return
                }
            }
            if (!isConnected) {
                return
            }
        }
    }

    private fun connectToServer(clientChannel: SocketChannel, scanner: Scanner) {
        if (clientChannel.isConnectionPending) {
            clientChannel.finishConnect()
            clientChannel.register(selector, SelectionKey.OP_READ)
            isConnected = true

            if (scanner.hasNextLine()) {
                readLine(scanner, clientChannel)
            }
        }
    }

    private fun readFromServer(clientChannel: SocketChannel, scanner: Scanner) {
        buffer.clear()
        val bytesRead = clientChannel.read(buffer)

        if (bytesRead == -1) {
            clientChannel.close()
            return
        }
        buffer.flip()
        val data = ByteArray(buffer.remaining())
        buffer.get(data)
        val message = String(data)

        println(message)
        if(isConnected && CommandHandler.executeScriptFlag) {
            readLine(scanner, clientChannel)
        } else {
            if (isConnected && scanner.hasNextLine()) {
                readLine(scanner, clientChannel)
            }
        }
    }

    private fun readLine(scanner: Scanner, clientChannel: SocketChannel) {
        lateinit var outputString: String
        if (!CommandHandler.executeScriptFlag) {
            val inputString = scanner.nextLine()
            val commandHandler = CommandHandler()
            if (inputString == "exit") {
                println("Отключение от сервера")
                clientChannel.close()
                isConnected = false
                return
            }
            outputString = commandHandler.execute(inputString)
        }
        if (CommandHandler.executeScriptFlag || outputString == "executeScript"){
            val index = ExecuteScript.index
            val command = ExecuteScript.commands[index]
            ExecuteScript.index++
            if (ExecuteScript.index == ExecuteScript.commands.size) {
                CommandHandler.executeScriptFlag = false
                ExecuteScript.index = 0
                ExecuteScript.commands.clear()
            }
            outputString = Json.encodeToString(command)
        }
        val outputBuffer = ByteBuffer.wrap(outputString.toByteArray())
        clientChannel.write(outputBuffer)
    }
}