import java.io.File

fun main() {
    val path = "src\\main\\kotlin\\day3\\input.txt"
    val reports = File(path).readLines()
    val zeroCounts = (0 until reports[0].length).map { index ->
        reports.count { it[index] == '0' }
    }
    val gammaRateBinary = zeroCounts.joinToString(separator = "") {
        if (it > reports.size - it) "0" else "1"
    }
    val epsilonRateBinary = zeroCounts.joinToString(separator = "") {
        if (it > reports.size - it) "1" else "0"
    }
    val gammaRate = Integer.parseInt(gammaRateBinary, 2)
    val epsilonRate = Integer.parseInt(epsilonRateBinary, 2)
    println(gammaRate * epsilonRate)
}