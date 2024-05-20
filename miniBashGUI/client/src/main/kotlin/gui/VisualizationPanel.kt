package com.net0pyr.gui

import com.net0pyr.Client
import com.net0pyr.entity.SpaceMarine
import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JOptionPane
import javax.swing.JPanel

class VisualizationPanel(private val objects: List<SpaceMarine>) : JPanel() {
    private var selectedObject: SpaceMarine? = null


    init {
        preferredSize = Dimension(400, 400)
        background = Color.WHITE
        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                objects.forEach {
                    if (isPointInsideObject(e.point, it)) {
                        selectedObject = it
                        JOptionPane.showMessageDialog(this@VisualizationPanel, "Selected Object: $it")
                    }
                }
            }
        })
    }

    private fun isPointInsideObject(point: Point, spaceMarine: SpaceMarine): Boolean {
        val rect = Rectangle(
            spaceMarine.coordinates!!.x!!.toInt(),
            spaceMarine.coordinates!!.y!!.toInt(),
            spaceMarine.height!!,
            spaceMarine.health!!.toInt()
        )
        return rect.contains(point)
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        objects.forEach {
            g.color = if (it.creator == Client.id) Color.BLUE else Color.RED
            g.fillRect(it.coordinates!!.x!!.toInt(),
                it.coordinates!!.y!!.toInt(),
                it.height!!,
                it.health!!.toInt()
            )
        }
    }
}