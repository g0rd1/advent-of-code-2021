package day12.part1

import day12.Caves
import java.io.File

fun main() {
    val path = "src\\main\\kotlin\\day12\\input.txt"
    val input = File(path).readLines().map { it.trim() }
    println(Caves(input).getRoutes(1).count { route -> route.any { it.isSmall() } })
}
