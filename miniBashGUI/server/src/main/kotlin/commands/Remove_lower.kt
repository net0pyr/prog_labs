package com.net0pyr.commands

import com.net0pyr.Server
import com.net0pyr.army.Chapter
import com.net0pyr.entity.SpaceMarine
import com.net0pyr.entity.SpaceMarineInTreeSet
import java.util.*


/**
 * Класс команды remove_lower
 * @author net0pyr
 */
class Remove_lower : CommandExample() {
    /**Поле описания команды*/
    override val commandDescription = "remove_lower - удалить из коллекции все элементы, меньшие, чем заданный"

    /** Метод исполнения команды
     * @param commandArgument аргумент команды
     */
    override fun <T> commandExecution(
        commandArgument: T,
        spaceMarine: SpaceMarine?,
        chapter: Chapter?,
        id: Int
    ): String? {
        if (commandArgument != null && commandArgument != "") {
            return "\u001B[31mКоманда remove_lower не имеет таких аргуметов.\u001B[0m\nВоспользуйтесь командой help, чтобы получить дополнительную информацию"
        } else {
            try {
                if (spaceMarine != null) {
                    val spaceMarineComparator = Comparator<SpaceMarine> { sm1, sm2 ->
                        when {
                            sm1.health == null && sm2.health != null -> -1
                            sm1.health != null && sm2.health == null -> 1
                            sm1.health != null && sm2.health != null -> {
                                val healthComparison = sm1.health!!.compareTo(sm2.health!!)
                                if (healthComparison != 0) {
                                    healthComparison
                                } else {
                                    sm1.height?.compareTo(sm2.height ?: 0) ?: 0
                                }
                            }

                            else -> 0
                        }
                    }
                    SpaceMarineInTreeSet.spaceMarines.forEach {
                        if (spaceMarineComparator.compare(it, spaceMarine) < 0 &&
                                Server.dataBase.getCreatorSpaceMarine(it.id) == id) {
                            SpaceMarineInTreeSet.spaceMarines.remove(it)
                            Server.dataBase.deleteSpaceMarine(it)
                        }
                    }
                }
                return "Удаление Ваших десантников успешно завершено"
            } catch (e: InputMismatchException) {
                return "\u001B[31mОшибка:\u001B[0m Неверный формат ввода"
            }
        }
    }
}
