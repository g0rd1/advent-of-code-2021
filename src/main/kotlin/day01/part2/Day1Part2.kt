import java.io.File

fun main() {
    val path = "src\\main\\kotlin\\day01\\part1\\input.txt"
    val measurements = File(path).readLines().map { it.toInt() }
    val windowedMeasurements = measurements.windowed(3)
    val sumOfWindowedMeasurements = windowedMeasurements.map { it.sum() }
    val answer = (1 until sumOfWindowedMeasurements.count()).reduce { acc, index ->
        if (sumOfWindowedMeasurements[index] > sumOfWindowedMeasurements[index - 1]) acc + 1 else acc
    }
    println(answer)
}