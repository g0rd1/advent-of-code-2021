package day09.part2

import java.io.File

fun main() {
    val path = "src\\main\\kotlin\\day9\\input.txt"
    val input = File(path).readLines()
    val points = input.map { inputLine ->
        inputLine.split("").filter { it.isNotBlank() }.map { it.toInt() }
    }
    val basins: MutableList<List<Point>> = mutableListOf()
    for (row in points.indices) {
        for (column in points[row].indices) {
            basins.add(getBasin(points, row, column).toList())
        }
    }
    val answer = basins.map { it.size }.sortedByDescending { it }.take(3).reduce { acc, size ->
        acc * size
    }
    println(answer)
}

fun getBasin(
    points: List<List<Int>>,
    row: Int,
    column: Int,
    previousValue: Int = -1,
): List<Point> {
    val value = points[row][column]
    if (value == 9 || previousValue >= value) return listOf()
    return getAdjacentPoints(points, row, column).flatMap { (pointRow, pointColumn) ->
        getBasin(points, pointRow, pointColumn, value)
    }.distinct() + Point(value, row, column)
}

private fun getAdjacentPoints(points: List<List<Int>>, row: Int, column: Int): List<Pair<Int, Int>> {
    return listOf(
        row + 1 to column,
        row to column + 1,
        row - 1 to column,
        row to column - 1,
    ).filter { (row, column) ->
        row >= 0 && column >= 0 && row < points.size && column < points[0].size
    }
}


