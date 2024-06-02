package com.net0pyr.gui

import java.awt.BorderLayout
import javax.swing.*

class Login {
    companion object {
        var password = ""
        var username = ""
        var loginFlag = -1
        val frame = JFrame("User Authentication")
    }
    fun execute() {
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.setSize(300, 200)
        frame.layout = BorderLayout()

        val panel = JPanel()
        panel.layout = BoxLayout(panel, BoxLayout.Y_AXIS)

        val userLabel = JLabel("Username:")
        val userTextField = JTextField(20)
        panel.add(userLabel)
        panel.add(userTextField)

        val passwordLabel = JLabel("Password:")
        val passwordField = JPasswordField(20)
        panel.add(passwordLabel)
        panel.add(passwordField)

        val loginButton = JButton("Login")
        panel.add(loginButton)

        val registerButton = JButton("Registration")
        panel.add(registerButton)

        frame.add(panel, BorderLayout.CENTER)

        loginButton.addActionListener {
            username = userTextField.text
            password = String(passwordField.password)
            loginFlag = 1
        }

        registerButton.addActionListener {
            username = userTextField.text
            password = String(passwordField.password)
            loginFlag = 2
        }

        frame.isVisible = true
    }
}