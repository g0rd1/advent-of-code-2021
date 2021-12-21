package day15.part1

import day15.PathFinder
import day15.Position
import java.io.File

fun main() {
    val path = "src\\main\\kotlin\\day15\\input.txt"
    val input = File(path).readLines().map { it.trim() }
    val positionToRiskLevel = (input.indices).flatMap { inputRowIndex ->
        (0 until input[inputRowIndex].length).map { inputColumnIndex ->
            Position(inputColumnIndex, inputRowIndex) to input[inputRowIndex][inputColumnIndex].toString().toInt()
        }
    }.toMap()

    println(PathFinder(positionToRiskLevel).getMinRiskLevel())
}
