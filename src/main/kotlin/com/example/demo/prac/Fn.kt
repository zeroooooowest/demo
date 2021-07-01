package com.example.demo.prac

class Fn {
}

fun main() {
    var factor = 2
    val doubled = listOf(1, 2).map { it * factor }
    val doubledAlso = sequenceOf(1, 2).map { it * factor }

    factor = 0
    doubled.forEach(::println)
    doubledAlso.forEach(::println)


    caller()
    println("after return from caller")
    println()
}

fun invokeWith(n: Int, action: (Int) -> Unit) {
    println("enter invokeWith $n")
    action(n)
    println("exit invokeWith $n")
}

fun caller() {
    (1..3).forEach { i ->
        invokeWith(i) here@{
            println("enter for $it")
            if (it == 2) {
                return@here
            }
            println("exit for $it")
        }
    }
}

fun caller2() {
    (1..3).forEach { i ->
        invokeWith(i) {
            println("enter for $it")
            if (it == 2) {
                return@invokeWith
            }
            println("exit for $it")
        }
    }
    println("end of caller")
}

fun caller3() {
    (1..3).forEach { i ->
        println("in forEach for $i")
        if (i == 2) {
            return
        }
        invokeWith(i) {
            println("enter for $it")
            if (it == 2) {
                return@invokeWith
            }
            println("exit for $it")
        }
    }
    println("end of caller")
}