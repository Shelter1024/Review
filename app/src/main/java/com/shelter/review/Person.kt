package com.shelter.review

/**
 * @author: Shelter
 * Create time: 2022/3/30, 23:16.
 */
class Person {
    lateinit var name: String

    fun eat() {
        var a = 1
        val obj = object {
            fun count() {
                a++
            }
        }
//        println("eat something")
        obj.count()
        println(a)
    }

    companion object {

        @JvmStatic
        fun sleep() {
        }
    }

    object Student {
        val grade: String = "一年级"

        fun play() {

        }
    }
}