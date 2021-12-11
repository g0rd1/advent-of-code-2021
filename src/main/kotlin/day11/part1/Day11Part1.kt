package day11.part1

import day11.Energy
import java.io.File

fun main() {
    val path = "src\\main\\kotlin\\day11\\input.txt"
    val input = File(path).readLines()
    val energy = input.map { inputLine ->
        inputLine.trim().toList().map { Energy.StoreEnergy(it.toString().toInt()) }
    }
    println(getEnergyAndFlashesAfterSteps(energy, 100).second)
}

private fun getEnergyAndFlashesAfterSteps(
    energy: List<List<Energy.StoreEnergy>>,
    steps: Int,
    startFlashes: Long = 0L
): Pair<List<List<Energy.StoreEnergy>>, Long> {
    if (steps < 1) return energy to startFlashes
    val increasedEnergy = energy.map { energyRow -> energyRow.map { it + 1 } }
    val energyAfterFlashes = getEnergyAfterFlashes(increasedEnergy)
    val flashes = energyAfterFlashes.flatten().count { it is Energy.Flashed }
    val storedEnergyAfterFlashes = energyAfterFlashes.map { energyRow ->
        energyRow.map {
            when (it) {
                is Energy.StoreEnergy -> it
                is Energy.Flashed -> Energy.StoreEnergy(0)
            }
        }
    }
    return getEnergyAndFlashesAfterSteps(storedEnergyAfterFlashes, steps - 1, startFlashes + flashes)
}

private fun getEnergyAfterFlashes(energy: List<List<Energy>>): List<List<Energy>> {
    val firstReadyToFlashPoint = getFirstReadyToFlashPointOrNull(energy)
    return if (firstReadyToFlashPoint != null) {
        return getEnergyAfterFlashes(
            getEnergyAfterFlash(energy, firstReadyToFlashPoint.first, firstReadyToFlashPoint.second)
        )
    } else {
        energy
    }
}

private fun getFirstReadyToFlashPointOrNull(energy: List<List<Energy>>): Pair<Int, Int>? {
    for (row in energy.indices) {
        for (column in energy[row].indices) {
            val singleEnergy = energy[row][column]
            if (singleEnergy is Energy.StoreEnergy && singleEnergy.energy > 9) {
                return row to column
            }
        }
    }
    return null
}

private fun getEnergyAfterFlash(energy: List<List<Energy>>, row: Int, column: Int): List<List<Energy>> {
    return energy.mapIndexed { energyRowIndex, energyRow ->
        energyRow.mapIndexed { energyColumnIndex, singleEnergy ->
            if ((energyRowIndex to energyColumnIndex) in getAdjacentEnergyPoints(energy, row, column)) {
                when (singleEnergy) {
                    is Energy.StoreEnergy -> singleEnergy + 1
                    is Energy.Flashed -> singleEnergy
                }
            } else {
                if (row == energyRowIndex && column == energyColumnIndex) {
                    Energy.Flashed
                } else {
                    singleEnergy
                }
            }
        }
    }
}

private fun getAdjacentEnergyPoints(energy: List<List<Energy>>, row: Int, column: Int): List<Pair<Int, Int>> {
    return listOf(
        row + 1 to column,
        row to column + 1,
        row - 1 to column,
        row to column - 1,
        row + 1 to column + 1,
        row + 1 to column - 1,
        row - 1 to column + 1,
        row - 1 to column - 1,
    ).filter { (row, column) ->
        row >= 0 && column >= 0 && row < energy.size && column < energy[0].size
    }
}
