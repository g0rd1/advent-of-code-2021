import day05.Line
import day05.Point
import java.io.File

fun main() {
    val path = "src\\main\\kotlin\\day5\\input.txt"
    val input = File(path).readLines()
    val lines = input.map { inputLine ->
        val points = inputLine.split(" -> ")
        val startPoint = points[0].split(",")
        val endPoint = points[1].split(",")
        Line(
            Point(startPoint[0].toInt(), startPoint[1].toInt()),
            Point(endPoint[0].toInt(), endPoint[1].toInt())
        )
    }
    val linesPoints = lines.flatMap { it.getPointsWithoutDiagonals() }
    val groupedByCountPoints = linesPoints.groupingBy { it }.eachCount()
    println(groupedByCountPoints.values.count { it > 1 })
}