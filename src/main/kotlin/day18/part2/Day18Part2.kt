package day18.part2

import day18.SnailfishNumber
import java.io.File

fun main() {
    val path = "src\\main\\kotlin\\day18\\input.txt"
    val input = File(path).readLines()
    val numbers = input.map { SnailfishNumber(it) }
    val magnitudes = mutableListOf<Int>()
    for (i in input.indices) {
        for (j in input.indices) {
            if (i == j) continue
            magnitudes.add((numbers[i] + numbers[j]).getMagnitude())
            magnitudes.add((numbers[j] + numbers[i]).getMagnitude())
        }
    }
    println(magnitudes.maxOf { it })
}