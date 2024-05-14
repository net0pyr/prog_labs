package com.net0pyr

import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import com.net0pyr.army.Chapter
import com.net0pyr.commands.History
import com.net0pyr.entity.SpaceMarine
import com.net0pyr.entity.SpaceMarineInTreeSet
import com.net0pyr.enums.AstartesCategory
import com.net0pyr.enums.MeleeWeapon
import com.net0pyr.location.Coordinates
import java.sql.*
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.locks.ReentrantReadWriteLock

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

        val jdbcURL = "jdbc:postgresql://localhost:5432/lab7"
        val databaseHost = "192.168.10.80"
        val databaseUser = "net0pyr"
        val databasePassword = ""

        try {
//            session = jsch.getSession(user, host, port)
//            session.setConfig("PreferredAuthentications", "publickey")
//            jsch.setKnownHosts("~/.ssh/known_hosts")
//            jsch.addIdentity(privateKey)
//            session.connect()
//
//            session.setPortForwardingL(5432, databaseHost, 5432)

            Class.forName("org.postgresql.Driver")

            connection = DriverManager.getConnection(jdbcURL, databaseUser, databasePassword)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun addAccount(login: String, password: String): Int {
        if(checkLogin(login)) {
            return -2
        }

        val preparedStatement: PreparedStatement =
            connection.prepareStatement("INSERT INTO account (login, password) VALUES (?, ?)")

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

        return login(login, password)
    }

    fun checkLogin(login: String): Boolean {
        val query = "SELECT id FROM account WHERE login = ?"

        val preparedStatement: PreparedStatement = connection.prepareStatement(query)
        preparedStatement.setString(1, login)

        val resultSet: ResultSet = preparedStatement.executeQuery()

        if (resultSet.next()) {
            return true
        }

        resultSet.close()
        preparedStatement.close()
        //connection.close()

        return false
    }

    fun login(login: String, password: String): Int {
        val query = "SELECT id FROM account WHERE login = ? AND password = ?"

        val preparedStatement: PreparedStatement = connection.prepareStatement(query)
        preparedStatement.setString(1, login)
        preparedStatement.setString(2, password)

        val resultSet: ResultSet = preparedStatement.executeQuery()

        var id = -1

        if (resultSet.next()) {
            id = resultSet.getInt("id")
        }

        resultSet.close()
        preparedStatement.close()
        //connection.close()

        return id
    }

    fun addInHistory(command:String, id: Int) {
        val query = "INSERT INTO history (command, creator) VALUES (?, ?)"
        val preparedStatement: PreparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)

        preparedStatement.setString(1, command)
        preparedStatement.setInt(2, id)

        preparedStatement.executeUpdate()

        preparedStatement.close()
    }

    fun deleteFromHistory(command:String, id: Int) {
        val query = "DELETE FROM history WHERE creator = ? AND command = ? LIMIT 1"
        val preparedStatement: PreparedStatement = connection.prepareStatement(query)

        preparedStatement.setInt(1, id)
        preparedStatement.setString(2, command)

        preparedStatement.executeUpdate()

        preparedStatement.close()
    }

    fun fill(lock: ReentrantReadWriteLock) {
        lock.readLock().lock()
        try {
            val queryLogin = "SELECT id FROM account"
            val preparedStatementLogin: PreparedStatement = connection.prepareStatement(queryLogin)
            val resultSetLogin: ResultSet = preparedStatementLogin.executeQuery()

            while (resultSetLogin.next()) {
                History.history[resultSetLogin.getInt("id")]= mutableListOf()
            }

            resultSetLogin.close()
            preparedStatementLogin.close()

            val queryHistory = "SELECT * FROM history"
            val preparedStatementHistory: PreparedStatement = connection.prepareStatement(queryHistory)
            val resultSetHistory: ResultSet = preparedStatementHistory.executeQuery()

            while (resultSetHistory.next()) {
                val id = resultSetHistory.getInt("creator")
                val command = resultSetHistory.getString("command")
                History.history[id]?.add(command)
            }

            resultSetLogin.close()
            preparedStatementLogin.close()

            SpaceMarineInTreeSet.creationTime = LocalDateTime.now()
            val query =
                "SELECT space_marine.id as id, space_marine.name as name, space_marine.coordinates as coordinates, health, height, category, " +
                        "meleeWeapon, chapter, chapter.name as chapter_name, parent_legion, marines_count, world, x, y FROM space_marine " +
                        "LEFT JOIN chapter on chapter.id = space_marine.chapter " +
                        "LEFT JOIN coordinates on coordinates.id = space_marine.coordinates; "
            val preparedStatement: PreparedStatement = connection.prepareStatement(query)
            val resultSet: ResultSet = preparedStatement.executeQuery()

            SpaceMarineInTreeSet.spaceMarines.clear()

            while (resultSet.next()) {
                val coordinates = Coordinates(resultSet.getFloat("x"), resultSet.getDouble("y"))
                val chapter = Chapter(
                    resultSet.getString("chapter_name"), resultSet.getString("parent_legion"),
                    resultSet.getInt("marines_count"), resultSet.getString("world")
                )
                val spaceMarine = SpaceMarine(
                    resultSet.getString("name"),
                    coordinates,
                    resultSet.getDouble("health"),
                    resultSet.getInt("height"),
                    AstartesCategory.valueOf(resultSet.getString("category").uppercase(Locale.getDefault()).replace(' ','_')),
                    MeleeWeapon.valueOf(resultSet.getString("meleeweapon").uppercase().replace(' ','_')),
                    chapter
                )
                SpaceMarineInTreeSet.spaceMarines.add(spaceMarine)
            }

            resultSet.close()
            preparedStatement.close()
        } finally {
            lock.readLock().unlock()
        }
    }

    fun getCreatorSpaceMarine(id: Long): Int {
        val query = "SELECT creator FROM space_marine WHERE id = ?"

        val preparedStatement: PreparedStatement = connection.prepareStatement(query)
        preparedStatement.setInt(1, id.toInt())

        val resultSet: ResultSet = preparedStatement.executeQuery()

        var creator = -1

        if (resultSet.next()) {
            creator = resultSet.getInt("id")
        }

        preparedStatement.close()
        //connection.close()

        return creator
    }

    fun addChapter(chapter: Chapter, creator: Int): Int {
        val query = "INSERT INTO chapter (name, parent_legion, marines_count, world, creator) VALUES (?, ?, ?, ?, ?)"
        val preparedStatement: PreparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)

        preparedStatement.setString(1, chapter.getName())
        preparedStatement.setString(2, chapter.getLegion())
        chapter.getMarinesCount()?.let { preparedStatement.setInt(3, it) }
        preparedStatement.setString(4, chapter.getWorld())
        preparedStatement.setInt(5, creator)

        preparedStatement.executeUpdate()

        val generatedKeys = preparedStatement.generatedKeys
        var chapterId = -1
        if (generatedKeys.next()) {
            chapterId = generatedKeys.getInt(1)
        }
        preparedStatement.close()
        return chapterId
    }

    fun addCoordinates(coordinates: Coordinates, creator: Int): Int {
        val query = "INSERT INTO coordinates (x, y, creator) VALUES (?, ?, ?)"
        val preparedStatement: PreparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)

        coordinates.getX()?.let { preparedStatement.setFloat(1, it) }
        coordinates.getY()?.let { preparedStatement.setDouble(2, it) }
        preparedStatement.setInt(3, creator)

        preparedStatement.executeUpdate()

        val generatedKeys = preparedStatement.generatedKeys
        var coordinatesId = -1
        if (generatedKeys.next()) {
            coordinatesId = generatedKeys.getInt(1)
        }

        preparedStatement.close()
        return coordinatesId
    }

    fun add(spaceMarine: SpaceMarine, creator: Int): Long {
        val coordinatesId = spaceMarine.getCoordinates()?.let { addCoordinates(it, creator) }
        val chapterId = spaceMarine.getChapter()?.let { addChapter(it, creator) }

        val query =
            "INSERT INTO space_marine (name, coordinates, health, height, category, meleeweapon, chapter, creator) " +
                    "VALUES (?, ?, ?, ?, CAST(? AS astrates_category), CAST(? AS melle_weapon), ?, ?)"

        val preparedStatement: PreparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        preparedStatement.setString(1, spaceMarine.getName())
        if (coordinatesId != null) {
            preparedStatement.setInt(2, coordinatesId)
        }
        spaceMarine.getHealth()?.let { preparedStatement.setDouble(3, it) }
        spaceMarine.getHeight()?.let { preparedStatement.setInt(4, it) }
        println(spaceMarine.getCategory())
        preparedStatement.setString(5, spaceMarine.getCategory())
        preparedStatement.setString(6, spaceMarine.getMeleeWeapon())
        if (chapterId != null) {
            preparedStatement.setInt(7, chapterId)
        }
        preparedStatement.setInt(8, creator)

        preparedStatement.executeUpdate()

        val generatedKeys = preparedStatement.generatedKeys
        var id: Long = -1
        if (generatedKeys.next()) {
            id = generatedKeys.getLong(1)
        }
        preparedStatement.close()
        return id
    }

    fun updateSpaceMarine(spaceMarine: SpaceMarine, creator: Int) {


        val coordinatesId = spaceMarine.getCoordinates()?.let { addCoordinates(it, creator) }
        val chapterId = spaceMarine.getChapter()?.let { addChapter(it, creator) }

        val query =
            "UPDATE space_marine SET name = ?, coordinates = ?, health = ?, height = ?, category = ?, meleeweapon = ?, chapter = ? WHERE id = ?"
        val preparedStatement: PreparedStatement = connection.prepareStatement(query)

        preparedStatement.setString(1, spaceMarine.getName())
        if (coordinatesId != null) {
            preparedStatement.setInt(2, coordinatesId)
        }
        spaceMarine.getHealth()?.let { preparedStatement.setDouble(3, it) }
        spaceMarine.getHeight()?.let { preparedStatement.setInt(4, it) }
        preparedStatement.setString(5, spaceMarine.getCategory())
        preparedStatement.setString(6, spaceMarine.getMeleeWeapon())
        preparedStatement.setString(7, chapterId.toString())
        preparedStatement.setInt(8, spaceMarine.getId().toInt())

        preparedStatement.executeUpdate()

        preparedStatement.close()
    }

    fun deleteSpaceMarine(spaceMarine: SpaceMarine) {
        deleteChapterAndCoordinates(spaceMarine)
        val query = "DELETE FROM space_marine WHERE id = ?"
        val preparedStatement: PreparedStatement = connection.prepareStatement(query)

        preparedStatement.setInt(1, spaceMarine.getId().toInt())

        preparedStatement.executeUpdate()

        preparedStatement.close()
    }

    fun deleteChapterAndCoordinates(spaceMarine: SpaceMarine) {
        var query = "SELECT chapter, coordinates FROM space_marine WHERE id = ?"
        var preparedStatement: PreparedStatement = connection.prepareStatement(query)
        preparedStatement.setInt(1, spaceMarine.getId().toInt())
        val resultSet: ResultSet = preparedStatement.executeQuery()
        var chapterId: Int? = null
        var coordinatesId: Int? = null
        if (resultSet.next()) {
            chapterId = resultSet.getInt("chapter")
            coordinatesId = resultSet.getInt("coordinates")
        }

        preparedStatement.close()
        query = "DELETE FROM coordinates WHERE id = ?"
        preparedStatement = connection.prepareStatement(query)
        if (coordinatesId != null) {
            preparedStatement.setInt(1, coordinatesId)
        }
        preparedStatement.executeUpdate()
        preparedStatement.close()

        query = "DELETE FROM chapter WHERE id = ?"
        preparedStatement = connection.prepareStatement(query)
        if (chapterId != null) {
            preparedStatement.setInt(1, chapterId)
        }
        preparedStatement.executeUpdate()
        preparedStatement.close()

    }
}