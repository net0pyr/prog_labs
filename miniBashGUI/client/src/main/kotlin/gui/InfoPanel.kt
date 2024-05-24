package com.net0pyr.gui

import com.net0pyr.Client
import java.util.*
import javax.swing.*

class InfoPanel() {
    var controlPanel = JPanel()
    fun updateControlPanel() {
        SwingUtilities.invokeLater {
            controlPanel.removeAll()
            controlPanel.layout = BoxLayout(controlPanel, BoxLayout.X_AXIS)
            controlPanel.add(JLabel("${UserApplication.resourceBundle.getString("user")}: ${Client.login}"))
            controlPanel.add(Box.createHorizontalStrut(10))
            controlPanel.add(JLabel("${UserApplication.resourceBundle.getString("collectionData")}: ${Date()}"))
            controlPanel.add(Box.createHorizontalStrut(10))
            controlPanel.add(JLabel("${UserApplication.resourceBundle.getString("count")}: ${SpaceMarinesTable.data.size}"))
            controlPanel.add(Box.createHorizontalGlue())
            controlPanel.add(UserApplication.languageSwitcher)
            controlPanel.revalidate()
            controlPanel.repaint()
        }
    }
}