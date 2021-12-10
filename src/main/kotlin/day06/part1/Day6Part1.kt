import java.io.File

fun main() {
    val path = "src\\main\\kotlin\\day06\\input.txt"
    val input = File(path).readText()
    val lanternfishTimerToCount = input
        .split(",")
        .map { it.trim().toInt() }
        .groupingBy { it }
        .eachCount()
    println(getAfterDays(lanternfishTimerToCount, 80).values.sum())
}

private fun getAfterDays(lanternfishTimerToCount: Map<Int, Int>, days: Int): Map<Int, Int> {
    if (days < 1) return lanternfishTimerToCount
    return getAfterDays(getAfterDay(lanternfishTimerToCount), days - 1)
}

private fun getAfterDay(lanternfishTimerToCount: Map<Int, Int>): Map<Int, Int> {
    return (0..8).associateWith {
        when (it) {
            8 -> lanternfishTimerToCount[0] ?: 0
            6 -> (lanternfishTimerToCount[it + 1] ?: 0) + (lanternfishTimerToCount[0] ?: 0)
            else -> lanternfishTimerToCount[it + 1] ?: 0
        }
    }
}