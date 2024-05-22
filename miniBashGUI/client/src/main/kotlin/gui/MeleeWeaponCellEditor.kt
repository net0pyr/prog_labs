package com.net0pyr.gui

import com.net0pyr.enums.AstartesCategory
import com.net0pyr.enums.MeleeWeapon
import java.awt.Component
import javax.swing.DefaultCellEditor
import javax.swing.JComboBox
import javax.swing.JTable
import javax.swing.JTextField
import javax.swing.table.TableCellEditor

class MeleeWeaponCellEditor(enumValues: Array<MeleeWeapon>) : DefaultCellEditor(JTextField()),
    TableCellEditor {
    private val comboBox = JComboBox<MeleeWeapon>(enumValues)

    init {
        comboBox.isEditable = true
    }

    override fun getTableCellEditorComponent(table: JTable?, value: Any?, isSelected: Boolean, row: Int, column: Int): Component {
        val editor = super.getTableCellEditorComponent(table, value, isSelected, row, column)
        comboBox.selectedItem = value
        return comboBox
    }

    override fun getCellEditorValue(): Any {
        return comboBox.selectedItem
    }
}
