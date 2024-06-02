package com.net0pyr.gui

import com.net0pyr.Client
import com.net0pyr.entity.SpaceMarine
import com.net0pyr.enums.AstartesCategory
import com.net0pyr.enums.MeleeWeapon
import java.util.*
import javax.swing.JOptionPane
import javax.swing.table.DefaultTableModel

class SpaceMarinesTable(locale: Locale = Locale("en")) : DefaultTableModel() {
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

    override fun setValueAt(aValue: Any?, row: Int, column: Int) {

        val spaceMarine = data[row]

        when (column) {
            1 -> spaceMarine.name = (aValue as? String) ?: ""
            2 -> {
                val xValue = (aValue as? String)?.toFloatOrNull()
                if (xValue != null) {
                    spaceMarine.coordinates?.x = xValue
                } else {
                    JOptionPane.showMessageDialog(
                        null,
                        UserApplication.resourceBundle.getString("invalidFormat"),
                        UserApplication.resourceBundle.getString("error"),
                        JOptionPane.ERROR_MESSAGE
                    )
                }
            }

            3 -> {
                val yValue = (aValue as? String)?.toDoubleOrNull()
                if (yValue != null) {
                    spaceMarine.coordinates?.y = yValue
                } else {
                    JOptionPane.showMessageDialog(
                        null,
                        UserApplication.resourceBundle.getString("invalidFormat"),
                        UserApplication.resourceBundle.getString("error"),
                        JOptionPane.ERROR_MESSAGE
                    )
                }
            }

            4 -> {
                val healthValue = (aValue as? String)?.toDoubleOrNull()
                if (healthValue != null) {
                    spaceMarine.health = healthValue
                } else {
                    JOptionPane.showMessageDialog(
                        null,
                        UserApplication.resourceBundle.getString("invalidFormat"),
                        UserApplication.resourceBundle.getString("error"),
                        JOptionPane.ERROR_MESSAGE
                    )
                }
            }

            5 -> {
                val heightValue = (aValue as? String)?.toIntOrNull()
                if (heightValue != null) {
                    spaceMarine.height = heightValue
                } else {
                    JOptionPane.showMessageDialog(
                        null,
                        UserApplication.resourceBundle.getString("invalidFormat"),
                        UserApplication.resourceBundle.getString("error"),
                        JOptionPane.ERROR_MESSAGE
                    )
                }
            }

            6 -> spaceMarine.category = (aValue as? AstartesCategory) ?: AstartesCategory.SCOUT
            7 -> spaceMarine.meleeWeapon = (aValue as? MeleeWeapon) ?: MeleeWeapon.CHAIN_SWORD
            8 -> spaceMarine.chapter?.name = (aValue as? String) ?: ""
            9 -> spaceMarine.chapter?.parentLegion = (aValue as? String) ?: ""
            10 -> {
                val marineCountValue = (aValue as? String)?.toIntOrNull()
                if (marineCountValue != null) {
                    spaceMarine.chapter?.marineCount = marineCountValue
                } else {
                    JOptionPane.showMessageDialog(
                        null,
                        UserApplication.resourceBundle.getString("invalidFormat"),
                        UserApplication.resourceBundle.getString("error"),
                        JOptionPane.ERROR_MESSAGE
                    )
                }
            }

            11 -> spaceMarine.chapter?.world = (aValue as? String) ?: ""
        }

        if (spaceMarine.creator != Client.id) {
            JOptionPane.showMessageDialog(
                null,
                UserApplication.resourceBundle.getString("invalidOwner"),
                UserApplication.resourceBundle.getString("error"),
                JOptionPane.ERROR_MESSAGE
            )
        } else {
            Client.command.spaceMarine = spaceMarine
            Client.command.commandArgument = spaceMarine.id.toString()
            Client.command.name = "update"
        }
    }

    override fun getColumnClass(columnIndex: Int): Class<*> {
        return when (columnIndex) {
            6 -> AstartesCategory::class.java
            7 -> MeleeWeapon::class.java
            else -> Any::class.java
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
        try {
        rowCount = 0

        data.forEach { addRow(toRow(it)) }

        fireTableStructureChanged()
        fireTableDataChanged()
        } catch (_: ArrayIndexOutOfBoundsException) {

        }
    }
}
