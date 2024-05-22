package com.net0pyr

import com.net0pyr.army.Chapter
import com.net0pyr.entity.SpaceMarine
import com.net0pyr.enums.AstartesCategory
import com.net0pyr.enums.MeleeWeapon
import com.net0pyr.location.Coordinates
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.nio.channels.ClosedChannelException
import java.util.*
import java.util.stream.Stream

fun main() {
    Scanner(System.`in`).use { scanner ->
        val client = Client()
        var connectionFlag = false
        while (!connectionFlag) {
            try {
                client.start(scanner)
                connectionFlag = true
            } catch (_: ClosedChannelException) {
            }
        }
        scanner.close()
    }
}


//import javax.swing.*
//import javax.swing.table.*
//import java.awt.*
//import java.awt.event.*
//import java.util.*
//
//data class UserObject(
//    val id: Int,
//    var name: String,
//    var x: Int,
//    var y: Int,
//    var size: Int,
//    var owner: String,
//    var field1: String,
//    var field2: String,
//    var field3: String,
//    var field4: String
//)
//
//class UserObjectTableModel(objects: List<UserObject>) : DefaultTableModel() {
//    private val columnNames = arrayOf(
//        "ID", "Name", "X", "Y", "Size", "Owner",
//        "Field1", "Field2", "Field3", "Field4"
//    )
//    private val data: MutableList<UserObject> = objects.toMutableList()
//
//    init {
//        columnNames.forEach { addColumn(it) }
//        data.forEach { addRow(toRow(it)) }
//    }
//
//    private fun toRow(obj: UserObject): Vector<Any> = Vector(
//        arrayOf(
//            obj.id, obj.name, obj.x, obj.y, obj.size, obj.owner,
//            obj.field1, obj.field2, obj.field3, obj.field4
//        ).toList()
//    )
//
//    override fun isCellEditable(row: Int, column: Int): Boolean {
//        return column != 0 // ID не редактируемый
//    }
//
//    override fun setValueAt(aValue: Any?, row: Int, column: Int) {
//        super.setValueAt(aValue, row, column)
//        val obj = data[row]
//        when (column) {
//            1 -> obj.name = aValue as String
//            2 -> obj.x = (aValue as String).toInt()
//            3 -> obj.y = (aValue as String).toInt()
//            4 -> obj.size = (aValue as String).toInt()
//            5 -> obj.owner = aValue as String
//            6 -> obj.field1 = aValue as String
//            7 -> obj.field2 = aValue as String
//            8 -> obj.field3 = aValue as String
//            9 -> obj.field4 = aValue as String
//        }
//    }
//
//    fun addObject(obj: UserObject) {
//        data.add(obj)
//        addRow(toRow(obj))
//    }
//
//    fun removeObject(row: Int) {
//        data.removeAt(row)
//        removeRow(row)
//    }
//
//    fun updateObject(row: Int, obj: UserObject) {
//        data[row] = obj
//        for (i in 0 until columnCount) {
//            setValueAt(toRow(obj)[i], row, i)
//        }
//    }
//
//    fun getObjects(): List<UserObject> = data.toList()
//
//    fun setFilteredData(filteredObjects: List<UserObject>) {
//        val vectorData = filteredObjects.map { toRow(it) }.toCollection(Vector())
//        val vectorColumns = columnNames.toCollection(Vector())
//        setDataVector(vectorData, vectorColumns)
//    }
//}
//
//class VisualizationPanel(private val objects: List<UserObject>) : JPanel() {
//    private var selectedObject: UserObject? = null
//
//    init {
//        preferredSize = Dimension(400, 400)
//        background = Color.WHITE
//        addMouseListener(object : MouseAdapter() {
//            override fun mouseClicked(e: MouseEvent) {
//                objects.forEach {
//                    if (isPointInsideObject(e.point, it)) {
//                        selectedObject = it
//                        JOptionPane.showMessageDialog(this@VisualizationPanel, "Selected Object: $it")
//                    }
//                }
//            }
//        })
//    }
//
//    private fun isPointInsideObject(point: Point, obj: UserObject): Boolean {
//        val rect = Rectangle(obj.x, obj.y, obj.size, obj.size)
//        return rect.contains(point)
//    }
//
//    override fun paintComponent(g: Graphics) {
//        super.paintComponent(g)
//        objects.forEach {
//            g.color = if (it.owner == "current_user") Color.BLUE else Color.RED
//            g.fillRect(it.x, it.y, it.size, it.size)
//        }
//    }
//}
//
//class UserApplication : JFrame() {
//    private val objects = mutableListOf(
//        UserObject(1, "Object1", 50, 50, 50, "user1", "Field1", "Field2", "Field3", "Field4"),
//        UserObject(2, "Object2", 150, 100, 70, "user2", "Field1", "Field2", "Field3", "Field4")
//    )
//    private val tableModel = UserObjectTableModel(objects)
//    private val table = JTable(tableModel)
//    private val visualizationPanel = VisualizationPanel(objects)
//    private val languageSwitcher = JComboBox(arrayOf("English", "Русский"))
//
//    init {
//        title = "User Application"
//        defaultCloseOperation = EXIT_ON_CLOSE
//        size = Dimension(800, 600)
//        layout = BorderLayout()
//
//        // Information panel
//        val infoPanel = JPanel().apply {
//            layout = BoxLayout(this, BoxLayout.Y_AXIS)
//            add(JLabel("User: current_user"))
//            add(JLabel("Collection created: ${Date()}"))
//            add(JLabel("Number of elements: ${objects.size}"))
//            add(languageSwitcher)
//        }
//
//        // Buttons panel
//        val buttonsPanel = JPanel().apply {
//            layout = GridLayout(1, 5)
//            add(JButton("Command 1").apply { addActionListener { executeCommand("Command 1") } })
//            add(JButton("Command 2").apply { addActionListener { executeCommand("Command 2") } })
//            add(JButton("Command 3").apply { addActionListener { executeCommand("Command 3") } })
//            add(JButton("Command 4").apply { addActionListener { executeCommand("Command 4") } })
//            add(JButton("Command 5").apply { addActionListener { executeCommand("Command 5") } })
//        }
//
//        // Add components to the main frame
//        add(infoPanel, BorderLayout.NORTH)
//        add(JScrollPane(table), BorderLayout.WEST)
//        add(visualizationPanel, BorderLayout.CENTER)
//        add(buttonsPanel, BorderLayout.SOUTH)
//
//        // Language switching functionality
//        languageSwitcher.addActionListener { switchLanguage(languageSwitcher.selectedItem as String) }
//
//        // Enable sorting
//        table.autoCreateRowSorter = true
//
//        // Add row sorter listener for toggling sort order on column click
//        table.tableHeader.addMouseListener(object : MouseAdapter() {
//            override fun mouseClicked(e: MouseEvent) {
//                val column = table.columnAtPoint(e.point)
//                val sortKeys = table.rowSorter.sortKeys
//                val currentSortKey = sortKeys.firstOrNull { it.column == column }
//                val newSortOrder = if (currentSortKey?.sortOrder == SortOrder.ASCENDING) {
//                    SortOrder.DESCENDING
//                } else {
//                    SortOrder.ASCENDING
//                }
//                table.rowSorter.toggleSortOrder(column)
//                table.rowSorter.sortKeys = listOf(RowSorter.SortKey(column, newSortOrder))
//            }
//        })
//    }
//
//    private fun executeCommand(command: String) {
//        JOptionPane.showMessageDialog(this, "$command executed")
//    }
//
//    private fun switchLanguage(language: String) {
//        when (language) {
//            "English" -> {
//                // Switch to English
//                setTitle("User Application")
//                // Update other labels and components
//            }
//            "Русский" -> {
//                // Switch to Russian
//                setTitle("Приложение пользователя")
//                // Update other labels and components
//            }
//        }
//    }
//}
//
//fun main() {
//    SwingUtilities.invokeLater {
//        UserApplication().isVisible = true
//    }
//}

