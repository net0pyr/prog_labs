package com.net0pyr.toolsForChanging

import com.net0pyr.army.Chapter
import com.net0pyr.entity.SpaceMarine
import com.net0pyr.enums.AstartesCategory
import com.net0pyr.enums.MeleeWeapon
import com.net0pyr.location.Coordinates
import kotlin.properties.Delegates

class ObjectFromList {

    fun getSpaceMarine(mutableList: MutableList<String>): SpaceMarine {
        lateinit var name: String
        var health by Delegates.notNull<Double>()
        var height by Delegates.notNull<Int>()
        var coordinateX by Delegates.notNull<Float>()
        var coordinateY by Delegates.notNull<Double>()
        lateinit var category: String
        lateinit var meleeWeapon: String
        lateinit var chapterName: String
        lateinit var LegionName: String
        var marinesCount by Delegates.notNull<Int>()
        lateinit var world: String
        mutableList.forEach { line ->
            var field = StringBuilder()
            var argument = StringBuilder()
            var startFlag = false
            var finishFlag = false
            var argumentFlag = false
            line.forEach {
                if (!startFlag && it != ' ') {
                    startFlag = true
                }
                if (finishFlag && it != ' ') {
                    argumentFlag = true
                }
                if (startFlag && it == ':') {
                    finishFlag = true
                }
                if (!finishFlag && startFlag) {
                    field.append(it)
                }
                if (argumentFlag) {
                    argument.append(it)
                }
            }
            var stringField = field.toString()
            var stringArgument = argument.toString()
            if (stringField == "Имя")
                name = stringArgument
            if (stringField == "Здоровье")
                health = stringArgument.toDouble()
            if (stringField == "Рост")
                height = stringArgument.toInt()
            if (stringField == "x")
                coordinateX = stringArgument.toFloat()
            if (stringField == "y")
                coordinateY = stringArgument.toDouble()
            if (stringField == "Категория война")
                category = stringArgument
            if (stringField == "Вид оружия")
                meleeWeapon = stringArgument
            if (stringField == "Название")
                chapterName = stringArgument
            if (stringField == "Легион")
                LegionName = stringArgument
            if (stringField == "Количество десантников")
                marinesCount = stringArgument.toInt()
            if (stringField == "Название мира")
                world = stringArgument

        }
        val coordinates = Coordinates(coordinateX, coordinateY)
        val chapter = Chapter(chapterName, LegionName, marinesCount, world)

        var astartesCategory: AstartesCategory? = null
        if (category.equals("Scout", ignoreCase = true) || category == "1")
            astartesCategory = AstartesCategory.SCOUT
        if (category.equals("Chaplain", ignoreCase = true) || category == "2")
            astartesCategory = AstartesCategory.CHAPLAIN
        if (category.equals("Helix", ignoreCase = true) || category == "3")
            astartesCategory = AstartesCategory.HELIX

        var meleeWeapons: MeleeWeapon? = null
        if (meleeWeapon.equals("CHAIN SWORD", ignoreCase = true) || meleeWeapon == "1")
            meleeWeapons = MeleeWeapon.CHAIN_SWORD
        if (meleeWeapon.equals("CHAIN AXE", ignoreCase = true) || meleeWeapon == "2")
            meleeWeapons = MeleeWeapon.CHAIN_AXE
        if (meleeWeapon.equals("MANREAPER", ignoreCase = true) || meleeWeapon == "3")
            meleeWeapons = MeleeWeapon.MANREAPER
        if (meleeWeapon.equals("POWER BLADE", ignoreCase = true) || meleeWeapon == "4")
            meleeWeapons = MeleeWeapon.POWER_BLADE
        if (meleeWeapon.equals("POWER SWORD", ignoreCase = true) || meleeWeapon == "5")
            meleeWeapons = MeleeWeapon.POWER_SWORD
        val spaceMarine = SpaceMarine(name, coordinates, health, height, astartesCategory, meleeWeapons, chapter)
        return (spaceMarine)
    }

    fun getChapter(mutableList: MutableList<String>): Chapter {
        lateinit var chapterName: String
        lateinit var LegionName: String
        var marinesCount by Delegates.notNull<Int>()
        lateinit var world: String
        mutableList.forEach { line ->
            var field = StringBuilder()
            var argument = StringBuilder()
            var startFlag = false
            var finishFlag = false
            var argumentFlag = false
            line.forEach {
                if (!startFlag && it != ' ') {
                    startFlag = true
                }
                if (finishFlag && it != ' ') {
                    argumentFlag = true
                }
                if (startFlag && it == ':') {
                    finishFlag = true
                }
                if (!finishFlag && startFlag) {
                    field.append(it)
                }
                if (argumentFlag) {
                    argument.append(it)
                }
            }
            var stringField = field.toString()
            var stringArgument = argument.toString()

            if (stringField == "Название")
                chapterName = stringArgument
            if (stringField == "Легион")
                LegionName = stringArgument
            if (stringField == "Количество десантников")
                marinesCount = stringArgument.toInt()
            if (stringField == "Название мира")
                world = stringArgument

        }
        val chapter = Chapter(chapterName, LegionName, marinesCount, world)

        return (chapter)
    }
}