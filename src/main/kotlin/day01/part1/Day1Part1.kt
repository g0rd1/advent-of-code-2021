import java.io.File

fun main() {
    val path = "src\\main\\kotlin\\day01\\part1\\input.txt"
    val measurements = File(path).readLines().map { it.toInt() }
    val answer = (1 until measurements.count()).reduce { acc, index ->
        if (measurements[index] > measurements[index - 1]) acc + 1 else acc
    }
    println(answer)
}