package day8.part2

import java.io.File

fun main() {
    val path = "src\\main\\kotlin\\day8\\input.txt"
    val input = File(path).readLines()
    val patternsToOutputValues: Map<List<String>, List<String>> = input.associate { inputValue ->
        Pair(
            inputValue.split("|")[0].split(" ").filterNot { it.isBlank() },
            inputValue.split("|")[1].split(" ").filterNot { it.isBlank() }
        )
    }
    val displayToOutputValues = patternsToOutputValues.mapKeys { (patterns, _) ->
        Display(patterns)
    }
    val numbers = displayToOutputValues.map { (display, outputValues) ->
        outputValues.map { display.decodeValue(it) }.joinToString("").toInt()
    }
    println(numbers.sum())
}

