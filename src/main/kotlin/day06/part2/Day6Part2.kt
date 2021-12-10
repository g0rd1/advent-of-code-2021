import java.io.File

fun main() {
    val path = "src\\main\\kotlin\\day06\\input.txt"
    val input = File(path).readText()
    val lanternfishTimerToCount = input
        .split(",")
        .map { it.trim().toInt() }
        .groupingBy { it }
        .eachCount()
        .mapValues { (_, count) -> count.toLong() }
    println(getAfterDays(lanternfishTimerToCount, 256).values.sum())
}

private fun getAfterDays(lanternfishTimerToCount: Map<Int, Long>, days: Int): Map<Int, Long> {
    if (days < 1) return lanternfishTimerToCount
    return getAfterDays(getAfterDay(lanternfishTimerToCount), days - 1)
}

private fun getAfterDay(lanternfishTimerToCount: Map<Int, Long>): Map<Int, Long> {
    return (0..8).associateWith {
        when (it) {
            8 -> lanternfishTimerToCount[0] ?: 0
            6 -> (lanternfishTimerToCount[it + 1] ?: 0) + (lanternfishTimerToCount[0] ?: 0)
            else -> lanternfishTimerToCount[it + 1] ?: 0
        }
    }
}