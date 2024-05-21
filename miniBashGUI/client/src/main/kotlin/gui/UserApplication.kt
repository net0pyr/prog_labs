package com.net0pyr.gui


import com.net0pyr.Client
import com.net0pyr.army.Chapter
import com.net0pyr.entity.SpaceMarine
import com.net0pyr.enums.AstartesCategory
import com.net0pyr.enums.MeleeWeapon
import com.net0pyr.location.Coordinates
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.GridLayout
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.util.*
import javax.swing.*

class UserApplication : JFrame() {
    companion object {
        var tableModel: SpaceMarinesTable = SpaceMarinesTable()
        var table: JTable = JTable(tableModel)
        var visualizationPanel: VisualizationPanel = VisualizationPanel(SpaceMarinesTable.data)
        val languageSwitcher = JComboBox(arrayOf("English", "Русский"))
        var resourceBundle = ResourceBundle.getBundle("messages", Locale("en"))
    }
    init {
        Client.command.name = "show"
        Client.selector.select(50)
        title = "User Application"
        defaultCloseOperation = EXIT_ON_CLOSE
        size = Dimension(1000, 800)
        layout = BorderLayout()
        val infoPanel = JPanel().apply {
            layout = BoxLayout(this, BoxLayout.Y_AXIS)
            add(JLabel("User: current_user"))
            add(JLabel("Collection created: ${Date()}"))
            add(JLabel("Number of elements: ${SpaceMarinesTable.data.size}"))
            add(languageSwitcher)
        }

        val buttonsPanel = JPanel().apply {
            add(JButton(resourceBundle.getString("add")).apply {
                addActionListener {
                    val spaceMarine = showAddDialog()
                    if (spaceMarine != null) {
                        spaceMarine.creator = Client.id
                    }
                    Client.command.spaceMarine = spaceMarine
                    Client.command.name = "add"
                }
            })
            add(JButton("Command 2").apply { addActionListener { executeCommand("Command 2") } })
            add(JButton("Command 3").apply { addActionListener { executeCommand("Command 3") } })
            add(JButton("Command 4").apply { addActionListener { executeCommand("Command 4") } })
            add(JButton("Command 5").apply { addActionListener { executeCommand("Command 5") } })
        }

        // Add components to the main frame
        add(infoPanel, BorderLayout.NORTH)
        add(JScrollPane(table), BorderLayout.CENTER)
        add(visualizationPanel, BorderLayout.EAST)
        add(buttonsPanel, BorderLayout.SOUTH)

        // Language switching functionality
        languageSwitcher.addActionListener { switchLanguage(languageSwitcher.selectedItem as String) }

        // Enable sorting
        table.autoCreateRowSorter = true

        // Add row sorter listener for toggling sort order on column click
        table.tableHeader.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                val column = table.columnAtPoint(e.point)
                val sortKeys = table.rowSorter.sortKeys
                val currentSortKey = sortKeys.firstOrNull { it.column == column }
                val newSortOrder = if (currentSortKey?.sortOrder == SortOrder.ASCENDING) {
                    SortOrder.DESCENDING
                } else {
                    SortOrder.ASCENDING
                }
                table.rowSorter.toggleSortOrder(column)
                table.rowSorter.sortKeys = listOf(RowSorter.SortKey(column, newSortOrder))
            }
        })
        tableModel.refreshTable()
    }

    private fun executeCommand(command: String) {
        JOptionPane.showMessageDialog(this, "$command executed")
    }

    private fun initLocalization(locale: Locale) {
        resourceBundle = ResourceBundle.getBundle("messages", locale)

        for (i in 0 until table.columnCount) {
            val column = table.columnModel.getColumn(i)
            val columnName = resourceBundle.getString("column_${i + 1}")
            column.headerValue = columnName
        }
        table.tableHeader.repaint()
    }

    private fun switchLanguage(language: String) {
        when (language) {
            "English" -> initLocalization(Locale("en"))
            "Русский" -> initLocalization(Locale("ru"))
        }
    }


    private fun showAddDialog(): SpaceMarine? {
        val dialogPanel = JPanel(GridLayout(0, 2))


        val name = JTextField()
        val coordinatesX = JTextField()
        val coordinatesY = JTextField()
        val health = JTextField()
        val height = JTextField()
        val chapterName = JTextField()
        val parentLegion = JTextField()
        val marinesCount = JTextField()
        val world = JTextField()

        dialogPanel.add(JLabel("${resourceBundle.getString("column_2")}:"))
        dialogPanel.add(name)
        dialogPanel.add(JLabel("${resourceBundle.getString("column_3")}:"))
        dialogPanel.add(coordinatesX)
        dialogPanel.add(JLabel("${resourceBundle.getString("column_4")}:"))
        dialogPanel.add(coordinatesY)
        dialogPanel.add(JLabel("${resourceBundle.getString("column_5")}:"))
        dialogPanel.add(health)
        dialogPanel.add(JLabel("${resourceBundle.getString("column_6")}:"))
        dialogPanel.add(height)
        dialogPanel.add(JLabel("${resourceBundle.getString("column_9")}:"))
        dialogPanel.add(chapterName)
        dialogPanel.add(JLabel("${resourceBundle.getString("column_10")}:"))
        dialogPanel.add(parentLegion)
        dialogPanel.add(JLabel("${resourceBundle.getString("column_11")}:"))
        dialogPanel.add(marinesCount)
        dialogPanel.add(JLabel("${resourceBundle.getString("column_12")}:"))
        dialogPanel.add(world)

        val categoryComboBox =
            JComboBox(AstartesCategory.entries.toTypedArray()) // Создаем комбобокс для выбора значения enum
        dialogPanel.add(JLabel("${resourceBundle.getString("column_7")}:"))
        dialogPanel.add(categoryComboBox)
        // Добавьте остальные метки и поля для других полей SpaceMarine...

        val category = categoryComboBox.selectedItem as AstartesCategory

        val weaponComboBox = JComboBox(MeleeWeapon.entries.toTypedArray()) // Создаем комбобокс для выбора значения enum
        dialogPanel.add(JLabel("${resourceBundle.getString("column_8")}:"))
        dialogPanel.add(weaponComboBox)
        // Добавьте остальные метки и поля для других полей SpaceMarine...

        val weapon = weaponComboBox.selectedItem as MeleeWeapon

        val result = JOptionPane.showConfirmDialog(
            this,
            dialogPanel,
            resourceBundle.getString("add"),
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        )

        if (result == JOptionPane.OK_OPTION) {
            try {
                val nameR = name.text
                val coordinatesXR = coordinatesX.text.toFloat()
                val coordinatesYR = coordinatesY.text.toDouble()
                val healthR = health.text.toDouble()
                val heightR = height.text.toInt()
                val chapterNameR = chapterName.text
                val parentLegionR = parentLegion.text
                val marinesCountR = marinesCount.text.toInt()
                val worldR = world.text
                return SpaceMarine(
                    nameR,
                    Coordinates(coordinatesXR, coordinatesYR),
                    healthR,
                    heightR,
                    category,
                    weapon,
                    Chapter(chapterNameR, parentLegionR, marinesCountR, worldR)
                )
            } catch (e: NumberFormatException) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numbers.")
            }
        }

        return null
    }
}