package com.net0pyr

import com.net0pyr.WorkingWithCommand.CommandHandler
import com.net0pyr.commands.History
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel
import java.util.*
import java.util.concurrent.Executors

class Server {
    private val port = 12345
    private val selector = Selector.open()
    val cacheRead = Executors.newCachedThreadPool()
    val fixedExecute = Executors.newFixedThreadPool(20)
    val fixedWrite = Executors.newFixedThreadPool(20)

    companion object {
        val logger: Logger? = LogManager.getLogger(Server::class.java)
        val dataBase = DataBase()
    }

    fun start() {
        val serverChannel = ServerSocketChannel.open()
        serverChannel.socket().bind(InetSocketAddress(port))
        serverChannel.configureBlocking(false)
        serverChannel.register(selector, SelectionKey.OP_ACCEPT)
        logger?.info("Сервер начал работу")
//        Thread {
//            BufferedReader(InputStreamReader(System.`in`)).use { reader ->
//                while (true) {
//                    if (reader.ready()) {
//                        val line = reader.readLine()
//                        if (line == "save") {
//                            val save = Save()
//                            save.commandExecution(null)
//                        }
//                    }
//                }
//            }
//        }.start()


        while (true) {

            selector.select(50)
            val keys = selector.selectedKeys()
            keys.forEach { key ->
                when {
                    key.isAcceptable -> acceptClient(serverChannel)
                    key.isReadable -> readFromClient(key)
                }
                Thread.sleep(50)
                keys.remove(key)
            }

        }
    }

    private fun acceptClient(serverChannel: ServerSocketChannel) {
        logger?.info("Получено новое подключение")
        val clientChannel = serverChannel.accept()
        clientChannel.configureBlocking(false)
        val buffer = ByteBuffer.allocate(4096)
        val outputBuffer = ByteBuffer.wrap(listsWithData())
        clientChannel.write(outputBuffer)
        buffer.clear()
        clientChannel.register(selector, SelectionKey.OP_READ)
    }

    private fun readFromClient(key: SelectionKey) {

        class Writer(private val clientChannel: SocketChannel, private val buffer: ByteBuffer, private val outputString: String) : Runnable {
            override fun run() {
                logger?.info("Отправлен ответ: {}", outputString)
                val outputBuffer = ByteBuffer.wrap(outputString.toByteArray())
                clientChannel.write(outputBuffer)
                buffer.clear()
            }
        }

        class Executor(private val clientChannel: SocketChannel, private val buffer: ByteBuffer, private val inputString: String) : Runnable {
            override fun run() {
                lateinit var outputString: String

                when (inputString[0]) {
                    '1' -> {
                        val login = inputString.split(":")[1]
                        val password = inputString.split(":")[2]
                        val id = dataBase.login(login, password)
                        outputString = if (id != -1) {
                            "Вход успешно выполнен:${id}"
                        } else {
                            "Неверный логин или пароль"
                        }
                    }

                    '2' -> {
                        val login = inputString.split(":")[1]
                        val password = inputString.split(":")[2]
                        val id = dataBase.addAccount(login, password)
                        outputString = if (id == -1) {
                            "Ошибка создания аккаунта"
                        } else if (id == -2) {
                            "Аккаунт с таким логином уже существует"
                        }else {
                            History.history[id] = mutableListOf<String>()
                            "Аккаунт успешно добавлен. Можете воспользоваться командой help, чтобы ознакомиться с командами.:${id}"
                        }
                    }

                    else -> {
                        val commandHandler = CommandHandler()
                        outputString = commandHandler.execute(inputString).trimEnd('\n')
                    }
                }
                val write = Writer(clientChannel, buffer, outputString)
                fixedWrite.execute(write)
            }
        }

        class Reader(private val clientChannel: SocketChannel, private val buffer: ByteBuffer) : Runnable {
            override fun run() {
                val bytesRead = clientChannel.read(buffer)

                if (bytesRead == -1) {
                    clientChannel.close()
                    logger?.info("Клиент отключился")
                    return
                }

                val data = Arrays.copyOfRange(buffer.array(), 0, bytesRead)
                val inputString = String(data)
                logger?.info("Получен новый запрос: {}", inputString)
                val executor = Executor(clientChannel, buffer, inputString)
                fixedExecute.execute(executor)
            }
        }

        val clientChannel = key.channel() as SocketChannel
        val buffer = ByteBuffer.allocate(4096)

        val reader = Reader(clientChannel,buffer)
        cacheRead.execute(reader)

//        val clientChannel = key.channel() as SocketChannel
//        val buffer = ByteBuffer.allocate(4096)
//        val bytesRead = clientChannel.read(buffer)
//
//        if (bytesRead == -1) {
//            clientChannel.close()
//            logger?.info("Клиент отключился")
//            return
//        }
//
//        val data = Arrays.copyOfRange(buffer.array(), 0, bytesRead)
//        val inputString = String(data)
//        logger?.info("Получен новый запрос: {}", inputString)
//        lateinit var outputString: String
//
//        when (inputString[0]) {
//            '1' -> {
//                val login = inputString.split(":")[1]
//                val password = inputString.split(":")[2]
//                val id = dataBase.login(login, password)
//                outputString = if (id != -1) {
//                    "Вход успешно выполнен:${id}"
//                } else {
//                    "Неверный логин или пароль"
//                }
//            }
//
//            '2' -> {
//                val login = inputString.split(":")[1]
//                val password = inputString.split(":")[2]
//                val id = dataBase.addAccount(login, password)
//                outputString = if (id == -1) {
//                    "Ошибка создания аккаунта"
//                } else {
//                    "Аккаунт успешно добавлен. Можете воспользоваться командой help, чтобы ознакомиться с командами.:${id}"
//                }
//            }
//
//            else -> {
//                val commandHandler = CommandHandler()
//                outputString = commandHandler.execute(inputString).trimEnd('\n')
//            }
//        }
//        logger?.info("Отправлен ответ: {}", outputString)
//        val outputBuffer = ByteBuffer.wrap(outputString.toByteArray())
//        clientChannel.write(outputBuffer)
//        buffer.clear()

    }

    private fun listsWithData(): ByteArray {
        val commandWithSpaceMarine = listOf("add", "add_if_max", "remove_lower", "update")
        val commandWithChapter = listOf("count_less_than_chapter")
        var outputString =
            commandWithSpaceMarine.joinToString(separator = " ") + "\n" + commandWithChapter.joinToString(separator = " ")
        return outputString.toByteArray()
    }
}