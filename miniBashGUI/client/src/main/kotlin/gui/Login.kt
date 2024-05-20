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

        // Создание панели для размещения компонентов
        val panel = JPanel()
        panel.layout = BoxLayout(panel, BoxLayout.Y_AXIS)

        // Добавление метки и текстового поля для имени пользователя
        val userLabel = JLabel("Username:")
        val userTextField = JTextField(20)
        panel.add(userLabel)
        panel.add(userTextField)

        // Добавление метки и поля для ввода пароля
        val passwordLabel = JLabel("Password:")
        val passwordField = JPasswordField(20)
        panel.add(passwordLabel)
        panel.add(passwordField)

        // Создание кнопки для отправки
        val loginButton = JButton("Login")
        panel.add(loginButton)

        val registerButton = JButton("Registration")
        panel.add(registerButton)

        // Добавление панели в основное окно
        frame.add(panel, BorderLayout.CENTER)



        // Обработка события нажатия кнопки
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

        // Отображение окна
        frame.isVisible = true
    }
}