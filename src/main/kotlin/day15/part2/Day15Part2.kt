package day15.part2

import day15.PathFinder
import day15.Position
import java.io.File

fun main() {
    val path = "src\\main\\kotlin\\day15\\input.txt"
    val input = File(path).readLines().map { it.trim() }
    val positionToRiskLevel = (0 until input.size * 5).flatMap { inputRowIndex ->
        (0 until input[0].length * 5).map { inputColumnIndex ->
            val rowShift = inputRowIndex / input.size
            val columnShift = inputColumnIndex / input[0].length
            val originalInputRowIndex = inputRowIndex % input.size
            val originalInputColumnIndex = inputColumnIndex % input[0].length
            val riskLevelWithoutShift = input[originalInputRowIndex][originalInputColumnIndex].toString().toInt()
            val riskLevelWithShift = riskLevelWithoutShift + rowShift + columnShift
            val riskLevel = if (riskLevelWithShift < 10) riskLevelWithShift else riskLevelWithShift % 9
            Position(inputColumnIndex, inputRowIndex) to riskLevel
        }
    }.toMap()

    println(PathFinder(positionToRiskLevel).getMinRiskLevel())
}
