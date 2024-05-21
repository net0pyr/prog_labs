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
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.util.*
import javax.swing.*
import javax.swing.RowFilter
import javax.swing.table.TableRowSorter

class UserApplication : JFrame() {
    companion object {
        var tableModel: SpaceMarinesTable = SpaceMarinesTable()
        var table: JTable = JTable(tableModel)
        var visualizationPanel: VisualizationPanel = VisualizationPanel(SpaceMarinesTable.data)
        val languageSwitcher = JComboBox(arrayOf("English", "Русский"))
        var resourceBundle = ResourceBundle.getBundle("messages", Locale("en"))
    }

    private val sorter = TableRowSorter(tableModel)
    private val filterPanel = JPanel(GridLayout(1, tableModel.columnCount))

    init {
        Client.command.name = "show"
        Client.selector.select(50)
        title = "User Application"
        defaultCloseOperation = EXIT_ON_CLOSE
        size = Dimension(1000, 800)
        layout = BorderLayout()

        // Панель информации
        // Создаем панель для размещения элементов управления
        val controlPanel = JPanel().apply {
            layout = BoxLayout(this, BoxLayout.X_AXIS)
            add(JLabel("User: current_user"))
            add(Box.createHorizontalStrut(10))
            add(JLabel("Collection created: ${Date()}"))
            add(Box.createHorizontalStrut(10))
            add(JLabel("Number of elements: ${SpaceMarinesTable.data.size}"))
            add(Box.createHorizontalGlue())
            add(languageSwitcher)
        }

        // Добавляем панель управления в верхнюю часть окна
        add(controlPanel, BorderLayout.NORTH)

        // Панель с кнопками
        val buttonsPanel = JPanel().apply {
            add(JButton(resourceBundle.getString("add")).apply {
                addActionListener {
                    val spaceMarine = showAddDialog()
                    if (spaceMarine != null) {
                        spaceMarine.creator = Client.id
                        tableModel.addObject(spaceMarine) // Добавляем объект в модель таблицы
                    }
                    Client.command.spaceMarine = spaceMarine
                    Client.command.name = "add"
                }
            })
            add(JButton(resourceBundle.getString("refresh")).apply {
                addActionListener {
                    Client.command.name = "show"
                    tableModel.refreshTable() // Обновляем таблицу после команды "show"
                }
            })
            add(JButton("Command 3").apply { addActionListener { executeCommand("Command 3") } })
            add(JButton("Command 4").apply { addActionListener { executeCommand("Command 4") } })
            add(JButton("Command 5").apply { addActionListener { executeCommand("Command 5") } })
        }


        // Панель фильтров
        val filters = mutableListOf<JComponent>()

        for (i in 0 until tableModel.columnCount) {
            val columnClass = tableModel.getColumnClass(i)
            val filterComponent: JComponent = when {
                columnClass == AstartesCategory::class.java -> {
                    val comboBox = JComboBox(AstartesCategory.entries.toTypedArray())
                    comboBox.addActionListener {
                        applyFilters()
                    }
                    comboBox
                }
                columnClass == MeleeWeapon::class.java -> {
                    val comboBox = JComboBox(MeleeWeapon.entries.toTypedArray())
                    comboBox.addActionListener {
                        applyFilters()
                    }
                    comboBox
                }
                else -> {
                    val textField = JTextField()
                    textField.addKeyListener(object : KeyAdapter() {
                        override fun keyReleased(e: KeyEvent) {
                            applyFilters()
                        }
                    })
                    textField
                }
            }
            filterPanel.add(filterComponent)
            filters.add(filterComponent)
        }

        // Создаем панель для таблицы и фильтров
        val tablePanel = JPanel().apply {
            layout = BorderLayout()

            // Создаем панель для таблицы и фильтров
            val filterPanel = JPanel().apply {
                layout = BorderLayout()
                add(JScrollPane(table), BorderLayout.CENTER)
                // Добавляем фильтры в нижнюю часть панели
                add(createFiltersPanel(filters), BorderLayout.SOUTH)
            }

            // Добавляем панель таблицы и фильтров в левую часть панели
            add(filterPanel, BorderLayout.WEST)

            // Добавляем визуализацию в правую часть панели
            add(visualizationPanel, BorderLayout.CENTER)
        }

        add(tablePanel, BorderLayout.CENTER)
        // Добавляем компоненты на главный фрейм

        //add(JScrollPane(table), BorderLayout.CENTER)
        //add(visualizationPanel, BorderLayout.EAST)
        add(buttonsPanel, BorderLayout.SOUTH)
        //add(filterPanel, BorderLayout.NORTH)

        // Настраиваем переключатель языка
        languageSwitcher.addActionListener { switchLanguage(languageSwitcher.selectedItem as String) }

        // Включаем сортировку
        table.rowSorter = sorter

        // Обновляем таблицу
//        tableModel.refreshTable()
    }

    // Метод создания панели с фильтрами
    private fun createFiltersPanel(filters: List<JComponent>): JPanel {
        val filterPanel = JPanel(GridLayout(1, table.columnCount)).apply {
            preferredSize = Dimension(800, 50) // Задаем размер панели
        }

        filters.forEach { filter ->
            filterPanel.add(filter) // Добавляем каждый компонент фильтра на панель
        }

        return filterPanel
    }


    private fun applyFilters() {
        val filters = mutableListOf<RowFilter<Any, Any>>()

        for (i in 0 until tableModel.columnCount) {
            val filterComponent = filterPanel.getComponent(i)
            if (filterComponent is JTextField) {
                val text = filterComponent.text
                if (text.isNotEmpty()) {
                    filters.add(RowFilter.regexFilter("(?i)$text", i))
                }
            } else if (filterComponent is JComboBox<*>) {
                val selectedItem = filterComponent.selectedItem
                if (selectedItem != null) {
                    filters.add(RowFilter.regexFilter("^${selectedItem.toString()}$", i))
                }
            }
        }

        sorter.rowFilter = if (filters.isEmpty()) null else RowFilter.andFilter(filters)
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

        val categoryComboBox = JComboBox(AstartesCategory.entries.toTypedArray())
        dialogPanel.add(JLabel("${resourceBundle.getString("column_7")}:"))
        dialogPanel.add(categoryComboBox)

        val weaponComboBox = JComboBox(MeleeWeapon.entries.toTypedArray())
        dialogPanel.add(JLabel("${resourceBundle.getString("column_8")}:"))
        dialogPanel.add(weaponComboBox)

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
                val category = categoryComboBox.selectedItem as AstartesCategory
                val weapon = weaponComboBox.selectedItem as MeleeWeapon

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
