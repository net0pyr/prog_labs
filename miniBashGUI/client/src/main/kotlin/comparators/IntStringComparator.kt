package com.net0pyr.comparators

import java.util.Comparator

class IntStringComparator : Comparator<String> {
    override fun compare(s1: String?, s2: String?): Int {
        // Проверяем, что обе строки не пустые
        if (s1.isNullOrEmpty() && s2.isNullOrEmpty()) return 0
        if (s1.isNullOrEmpty()) return -1
        if (s2.isNullOrEmpty()) return 1

        // Преобразуем строки в целые числа
        val int1 = s1!!.toIntOrNull()
        val int2 = s2!!.toIntOrNull()

        // Если обе строки преобразовались в числа, сравниваем их
        if (int1 != null && int2 != null) {
            return int1.compareTo(int2)
        }

        // Если одна из строк не преобразуется в число, возвращаем нуль (равенство)
        return 0
    }
}