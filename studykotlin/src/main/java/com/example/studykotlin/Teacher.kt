package com.example.studykotlin

/**
 * @author: Shelter
 * Create time: 2022/11/30, 15:27.
 */
class Teacher(val sex: String, age: Int) {
    var classString = "teacher: $sex"
    val length: Int
        get() = classString.length
    private var introduction: String

    init {
        introduction = "sex:$sex age:$age"
        println("init block: $introduction")
    }

    constructor(age: Int) : this("male", age) {
        action()
    }

    fun action() {
        println("action run")
        also {  }
        println("string length = $length")
        classString = ""
        println("string length = $length")

    }
}