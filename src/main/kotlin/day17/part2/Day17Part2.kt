package day17.part2

import java.io.File

fun main() {
    val path = "src\\main\\kotlin\\day17\\input.txt"
    val input = File(path).readText()
    val coordRanges = input.substring(13).split(", ")
    val xRawRange = coordRanges[0].split("=")[1].split("..")
    val yRawRange = coordRanges[1].split("=")[1].split("..")
    val xRange = xRawRange[0].toInt()..xRawRange[1].toInt()
    val yRange = yRawRange[0].toInt()..yRawRange[1].toInt()
    val steps = (xRange.count() * -10..xRange.count() * 10).flatMap { x ->
        (yRange.count() * -10..yRange.count() * 10).map { y ->
            Step(0, 0, x, y)
        }
    }
    val filteredSteps = steps.filter { it.willGetToCoordinates(xRange, yRange) }
    println(filteredSteps.size)
}
