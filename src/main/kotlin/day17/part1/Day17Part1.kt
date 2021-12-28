package day17.part1

import java.io.File

fun main() {
    val path = "src\\main\\kotlin\\day17\\input.txt"
    val input = File(path).readText()
    val coordRanges = input.substring(13).split(", ")
    val yRawRange = coordRanges[1].split("=")[1].split("..")
    val yRange = yRawRange[0].toInt()..yRawRange[1].toInt()
    var startYVelocity = 0
    var maxYStartVelocityToHitTarget = 0
    while (true) {
        var y = getMemberOfArithmeticProgression(startYVelocity)
        var yVelocity = 0
        while (y > yRange.maxOf { it }) {
            y += yVelocity
            yVelocity--
        }
        if (y in yRange) {
            maxYStartVelocityToHitTarget = startYVelocity
        }
        if (startYVelocity > yRange.count() * 10) break
        startYVelocity++
    }
    println(getMemberOfArithmeticProgression(maxYStartVelocityToHitTarget))
}

private fun getMemberOfArithmeticProgression(index: Int): Long {
    return index * (index + 1) / 2L
}