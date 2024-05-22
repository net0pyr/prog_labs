package com.net0pyr.gui

import javax.swing.DefaultCellEditor
import javax.swing.DefaultListCellRenderer
import javax.swing.JComboBox

//class EnumCellEditor<T : Enum<T>>(enumClass: Class<T>) : DefaultCellEditor(JComboBox<T>(enumClass.enumConstants)) {
//    init {
//        val comboBox = component as JComboBox<T>
//        comboBox.renderer = DefaultListCellRenderer()
//    }
//}

class EnumCellEditor<E : Enum<E>>(enumValues: Array<E>) : DefaultCellEditor(JComboBox(enumValues)) {
    override fun getCellEditorValue(): Any {
        val selectedValue = super.getCellEditorValue() as? E
        return selectedValue?.name ?: ""
    }
}