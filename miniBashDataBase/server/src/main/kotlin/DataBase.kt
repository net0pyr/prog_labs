package com.net0pyr

import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import java.sql.DriverManager

class DataBase {
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
            val session: Session = jsch.getSession(user, host, port)
            session.setConfig("PreferredAuthentications", "publickey");
            jsch.setKnownHosts("~/.ssh/known_hosts")
            jsch.addIdentity(privateKey)
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect()

            session.setPortForwardingL(localPort, databaseHost, 5432) // указываем стандартный порт для PostgreSQL

            System.setProperty("jdbc.url", jdbcURL)
            System.setProperty("jdbc.user", databaseUser)

//             Загрузка JDBC драйвера
            Class.forName("org.postgresql.Driver")

            val connection = DriverManager.getConnection(jdbcURL, databaseUser, databasePassword)

            // Пример выполнения SQL запроса
            val statement = connection.createStatement()
            val resultSet = statement.executeQuery("SELECT * FROM digging")

            while (resultSet.next()) {
                println(resultSet.getString("leader_id"))
            }

            resultSet.close()
            statement.close()
            connection.close()

            session.disconnect()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}