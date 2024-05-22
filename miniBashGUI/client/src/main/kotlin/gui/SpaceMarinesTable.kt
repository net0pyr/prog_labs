package com.net0pyr.gui

import com.net0pyr.Client
import com.net0pyr.army.Chapter
import com.net0pyr.entity.SpaceMarine
import com.net0pyr.enums.AstartesCategory
import com.net0pyr.enums.MeleeWeapon
import com.net0pyr.location.Coordinates
import java.util.*
import javax.swing.table.DefaultTableModel

class SpaceMarinesTable(locale: Locale = Locale.getDefault()) : DefaultTableModel() {
    private val columnNames: Array<String>

    companion object {
        var data: MutableList<SpaceMarine> = mutableListOf()
    }

    init {
        val resourceBundle = ResourceBundle.getBundle("messages", locale)
        columnNames = Array(12) { resourceBundle.getString("column_${it + 1}") }
        columnNames.forEach { addColumn(it) }
        data.forEach { addRow(toRow(it)) }
    }

    fun toRow(spaceMarine: SpaceMarine): Vector<Any> = Vector(
        arrayOf(
            spaceMarine.id, spaceMarine.name, spaceMarine.coordinates!!.x, spaceMarine.coordinates!!.y,
            spaceMarine.health, spaceMarine.height, spaceMarine.category, spaceMarine.meleeWeapon,
            spaceMarine.chapter!!.name, spaceMarine.chapter!!.parentLegion, spaceMarine.chapter!!.marineCount,
            spaceMarine.chapter!!.world
        ).toList()
    )

    override fun isCellEditable(row: Int, column: Int): Boolean {
        return column != 0
    }

    override fun getValueAt(row: Int, column: Int): Any? {
        val spaceMarine = data[row]
        return when (column) {
            0 -> spaceMarine.id.toInt()
            1 -> spaceMarine.name
            2 -> spaceMarine.coordinates?.x?.toDouble()
            3 -> spaceMarine.coordinates?.y
            4 -> spaceMarine.health
            5 -> spaceMarine.height
            6 -> spaceMarine.category as AstartesCategory
            7 -> spaceMarine.meleeWeapon as MeleeWeapon
            8 -> spaceMarine.chapter?.name
            9 -> spaceMarine.chapter?.parentLegion
            10 -> spaceMarine.chapter?.marineCount
            11 -> spaceMarine.chapter?.world
            else -> null
        }
    }

    override fun setValueAt(aValue: Any?, row: Int, column: Int) {

        val spaceMarine = data[row]

        when (column) {
            1 -> spaceMarine.name = (aValue as? String) ?: ""
            2 -> spaceMarine.coordinates?.x = (aValue as? String)?.toFloatOrNull() ?: 0f
            3 -> spaceMarine.coordinates?.y = (aValue as? String)?.toDoubleOrNull() ?: 0.0
            4 -> spaceMarine.health = (aValue as? String)?.toDoubleOrNull() ?: 0.0
            5 -> spaceMarine.height = (aValue as? String)?.toIntOrNull() ?: 0
            6 -> spaceMarine.category = (aValue as? String)?.let { AstartesCategory.valueOf(it) } ?: null
            7 -> spaceMarine.meleeWeapon = (aValue as? String)?.let { MeleeWeapon.valueOf(it) } ?: null
            8 -> spaceMarine.chapter?.name = (aValue as? String) ?: ""
            9 -> spaceMarine.chapter?.parentLegion = (aValue as? String) ?: ""
            10 -> spaceMarine.chapter?.marineCount = (aValue as? String)?.toIntOrNull() ?: 0
            11 -> spaceMarine.chapter?.world = (aValue as? String) ?: ""
        }


        Client.command.spaceMarine = spaceMarine
        Client.command.commandArgument = spaceMarine.id.toString()
        Client.command.name = "update"
    }

    override fun getColumnClass(columnIndex: Int): Class<*> {
        return when (columnIndex) {
//            0, 5, 10 -> Int::class.java
//            1, 8, 9, 11 -> String::class.java
//            2, 3, 4 -> Double::class.java
            6 -> AstartesCategory::class.java
            7 -> MeleeWeapon::class.java
            else -> String::class.java
        }
    }

    fun addObject(spaceMarine: SpaceMarine) {
        data.add(spaceMarine)
        addRow(toRow(spaceMarine))
    }

    fun removeObject(row: Int) {
        data.removeAt(row)
        removeRow(row)
    }

    fun updateObject(row: Int, spaceMarine: SpaceMarine) {
        data[row] = spaceMarine
        for (i in 0 until columnCount) {
            setValueAt(toRow(spaceMarine)[i], row, i)
        }
    }

    fun getObjects(): List<SpaceMarine> = data.toList()

    fun setFilteredData(filteredObjects: List<SpaceMarine>) {
        val vectorData = filteredObjects.map { toRow(it) }.toCollection(Vector())
        val vectorColumns = columnNames.toCollection(Vector())
        setDataVector(vectorData, vectorColumns)
    }

    fun refreshTable() {
        // Удаляем все строки из таблицы
        rowCount = 0

        // Добавляем строки с обновленными данными
        data.forEach { addRow(toRow(it)) }

        // Уведомляем о структурных изменениях в таблице
        fireTableStructureChanged()
        fireTableDataChanged()
    }
}
