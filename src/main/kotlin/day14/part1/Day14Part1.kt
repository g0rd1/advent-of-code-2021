package day14.part1

import java.io.File

fun main() {
    val path = "src\\main\\kotlin\\day14\\input.txt"
    val input = File(path).readLines().map { it.trim() }
    var polymerTemplate = input[0]
    val insertionRules = (2 until input.size).map { index ->
        val split = input[index].split(" -> ")
        split[0] to split[1]
    }.toMap()
    repeat(10) {
        val polymerTemplateBuilder = StringBuilder()
        polymerTemplateBuilder.append(polymerTemplate[0])
        (1 until polymerTemplate.length).forEach { index ->
            polymerTemplateBuilder.append(
                insertionRules["${polymerTemplate[index - 1]}${polymerTemplate[index]}"] ?: ""
            )
            polymerTemplateBuilder.append(polymerTemplate[index])
        }
        polymerTemplate = polymerTemplateBuilder.toString()
    }
    val elementToCount = polymerTemplate.groupingBy { it }.eachCount()
    val counts = elementToCount.values
    println(counts.maxOf { it } - counts.minOf { it })
}
