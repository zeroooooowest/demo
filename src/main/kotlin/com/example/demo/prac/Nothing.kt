package com.example.demo.prac

class Nothing {

}

fun main() {
    val items = listOf(1, 2, 3, 4, 5)

    items.fold(0) { acc: Int, i: Int ->
        print("acc = $acc, i = $i, ")
        val result = acc + i
        println("result = $result")
        result
    }

    val joinedToString = items.fold("Elements:", { acc, i -> acc + " " + i })
    println(joinedToString)
    val product = items.fold(1, Int::times)
    println(product)
}


fun MutableList<Int>.swap(index1: Int, index2: Int) {
    val tmp = this[index1]
    this[index1] = this[index2]
    this[index2] = tmp
}