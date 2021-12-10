package day10.part1

import java.io.File
import java.util.*

fun main() {
    val path = "src\\main\\kotlin\\day10\\input.txt"
    val input = File(path).readLines()
    val answer = input
        .mapNotNull { getFirstIncorrectClosingCharacterOrNull(it) }
        .sumOf { getClosingCharacterPoints(it) }
    println(answer)
}

private fun getFirstIncorrectClosingCharacterOrNull(line: String): Char? {
    val queue: Deque<Char> = ArrayDeque()
    line.toList().forEach { character ->
        if (!isClosingCharacter(character)) {
            queue.add(character)
        } else {
            if (getClosingCharacterForOpeningCharacter(queue.pollLast()) != character) {
                return character
            }
        }
    }
    return null
}

private fun getClosingCharacterForOpeningCharacter(closingCharacter: Char): Char {
    return when (closingCharacter) {
        '(' -> ')'
        '[' -> ']'
        '{' -> '}'
        '<' -> '>'
        else -> throw IllegalArgumentException()
    }
}

private fun isClosingCharacter(character: Char): Boolean {
    return when (character) {
        ')',
        ']',
        '}',
        '>' -> true
        else -> false
    }
}

private fun getClosingCharacterPoints(character: Char): Int {
    return when (character) {
        ')' -> 3
        ']' -> 57
        '}' -> 1197
        '>' -> 25137
        else -> throw IllegalArgumentException()
    }
}

