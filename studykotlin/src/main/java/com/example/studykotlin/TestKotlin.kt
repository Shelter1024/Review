package com.example.studykotlin

/**
 * @author: Shelter
 * Create time: 2022/11/18, 00:12.
 */

lateinit var fileName: String


fun main() {
    // 报错，因为setter方法私有
    //TestKotlin().name = ""
    TestKotlin().println()

    val list = listOf("apple", "banana", "kiwifruit")
    when {
        "orange" in list -> {
            println("juice")
        }
    }

    list.filter {
        it.startsWith("a")
    }.sortedBy {
        it
    }.map {
        it.uppercase()
    }


    val newStudent = Student("Shelter", 30, 1).copy(age = 10)

    list.filter {
        it.length > 5
    }

//        val item = "Shelter"
    val item = Any()
    when (item) {
        in list -> {

        }
        is String -> {

        }
        "Shelter" -> {

        }
        is Student -> {

        }
        else -> {

        }
    }

//    val map = mapOf(Pair("Shelter", 30), Pair("Penny", 30))
    val map = mapOf("Shelter" to 30, "Penny" to 30)
    for ((k, v) in map) {
        println("k:$k, v:$v")
    }

    TestKotlin.printCount()

    list.firstOrNull().let {

    }
    for ((index, value) in list.withIndex()) {
        println("index: $index, value: $value")
    }

    //方法的参数为匿名函数
    list.forEach(fun(value: String) {
        // 局部返回到匿名函数的调用者，即 forEach 循环
        if (value == "Shelter") return
    })

    //Files.newInputStream()

    var a = 10
    var b = 20
    a = b.also {
        b = a
    }
    println("a:$a, b:$b")

    val me = Student("Shelter", 18, 5)
    val (_, _, grade) = me
    println("grade = $grade")
    val people = listOf(me)
    var totalGrade: Int = 0
    for ((
        _,
        _,
        grade,
    ) in people) {
        totalGrade += grade
    }
    val num = 1 shl 10 and 0xfff00

    val text = """
    for (c in "foo")
        print(c)
    """
    println(text.trimMargin())
    val text2 = """
        |Tell me and I forget.
        |Teach me and I remember.
        |Involve me and I learn.
        |(Benjamin Franklin)
    """.trimMargin()
    println(text2)

    text2.also(::println)

    Teacher(10)

    println("is initialized: ${::fileName.isInitialized}")

    val predicate = IntPredicate {
        it % 2 == 0
    }
    println(predicate.accept(10))

    //val obj = TestKotlin.InnerClass()
    TestKotlin().expandMethod()
    println(TestKotlin().expandProperty)
}

fun interface IntPredicate {
    fun accept(i: Int): Boolean

    fun accept2() {

    }
}


fun testTODO(text: String): String = TODO("")

/**
 * 当要返一个回值的时候，解析器优先选用标签限制的 return
 */
fun calculate(num: Int): Int {
    val list = listOf(1, 2, 3, 4)
    list.forEach(fun(num: Int) {
        if (num == 2) return@calculate 1
    })
    return 1
}

open class TestKotlin {
    companion object Instance {
        const val count = 0

        fun printCount() {
            println("count = $count")
        }
    }

    lateinit var className: String

//    init {
//        className = ""
//    }

    var name: String = "TestKotlin"
        private set(value) {
            field = "::value"
        }

    fun println() {
        fun getLength() {

        }
        if (::className.isInitialized) {
            println("已初始化 ${getLength()}")
        } else {
            println("未初始化")
        }
        val obj = InnerClass("")
    }

    protected class InnerClass constructor(val name: String) {
        val num = 10
    }


}

fun TestKotlin.expandMethod() {
    println("执行了扩展函数")
}

val TestKotlin.expandProperty: String
    get() {
        return "expandProperty"
    }

