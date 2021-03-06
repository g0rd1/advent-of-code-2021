package day07.part1

import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.ceil
import kotlin.math.floor

fun main() {
    val path = "src\\main\\kotlin\\day07\\input.txt"
    val input = File(path).readText()
    val positions = input.split(",").map { it.trim().toInt() }
    val optimalPosition = getOptimalPosition(positions, positions.minOrNull()!!, positions.maxOrNull()!!)
    println(positions.sumOf { (it - optimalPosition).absoluteValue })
}

private fun getOptimalPosition(positions: List<Int>, minPosition: Int, maxPosition: Int): Int {
    if (minPosition == maxPosition) return minPosition
    val fuelCostForMin = positions.sumOf { (it - minPosition).absoluteValue }.toDouble()
    val fuelCostForMax = positions.sumOf { (it - maxPosition).absoluteValue }.toDouble()
    if (maxPosition - minPosition == 1) return if (fuelCostForMin < fuelCostForMax) minPosition else maxPosition
    val averagePosition = average(minPosition, maxPosition)
    val fuelCostForAverage = positions.sumByDouble { (it - averagePosition).absoluteValue }
    if (maxPosition - minPosition == 2 && fuelCostForMin > fuelCostForAverage && fuelCostForMax > fuelCostForAverage) return minPosition + 1
    return when (minOf(fuelCostForMin, fuelCostForAverage, fuelCostForMax)) {
        fuelCostForMin -> getOptimalPosition(positions, minPosition, ceil(averagePosition).toInt())
        fuelCostForMax -> getOptimalPosition(positions, floor(averagePosition).toInt(), maxPosition)
        else -> getOptimalPosition(
            positions,
            floor(average(minPosition, averagePosition)).toInt(),
            ceil(average(maxPosition, averagePosition)).toInt()
        )
    }
}

private fun average(first: Int, second: Int) = (first + second) / 2.toDouble()

private fun average(first: Int, second: Double) = (first + second) / 2.toDouble()

