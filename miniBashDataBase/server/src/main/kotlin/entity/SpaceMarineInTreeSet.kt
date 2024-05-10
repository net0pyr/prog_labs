package com.net0pyr.entity

import com.net0pyr.DataBase
import com.net0pyr.army.Chapter
import com.net0pyr.enums.AstartesCategory
import com.net0pyr.enums.MeleeWeapon
import com.net0pyr.location.Coordinates
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.locks.ReentrantReadWriteLock

/**
 * Класс описывающий коллекцию в которой хранятся десантники
 * @author net0pyr
 */
class SpaceMarineInTreeSet {
    companion object {
        /** коллекция с десантниками*/
        var spaceMarines: TreeSet<SpaceMarine> = TreeSet()

        /** время создания коллекции*/
        lateinit var creationTime: LocalDateTime
    }

//    /** Метод заполнения коллекции */
//    fun fill(lock: ReentrantReadWriteLock) {
//        lock.readLock().lock()
//        try {
//            val query = "SELECT * FROM space_marine LEFT JOIN chapter on chapter.id = space_marine.chapter LEFT JOIN coordinates on coordinates.id = space_marine.coordinates;"
//            val preparedStatement: PreparedStatement = DataBase.connection.prepareStatement(query)
//            val resultSet: ResultSet = preparedStatement.executeQuery()
//
//            spaceMarines.clear()
//
//            while (resultSet.next()) {
//                val coordinates = Coordinates(resultSet.getFloat("x"),resultSet.getDouble("y"))
//                val chapter = Chapter(resultSet.getString("chapter.name"),resultSet.getString("parent_legion"),
//                    resultSet.getInt("marines_count"),resultSet.getString("world"))
//                val spaceMarine = SpaceMarine(resultSet.getString("space_marine.name"),coordinates,resultSet.getDouble("health"),
//                    resultSet.getInt("height"),AstartesCategory.valueOf(resultSet.getString("category")),
//                    MeleeWeapon.valueOf(resultSet.getString("meleeweapon")), chapter)
//                spaceMarines.add(spaceMarine)
//            }
//
//            resultSet.close()
//            preparedStatement.close()
//        } finally {
//            lock.readLock().unlock()
//        }
//    }
}