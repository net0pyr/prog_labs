package com.net0pyr

import com.net0pyr.WorkingWithCommand.CommandHandler
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
            }
        }
    }

    private fun connectToServer(clientChannel: SocketChannel, scanner: Scanner) {
        if (clientChannel.isConnectionPending) {
            clientChannel.finishConnect()
            clientChannel.register(selector, SelectionKey.OP_READ)
            isConnected = true

            if (scanner.hasNextLine()) {
                readLine(scanner,clientChannel)
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
        if (isConnected && scanner.hasNextLine()) {
            readLine(scanner,clientChannel)
        }
    }

    private fun readLine(scanner: Scanner, clientChannel: SocketChannel) {
        val inputString = scanner.nextLine()
        val commandHandler = CommandHandler()
        val outputString = commandHandler.execute(inputString)
        val outputBuffer = ByteBuffer.wrap(outputString.toByteArray())
        clientChannel.write(outputBuffer)
    }
}