package com.net0pyr.WorkingWithCommand

import com.net0pyr.army.Chapter
import com.net0pyr.entity.SpaceMarine
import com.net0pyr.enums.AstartesCategory
import com.net0pyr.enums.MeleeWeapon
import com.net0pyr.location.Coordinates
import java.util.*

class ReadNewObject {
    fun readNewMarine(): SpaceMarine? {
        var spaceMarine: SpaceMarine? = null
        try {
            val scanner = Scanner(System.`in`)
            print("Имя: ")
            val name = scanner.nextLine()
            print("Здоровье: ")
            val health = scanner.nextDouble()
            scanner.nextLine()
            print("Рост: ")
            val height = scanner.nextInt()
            scanner.nextLine()
            print("Координаты местонахождения:\n x: ")
            val coordinateX = scanner.nextFloat()
            scanner.nextLine()
            print(" y: ")
            val coordinateY = scanner.nextDouble()
            scanner.nextLine()
            print("Категория война(1 - Scout, 2 - Chaplain, 3 - Helix): ")
            val category = scanner.nextLine()
            print("Вид оружия(1 - CHAIN SWORD, 2 - CHAIN AXE, 3 - MANREAPER, 4 - POWER BLADE, 5 - POWER SWORD): ")
            val meleeWeapon = scanner.nextLine()
            print("Военная часть:\n Название: ")
            val chapterName = scanner.nextLine()
            print(" Легион: ")
            val legionName = scanner.nextLine()
            print(" Количество десантников: ")
            val marinesCount = scanner.nextInt()
            scanner.nextLine()
            print(" Название мира: ")
            val world = scanner.nextLine()

            val coordinates = Coordinates(coordinateX, coordinateY)
            val chapter = Chapter(chapterName, legionName, marinesCount, world)

            var astartesCategory: AstartesCategory? = null
            if (category.equals("Scout", ignoreCase = true) || category.equals("1"))
                astartesCategory = AstartesCategory.SCOUT
            if (category.equals("Chaplain", ignoreCase = true) || category.equals("2"))
                astartesCategory = AstartesCategory.CHAPLAIN
            if (category.equals("Helix", ignoreCase = true) || category.equals("3"))
                astartesCategory = AstartesCategory.HELIX

            var meleeWeapons: MeleeWeapon? = null
            if (meleeWeapon.equals("CHAIN SWORD", ignoreCase = true) || meleeWeapon.equals("1"))
                meleeWeapons = MeleeWeapon.CHAIN_SWORD
            if (meleeWeapon.equals("CHAIN AXE", ignoreCase = true) || meleeWeapon.equals("2"))
                meleeWeapons = MeleeWeapon.CHAIN_AXE
            if (meleeWeapon.equals("MANREAPER", ignoreCase = true) || meleeWeapon.equals("3"))
                meleeWeapons = MeleeWeapon.MANREAPER
            if (meleeWeapon.equals("POWER BLADE", ignoreCase = true) || meleeWeapon.equals("4"))
                meleeWeapons = MeleeWeapon.POWER_BLADE
            if (meleeWeapon.equals("POWER SWORD", ignoreCase = true) || meleeWeapon.equals("5"))
                meleeWeapons = MeleeWeapon.POWER_SWORD

            spaceMarine = SpaceMarine(name, coordinates, health, height, astartesCategory, meleeWeapons, chapter)
        } catch (e: InputMismatchException) {
            println("\u001B[31mОшибка:\u001B[0m Неверный формат ввода")
        }
        return spaceMarine
    }
    fun readNewChapter(): Chapter? {
        var chapter: Chapter? = null
        try {
            val scanner = Scanner(System.`in`)
            print("Название военной части: ")
            val chapterName = scanner.nextLine()
            print("Легион: ")
            val LegionName = scanner.nextLine()
            print("Количество десантников: ")
            val marinesCount = scanner.nextInt()
            scanner.nextLine()
            print("Название мира: ")
            val world = scanner.nextLine()

            chapter = Chapter(chapterName, LegionName, marinesCount, world)
        } catch (e: InputMismatchException) {
            println("\u001B[31mОшибка:\u001B[0m Неверный формат ввода")
        }
        return chapter
    }
}