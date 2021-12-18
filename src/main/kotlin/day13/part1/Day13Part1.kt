package day13.part1

import day13.Paper
import day13.PaperReducer
import java.io.File

fun main() {
    val path = "src\\main\\kotlin\\day13\\input.txt"
    val input = File(path).readLines().map { it.trim() }
    val dotPoints = input.filter {
        it.matches("^\\d.*".toRegex())
    }.map {
        val split = it.split(",")
        split[0].toInt() to split[1].toInt()
    }
    val inputCommands = input.filter {
        it.matches("^fold along.*".toRegex())
    }.map {
        val split = it.split("=")
        if (split[0] == "fold along x") {
            { paper: Paper -> PaperReducer.splitAlongX(paper, split[1].toInt()) }
        } else {
            { paper: Paper -> PaperReducer.splitAlongY(paper, split[1].toInt()) }
        }
    }
    val positions = (0..dotPoints.maxOf { it.second }).map { rowIndex ->
        (0..dotPoints.maxOf { it.first }).map { columnIndex ->
            ((columnIndex to rowIndex) in dotPoints)
        }
    }
    val paper = Paper(positions)
    val answer = inputCommands[0](paper).positions.flatten().count { it }
    println(answer)
}
