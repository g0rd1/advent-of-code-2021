package day18.part1

import day18.SnailfishNumber
import java.io.File

fun main() {
    val path = "src\\main\\kotlin\\day18\\input.txt"
    val input = File(path).readLines()
    val numbers = input.map { SnailfishNumber(it) }
    val result = numbers.reduce { acc, snailfishNumber ->
        acc + snailfishNumber
    }
    println(result.getMagnitude())
}