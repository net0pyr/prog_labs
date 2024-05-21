package com.net0pyr.gui

import javax.swing.DefaultCellEditor
import javax.swing.JComboBox

class EnumCellEditor<E : Enum<E>>(enumValues: Array<E>) : DefaultCellEditor(JComboBox(enumValues)) {
    override fun getCellEditorValue(): Any {
        val selectedValue = super.getCellEditorValue() as? E
        return selectedValue?.name ?: ""
    }
}