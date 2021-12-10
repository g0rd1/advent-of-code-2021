package day09.part1

import java.io.File

fun main() {
    val path = "src\\main\\kotlin\\day9\\input.txt"
    val input = File(path).readLines()
    val points = input.map { inputLine ->
        inputLine.split("").filter { it.isNotBlank() }.map { it.toInt() }
    }
    val lowPoints: MutableList<Int> = mutableListOf()
    for (row in points.indices) {
        for (column in points[row].indices) {
            if (getAdjacentPoints(points, row, column).all { it > points[row][column] }) {
                lowPoints.add(points[row][column])
            }
        }
    }
    println(lowPoints.sumOf { it + 1 })
}

private fun getAdjacentPoints(points: List<List<Int>>, row: Int, column: Int): List<Int> {
    return listOf(
        row + 1 to column,
        row to column + 1,
        row - 1 to column,
        row to column - 1,
    ).filter { (row, column) ->
        row >= 0 && column >= 0 && row < points.size && column < points[0].size
    }.map { (row, column) ->
        points[row][column]
    }

}


