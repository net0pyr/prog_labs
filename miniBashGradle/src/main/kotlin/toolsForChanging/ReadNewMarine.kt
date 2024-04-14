package com.net0pyr.toolsForChanging

import com.net0pyr.army.Chapter
import com.net0pyr.entity.SpaceMarine
import com.net0pyr.entity.SpaceMarineInTreeSet
import com.net0pyr.enums.AstartesCategory
import com.net0pyr.enums.MeleeWeapon
import com.net0pyr.location.Coordinates
import java.util.*

class ReadNewMarine {
    fun readNewMarine(): SpaceMarine? {
        var spaceMarine: SpaceMarine? = null
        try {
            var scanner = Scanner(System.`in`)
            print("Имя: ")
            var name = scanner.nextLine()
            print("Здоровье: ")
            var health = scanner.nextDouble()
            scanner.nextLine()
            print("Рост: ")
            var height = scanner.nextInt()
            scanner.nextLine()
            print("Координаты местонахождения:\n x: ")
            var coordinateX = scanner.nextFloat()
            scanner.nextLine()
            print(" y: ")
            var coordinateY = scanner.nextDouble()
            scanner.nextLine()
            print("Категория война(1 - Scout, 2 - Chaplain, 3 - Helix): ")
            var category = scanner.nextLine()
            print("Вид оружия(1 - CHAIN SWORD, 2 - CHAIN AXE, 3 - MANREAPER, 4 - POWER BLADE, 5 - POWER SWORD): ")
            var meleeWeapon = scanner.nextLine()
            print("Военная часть:\n Название: ")
            var chapterName = scanner.nextLine()
            print(" Легион: ")
            var LegionName = scanner.nextLine()
            print(" Количество десантников: ")
            var marinesCount = scanner.nextInt()
            scanner.nextLine()
            print(" Название мира: ")
            var world = scanner.nextLine()

            val coordinates = Coordinates(coordinateX, coordinateY)
            val chapter = Chapter(chapterName, LegionName, marinesCount, world)

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
            SpaceMarineInTreeSet.spaceMarines.add(spaceMarine)
        } catch (e: InputMismatchException) {
            println("\u001B[31mОшибка:\u001B[0m Неверный формат ввода")
        }
        return spaceMarine
    }
}