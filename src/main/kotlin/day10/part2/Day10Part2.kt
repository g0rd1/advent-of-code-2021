package day10.part2

import java.io.File
import java.util.*

fun main() {
    val path = "src\\main\\kotlin\\day10\\input.txt"
    val input = File(path).readLines()
    val addingToCompleteCharactersPoints = input
        .mapNotNull { getAddingToCompleteCharactersOrNull(it) }
        .map { getAddingToCompleteCharactersPoints(it) }
        .sorted()
    val answer = addingToCompleteCharactersPoints[addingToCompleteCharactersPoints.size / 2]
    println(answer)
}

private fun getAddingToCompleteCharactersPoints(addingToCompleteCharacters: List<Char>): Long {
    return addingToCompleteCharacters.fold(0L) { acc, character ->
        acc * 5 + getClosingCharacterPoints(character)
    }
}

private fun getAddingToCompleteCharactersOrNull(line: String): List<Char>? {
    val queue: Deque<Char> = ArrayDeque()
    line.toList().forEach { character ->
        if (!isClosingCharacter(character)) {
            queue.add(character)
        } else {
            if (getClosingCharacterForOpeningCharacter(queue.pollLast()) != character) {
                return null
            }
        }
    }
    return queue.toList().reversed().map { getClosingCharacterForOpeningCharacter(it) }
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
        ')' -> 1
        ']' -> 2
        '}' -> 3
        '>' -> 4
        else -> throw IllegalArgumentException()
    }
}

