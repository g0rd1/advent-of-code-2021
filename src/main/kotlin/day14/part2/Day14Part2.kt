package day14.part2

import java.io.File

fun main() {
    val path = "src\\main\\kotlin\\day14\\input.txt"
    val input = File(path).readLines().map { it.trim() }
    val polymerTemplate = input[0]
    val insertionRules = (2 until input.size).associate { index ->
        val split = input[index].split(" -> ")
        split[0] to split[1].toCharArray()[0]
    }
    val getElementToCount = getElementToCount(40, polymerTemplate.toList(), insertionRules)
    val counts = getElementToCount.values
    println(counts.maxOf { it } - counts.minOf { it })
}

private fun getElementToCount(
    iterations: Int,
    polymer: List<Char>,
    insertionRules: Map<String, Char>,
): Map<Char, Long> {
    val step = doubleValue(1, iterations.toLong())
    val elementToCount = polymer.toList().groupingBy { it }.eachCount().mapValues { it.value.toLong() }.toMutableMap()
    (1 until polymer.size).forEach { elementIndex ->
        getElementToCount(
            leftElement = polymer[elementIndex - 1],
            leftElementIndex = (elementIndex - 1) * step,
            rightElement = polymer[elementIndex],
            rightElementIndex = elementIndex * step,
            insertionRules = insertionRules
        ).forEach { (element, count) ->
            elementToCount[element] = (elementToCount[element] ?: 0) + count
        }
    }
    return elementToCount
}

private fun doubleValue(value: Long, times: Long): Long {
    if (times < 1) return value
    return doubleValue(value * 2, times - 1)
}

private val cache: MutableMap<Triple<Char, Char, Long>, Map<Char, Long>> = mutableMapOf()

private fun getElementToCount(
    leftElement: Char,
    leftElementIndex: Long,
    rightElement: Char,
    rightElementIndex: Long,
    insertionRules: Map<String, Char>,
): Map<Char, Long> {
    if (leftElementIndex == rightElementIndex - 1) return mapOf()
    val cachedNullableElementToCount = cache[Triple(leftElement, rightElement, rightElementIndex - leftElementIndex)]
    if (cachedNullableElementToCount != null) return cachedNullableElementToCount
    val newElementIndex = (leftElementIndex + rightElementIndex) / 2
    val newElement = insertionRules["$leftElement$rightElement"]!!
    val elementToCount = mutableMapOf<Char, Long>()
    elementToCount[newElement] = 1
    getElementToCount(leftElement, leftElementIndex, newElement, newElementIndex, insertionRules)
        .forEach { (element, count) ->
            elementToCount[element] = (elementToCount[element] ?: 0) + count
        }
    getElementToCount(newElement, newElementIndex, rightElement, rightElementIndex, insertionRules)
        .forEach { (element, count) ->
            elementToCount[element] = (elementToCount[element] ?: 0) + count
        }
    cache[Triple(leftElement, rightElement, rightElementIndex - leftElementIndex)] = elementToCount
    return elementToCount
}
