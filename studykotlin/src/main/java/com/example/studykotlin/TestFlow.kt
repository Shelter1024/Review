package com.example.studykotlin

/**
 * @author: Shelter
 * Create time: 2022/12/21, 10:55.
 */

fun main() {
    simple().forEach { value ->
        println("value=$value")
    }

}

fun simple(): Sequence<Int> = sequence {
    for (i in 1..3) {
        Thread.sleep(100)
        yield(i)
    }
}

fun number2chinese(number: Int): String {
    var src = number + 1
    val num = arrayOf("零", "一", "二", "三", "四", "五", "六", "七", "八", "九")
    val unit = arrayOf("", "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千")
    var dst = ""
    var count = 0
    while (src > 0) {
        dst = num[src % 10] + unit[count] + dst
        src /= 10
        count++
    }
    val transformedNumber = dst.replace("零[千百十]".toRegex(), "零").replace("零+万".toRegex(), "万")
        .replace("零+亿".toRegex(), "亿").replace("亿万".toRegex(), "亿零")
        .replace("零+".toRegex(), "零").replace("零$".toRegex(), "")
    return "第${transformedNumber}期"
}

