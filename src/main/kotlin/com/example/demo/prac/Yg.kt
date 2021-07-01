package com.example.demo

class Yg {
}

fun main() {
    var doubleOfEven = mutableListOf<Int>()
    for (i in 1..10) {
        if (i % 2 == 0) {
            doubleOfEven.add(i * 2)
        }
    }

    var doubleOfEven2 = (1..10)
        .filter { e -> e % 2 == 0 }
        .map { e -> e * 2 }
    println()

    walk1To({ i -> print(i) }, 5)
    walk2To(5, { i -> print(i) })
    walk2To(5) { i -> print(i) }
    walk2To(5) { print(it) }
    walk2To(5, ::print)
    walk2To(5, System.out::println)

    walk3To(5) { i -> Terminal.write(i) }
    walk3To(5, Terminal::write)

    val names = listOf("Pam", "Pat", "Paul", "Paula")
    println(names.find { name -> name.length == 5 })
    println(names.find { name -> name.length == 4 })
    println(names.find { name -> name.length == 3 })

    val checkLength5 = { name: String -> name.length == 5 }
    println(names.find(checkLength5))

    val checkLength4: (String) -> Boolean = { name -> name.length == 4 }

    val checkLength3 = fun(name: String): Boolean { return name.length == 3 }

    names.find(fun(name: String): Boolean { return name.length == 6 })
    names.find(fun(name: String): Boolean { return name.length == 6 })

    val doubleIt = { e: Int -> e * 2 }
    val factor = 2
    val doubleIt2 = { e: Int -> e * factor }



}

fun isPrime(n: Int) = n > 1 && (2 until n).none { i: Int -> n % i == 0 }
fun isPrime2(n: Int) = n > 1 && (2 until n).none { n % it == 0 }

fun walk1To(action: (Int) -> Unit, n: Int) = (1..n).forEach { action(it) }
fun walk2To(n: Int, action: (Int) -> Unit) = (1..n).forEach { action(it) }
fun walk3To(n: Int, action: (Int) -> Unit) = (1..n).forEach(action)


object Terminal {
    fun write(value: Int) = println(value)
}

fun predicateOfLength(length: Int): (String) -> Boolean {
    return { input: String -> input.length == length }
}

fun predicateOfLength2(length: Int) = { input: String -> input.length == length }

