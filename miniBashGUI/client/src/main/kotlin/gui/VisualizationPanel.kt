package com.net0pyr.gui

import com.net0pyr.Client
import com.net0pyr.entity.SpaceMarine
import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JOptionPane
import javax.swing.JPanel
import javax.swing.Timer

class VisualizationPanel(private val spaceMarines: List<SpaceMarine>) : JPanel() {
    private var selectedObject: SpaceMarine? = null
    private var animationTimer: Timer? = null
    private var startTime: Long = 0
    private var animationDuration: Long = 1000

    init {
        preferredSize = Dimension(400, 1000)
        background = Color.WHITE
        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                spaceMarines.forEach {
                    if (isPointInsideObject(e.point, it)) {
                        selectedObject = it
                        JOptionPane.showMessageDialog(this@VisualizationPanel, "${UserApplication.resourceBundle.getString("Selected_Object")}: $it")
                    }
                }
            }
        })
    }

    private fun isPointInsideObject(point: Point, spaceMarine: SpaceMarine): Boolean {
        val rect = Rectangle(
            spaceMarine.coordinates!!.x!!.toInt(),
            spaceMarine.coordinates!!.y!!.toInt(),
            spaceMarine.health!!.toInt(),
            spaceMarine.health!!.toInt()
        )
        return rect.contains(point)
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        spaceMarines.forEach {
            if (animationTimer != null && animationTimer!!.isRunning) {
                val elapsedTime = System.currentTimeMillis() - startTime
                val progress = elapsedTime.toDouble() / animationDuration
                val alpha = (Math.sin(progress * Math.PI) / 2.0 + 0.5).toFloat()
                g.color = Color(
                    (255 * alpha).toInt(),
                    (255 * alpha).toInt(),
                    (255 * alpha).toInt()
                )
                g.fillRect(
                    it.coordinates!!.x!!.toInt(),
                    it.coordinates!!.y!!.toInt(),
                    it.health!!.toInt(),
                    it.health!!.toInt()
                )
            } else {
                g.color = if (it.creator == Client.id) Color.BLUE else Color.RED
                g.fillRect(
                    it.coordinates!!.x!!.toInt(),
                    it.coordinates!!.y!!.toInt(),
                    it.health!!.toInt(),
                    it.health!!.toInt()
                )
            }
        }
    }

    fun startStopAnimation() {
        if (animationTimer == null || !animationTimer!!.isRunning) {
            animationTimer = Timer(1, null)
            startTime = System.currentTimeMillis()
            animationTimer!!.addActionListener {
                val elapsedTime = System.currentTimeMillis() - startTime
                if (elapsedTime >= animationDuration) {
                    animationTimer!!.stop()
                }
                repaint()
            }
            animationTimer!!.start()
        } else {
            animationTimer!!.stop()
        }
    }
}
