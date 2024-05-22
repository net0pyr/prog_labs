package com.net0pyr.gui

import javax.swing.DefaultCellEditor
import javax.swing.JTextField
import javax.swing.text.AttributeSet
import javax.swing.text.PlainDocument

class NumericCellEditor : DefaultCellEditor(JTextField()) {
    init {
        val textField = component as JTextField
        textField.document = NumericDocument()
    }
    private class NumericDocument : PlainDocument() {
        override fun insertString(offs: Int, str: String?, a: AttributeSet?) {
            if (str == null) return
            if (str.matches(Regex("\\d*"))) {
                super.insertString(offs, str, a)
            }
        }
    }
}
