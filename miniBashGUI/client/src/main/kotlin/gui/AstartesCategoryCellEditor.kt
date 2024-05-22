package com.net0pyr.gui

import com.net0pyr.enums.AstartesCategory
import java.awt.Component
import javax.swing.DefaultCellEditor
import javax.swing.JComboBox
import javax.swing.JTable
import javax.swing.JTextField
import javax.swing.table.TableCellEditor

class AstartesCategoryCellEditor(enumValues: Array<AstartesCategory>) : DefaultCellEditor(JTextField()),
    TableCellEditor {
    private val comboBox = JComboBox<AstartesCategory>(enumValues)

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
