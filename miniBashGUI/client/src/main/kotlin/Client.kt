package com.net0pyr

import com.net0pyr.WorkingWithCommand.CommandHandler
import com.net0pyr.commands.ExecuteScript
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.ConnectException
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.ClosedChannelException
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.SocketChannel
import java.security.MessageDigest
import java.util.*

class Client {
    private val host = "localhost"
    private val port = 12345
    private val selector = Selector.open()
    private val buffer = ByteBuffer.allocate(4096)
    private var isConnected = false
    private var isFirst = true
    private var isLogin = false
    private var id = -1
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
            if (!isConnected) {
                return
            }
        }
    }

    private fun connectToServer(clientChannel: SocketChannel, scanner: Scanner) {
        try {
            if (clientChannel.isConnectionPending) {
                clientChannel.finishConnect()

                clientChannel.register(selector, SelectionKey.OP_READ)
                isConnected = true

                readFromServer(clientChannel, scanner)

                readLine(scanner, clientChannel)
            }
        } catch (_: ClosedChannelException) {
            clientChannel.register(selector, SelectionKey.OP_CONNECT)
        } catch (_: ConnectException) {
            clientChannel.register(selector, SelectionKey.OP_CONNECT)
        }
    }

    private fun readFromServer(clientChannel: SocketChannel, scanner: Scanner) {
        if (isFirst) {
            selector.select(50)
            buffer.clear()
            val bytesRead = clientChannel.read(buffer)
            buffer.flip()
            val data = ByteArray(buffer.remaining())
            buffer.get(data)
            val message = String(data)
            readLists(message)
            isFirst = false
        } else {
            buffer.clear()
            val bytesRead = clientChannel.read(buffer)

            if (bytesRead == -1) {
                clientChannel.close()
                return
            }
            buffer.flip()
            val data = ByteArray(buffer.remaining())
            buffer.get(data)
            val message = String(data).split(":")[0]
            if(message == "Аккаунт успешно добавлен. Можете воспользоваться командой help, чтобы ознакомиться с командами."||
                message == "Вход успешно выполнен") {
                isLogin = true
                id = String(data).split(":")[1].toInt()
            }
            println(message)
            if (isConnected && CommandHandler.executeScriptFlag) {
                readLine(scanner, clientChannel)
            } else {
                if (isConnected) {
                    readLine(scanner, clientChannel)
                }
            }
        }
    }

    private fun readLine(scanner: Scanner, clientChannel: SocketChannel) {
        lateinit var outputString: String
        if (!CommandHandler.executeScriptFlag) {
            val commandHandler = CommandHandler()
            if(isLogin) {
                val inputString = scanner.nextLine()
                if (inputString == "exit") {
                    println("Отключение от сервера")
                    clientChannel.close()
                    isConnected = false
                    return
                }
                outputString = commandHandler.execute(inputString, id)
            } else {
                println("У Вас есть аккаунт(1-да, 2-нет):")
                val typeOfLogin: String = scanner.nextLine()
                when(typeOfLogin) {
                    "1" -> {
                        println("Логин:")
                        val login = scanner.nextLine()
                        println("Пароль:")
                        val password = scanner.nextLine()
                        outputString = "1:${login}:${sha256(password)}"
                    }
                    "2" ->  {
                        println("Придумайте логин:")
                        val login = scanner.nextLine()
                        println("Придумайте пароль:")
                        val password = scanner.nextLine()
                        println("Повторите пароль:")
                        val repeatPassword = scanner.nextLine()
                        if(password != repeatPassword) {
                            println("Ошибка создания аккаунта. Пароли не совпадают")
                        } else {
                            outputString = "2:${login}:${sha256(password)}"
                        }
                    }
                    else -> println("Неверный формат ввода")
                }
            }
        }
        if (CommandHandler.executeScriptFlag || outputString == "executeScript") {
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

    private fun readLists(inputString: String) {
        val spaceMarine = inputString.split("\n")[0]
        val chapter = inputString.split("\n")[1]
        CommandHandler.commandWithSpaceMarine += spaceMarine.split(' ')
        CommandHandler.commandWithChapter += chapter.split(' ')
    }

    private fun sha256(input: String): String {
        val bytes = input.toByteArray()
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(bytes)
        return hashBytes.joinToString("") { "%02x".format(it) }
    }
}