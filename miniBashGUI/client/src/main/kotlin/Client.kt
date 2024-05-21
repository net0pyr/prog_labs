package com.net0pyr

import com.net0pyr.WorkingWithCommand.CommandHandler
import com.net0pyr.entity.Command
import com.net0pyr.gui.Login
import com.net0pyr.gui.SpaceMarinesTable
import com.net0pyr.gui.UserApplication
import kotlinx.serialization.decodeFromString
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
import javax.swing.JOptionPane
import javax.swing.SwingUtilities

class Client {
    private val host = "localhost"
    private val port = 12345
    private val buffer = ByteBuffer.allocate(4096)
    private var isConnected = false
    private var isFirst = true
    private var isLogin = false
    companion object {
        var command = Command("no")
        var id = -1
        val selector: Selector = Selector.open()
    }
    fun start(scanner: Scanner) {
        val clientChannel = SocketChannel.open()
        clientChannel.configureBlocking(false)
        clientChannel.connect(InetSocketAddress(host, port))
        clientChannel.register(selector, SelectionKey.OP_CONNECT)
        val login = Login()
        login.execute()


        while (true) {
            selector.select()
            val keys = selector.selectedKeys()
            val keyIterator = keys.iterator()
            while (keyIterator.hasNext()) {
                val key = keyIterator.next()
                keyIterator.remove()
                when {
                    key.isConnectable -> connectToServer(clientChannel)
                    key.isReadable -> readFromServer(clientChannel)
                }
            }
            if (!isConnected) {
                return
            }
        }
    }

    private fun connectToServer(clientChannel: SocketChannel) {
        try {
            if (clientChannel.isConnectionPending) {
                clientChannel.finishConnect()

                clientChannel.register(selector, SelectionKey.OP_READ)
                isConnected = true

                readFromServer(clientChannel)

                readLine(clientChannel)
            }
        } catch (_: ClosedChannelException) {
            clientChannel.register(selector, SelectionKey.OP_CONNECT)
        } catch (_: ConnectException) {
            clientChannel.register(selector, SelectionKey.OP_CONNECT)
        }
    }

    private fun readFromServer(clientChannel: SocketChannel) {
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
            if (message == "Аккаунт успешно добавлен. Можете воспользоваться командой help, чтобы ознакомиться с командами." ||
                message == "Вход успешно выполнен"
            ) {
                isLogin = true
                id = String(data).split(":")[1].toInt()
                Login.frame.dispose()
                SwingUtilities.invokeLater {
                    UserApplication().isVisible = true
                }
            } else if (message == "Неверный логин или пароль" ||
                message == "Ошибка создания аккаунта" ||
                message == "Аккаунт с таким логином уже существует"
            ) {
                JOptionPane.showMessageDialog(Login.frame, message, "Error", JOptionPane.ERROR_MESSAGE)
                Login.loginFlag = -1
            }
            if(command.name == "show") {
                SpaceMarinesTable.data.clear()
                String(data).split("|").forEach {
                    println(it)
                    SpaceMarinesTable.data.add(Json.decodeFromString(it))
                }
            }
            if(command.name == "add") {
                UserApplication.tableModel.addObject(Json.decodeFromString(message))
            }
            command.name = "no"
            if (isConnected && CommandHandler.executeScriptFlag) {
                readLine(clientChannel)
            } else {
                if (isConnected) {
                    readLine(clientChannel)
                }
            }
        }
    }

    private fun readLine(clientChannel: SocketChannel) {
        lateinit var outputString: String
        if (isLogin) {
            while (true) {
                selector.select(50)
                when (command.name) {
                    "no" -> continue
                    else -> {
                        println(command.name)
                        command.id = id
                        outputString = Json.encodeToString(command)
                        break
                    }
                }
            }
        } else {
            while (true) {
                selector.select(50)
                when (Login.loginFlag) {
                    1 -> {
                        outputString = "1:${Login.username}:${sha256(Login.password)}"
                        break
                    }

                    2 -> {
                        outputString = "2:${Login.username}:${sha256(Login.password)}"
                        break
                    }

                    -1 -> continue
                }
            }
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