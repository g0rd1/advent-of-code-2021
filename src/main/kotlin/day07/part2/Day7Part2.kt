package day07.part2

import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.ceil
import kotlin.math.floor

fun main() {
    val path = "src\\main\\kotlin\\day7\\input.txt"
    val input = File(path).readText()
    val positions = input.split(",").map { it.trim().toInt() }
    val optimalPosition = getOptimalPosition(positions, positions.minOrNull()!!, positions.maxOrNull()!!)
    println(positions.sumOf { getFuelCost(it, optimalPosition) })
}

private fun getOptimalPosition(positions: List<Int>, minPosition: Int, maxPosition: Int): Int {
    if (minPosition == maxPosition) return minPosition
    val fuelCostForMin = positions.sumOf { getFuelCost(it, minPosition) }.toDouble()
    val fuelCostForMax = positions.sumOf { getFuelCost(it, maxPosition) }.toDouble()
    if (maxPosition - minPosition == 1) return if (fuelCostForMin < fuelCostForMax) minPosition else maxPosition
    val averagePosition = average(minPosition, maxPosition)
    val fuelCostForAverage = positions.sumByDouble {
        average(
            getFuelCost(it, floor(averagePosition).toInt()),
            getFuelCost(it, ceil(averagePosition).toInt()),
        )
    }
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

private fun getFuelCost(startPosition: Int, endPosition: Int): Int {
    return (1..(startPosition - endPosition).absoluteValue).sum()
}

private fun average(first: Int, second: Int) = (first + second) / 2.toDouble()

private fun average(first: Int, second: Double) = (first + second) / 2.toDouble()

