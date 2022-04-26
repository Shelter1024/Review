package com.shelter.review

/**
 * @author: Shelter
 * Create time: 2022/3/30, 18:13.
 */

fun main() {
    val str: String = """
        ${'$'}9999.99
    """.trimIndent()
    println(str)

    println(getNumber("zds"))

    val str2: String? = null
    println(str2?.length)
    var person = Person()
    Person.Student.play()

    person.eat()
//    print(person.name)
}

fun getNumber(name: String): Int? {
    if (name == "zs")
        return 20;
    return null
}