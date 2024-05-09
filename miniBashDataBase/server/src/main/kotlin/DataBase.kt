package com.net0pyr

import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet

class DataBase {
    companion object {
        var accounts = mutableMapOf<String, String>()
        lateinit var connection: Connection
        lateinit var session: Session
    }

    init {
        val jsch = JSch()

        val host = "se.ifmo.ru"
        val user = "s408674"
        val privateKey = "~/.ssh/id_rsa"
        val port = 2222

        val jdbcURL = "jdbc:postgresql://localhost:5432/studs"
        val databaseHost = "pg"
        val databaseUser = "s408674"
        val databasePassword = "JqiD08XJqxZ0CiaR"

        val localPort = 5432 // локальный порт для туннелирования

        try {
            session = jsch.getSession(user, host, 2222)
            session.setConfig("PreferredAuthentications", "publickey");
            jsch.setKnownHosts("~/.ssh/known_hosts")
            jsch.addIdentity(privateKey)
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect()

            session.setPortForwardingL(localPort, databaseHost, 5432) // указываем стандартный порт для PostgreSQL

            System.setProperty("jdbc.url", jdbcURL)
            System.setProperty("jdbc.user", databaseUser)

            Class.forName("org.postgresql.Driver")

            connection = DriverManager.getConnection(jdbcURL, databaseUser, databasePassword)
//
//
//            val statement = connection.createStatement()
//            val resultSet = statement.executeQuery("SELECT * FROM account")
//
//            while (resultSet.next()) {
//                println(resultSet)
//            }
//
//            resultSet.close()
//            statement.close()
//            connection.close()

            //session.disconnect()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun addAccount(login: String, password: String) {
        val preparedStatement: PreparedStatement = connection.prepareStatement("INSERT INTO account (login, password) VALUES (?, ?)")

        preparedStatement.setString(1, login)
        preparedStatement.setString(2, password)

        val rowsAffected = preparedStatement.executeUpdate()

        if (rowsAffected > 0) {
            Server.logger?.info("Пользователь ${login} успешно добавлен")
        } else {
            Server.logger?.info("Произошла ошибка. Пользователь ${login} не добавлен")
        }

        preparedStatement.close()
        //connection.close()
    }

    fun login(login: String, password: String): Boolean {
        val query = "SELECT COUNT(*) FROM account WHERE login = ? AND password = ?"

        val preparedStatement: PreparedStatement = connection.prepareStatement(query)
        preparedStatement.setString(1, login)
        preparedStatement.setString(2, password)

        val resultSet: ResultSet = preparedStatement.executeQuery()

        resultSet.next()
        val count = resultSet.getInt(1)

        preparedStatement.close()
        //connection.close()

        return if (count > 0) {
            true
        } else {
            false
        }
    }
}