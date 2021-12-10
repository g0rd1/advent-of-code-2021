import java.io.File

fun main() {
    val path = "src\\main\\kotlin\\day03\\input.txt"
    val reports = File(path).readLines()
    val oxygenGeneratorRatingBinary = getReportByCriteria(true, reports)
    val co2ScrubberRatingBinary = getReportByCriteria(false, reports)
    val oxygenGeneratorRating = Integer.parseInt(oxygenGeneratorRatingBinary, 2)
    val co2ScrubberRating = Integer.parseInt(co2ScrubberRatingBinary, 2)
    println(oxygenGeneratorRating * co2ScrubberRating)
}

private fun getReportByCriteria(mostCommon: Boolean, reports: List<String>, bitIndex: Int = 0): String {
    if (reports.size == 1) return reports[0]
    val zeroCount = reports.count { it[bitIndex] == '0' }
    val filterBit = getFilterBit(zeroCount, reports.size, mostCommon)
    val filterReports = reports.filter { it[bitIndex] == filterBit }
    val nextBitIndex = if (reports[0].length - 1 == bitIndex) 0 else bitIndex + 1
    return getReportByCriteria(mostCommon, filterReports, nextBitIndex)
}

private fun getFilterBit(
    zeroCount: Int,
    reportsSize: Int,
    mostCommon: Boolean
): Char {
    return if (zeroCount > reportsSize - zeroCount) {
        if (mostCommon) {
            '0'
        } else {
            '1'
        }
    } else {
        if (mostCommon) {
            '1'
        } else {
            '0'
        }
    }
}