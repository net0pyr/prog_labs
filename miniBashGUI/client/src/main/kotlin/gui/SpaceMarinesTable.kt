package com.net0pyr.gui

import com.net0pyr.entity.SpaceMarine
import com.net0pyr.enums.AstartesCategory
import com.net0pyr.enums.MeleeWeapon
import java.util.*
import javax.swing.table.DefaultTableModel

class SpaceMarinesTable(locale: Locale = Locale.getDefault()) : DefaultTableModel() {
    private val columnNames: Array<String>
    companion object {
        var data: MutableList<SpaceMarine> = mutableListOf()
    }
    init {
        val resourceBundle = ResourceBundle.getBundle("messages", locale)
        columnNames = Array(12) { resourceBundle.getString("column_${it+1}") }
        columnNames.forEach { addColumn(it) }
        data.forEach { addRow(toRow(it)) }
    }

    private fun toRow(spaceMarine: SpaceMarine): Vector<Any> = Vector(
        arrayOf(
            spaceMarine.id, spaceMarine.name, spaceMarine.coordinates!!.x, spaceMarine.coordinates!!.y,
            spaceMarine.health, spaceMarine.height, spaceMarine.category, spaceMarine.meleeWeapon,
            spaceMarine.chapter!!.name, spaceMarine.chapter!!.parentLegion, spaceMarine.chapter!!.marineCount,
            spaceMarine.chapter!!.world
        ).toList()
    )

    override fun isCellEditable(row: Int, column: Int): Boolean {
        return column != 0 // ID не редактируемый
    }

    override fun setValueAt(aValue: Any?, row: Int, column: Int) {
        super.setValueAt(aValue, row, column)
        val spaceMarine = data[row]

        when (column) {
            0 -> spaceMarine.id = (aValue as String).toLong()
            1 -> spaceMarine.name = aValue as String
            2 -> spaceMarine.coordinates?.x = (aValue as String).toFloat()
            3 -> spaceMarine.coordinates?.y = (aValue as String).toDouble()
            4 -> spaceMarine.health = (aValue as String).toDouble()
            5 -> spaceMarine.height = (aValue as String).toInt()
            6 -> spaceMarine.category = AstartesCategory.valueOf(aValue as String)
            7 -> spaceMarine.meleeWeapon = MeleeWeapon.valueOf(aValue as String)
            8 -> spaceMarine.chapter?.name = aValue as String
            9 -> spaceMarine.chapter?.parentLegion = aValue as String
            10 -> spaceMarine.chapter?.marineCount = (aValue as String).toInt()
            11 -> spaceMarine.chapter?.world = aValue as String
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
}