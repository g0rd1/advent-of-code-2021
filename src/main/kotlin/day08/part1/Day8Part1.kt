package day08.part1

import java.io.File

fun main() {
    val path = "src\\main\\kotlin\\day08\\input.txt"
    val input = File(path).readLines()
    val outputValues = input.flatMap { inputValue ->
        inputValue.split("|")[1].split(" ").filterNot { it.isBlank() }
    }
    val count = outputValues.count { it.length in listOf(2, 3, 4, 7) }
    println(count)
}

