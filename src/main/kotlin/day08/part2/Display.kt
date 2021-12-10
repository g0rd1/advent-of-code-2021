package day08.part2

class Display(private val patterns: List<String>) {

    private val segmentToCode: MutableMap<Int, Char> = mutableMapOf()

    private val codeValues
        get() = segmentToCode.values

    init {
        patterns.sortedBy { it.length }.forEach { handlePattern(it) }
    }

    fun decodeValue(value: String): Char {
        return when (value.length) {
            2 -> '1'
            3 -> '7'
            4 -> '4'
            7 -> '8'
            5 -> {
                when {
                    value.toCharArray().toList().contains(segmentToCode[1]) -> '5'
                    value.toCharArray().toList().contains(segmentToCode[4]) -> '2'
                    else -> '3'
                }
            }
            6 -> {
                when {
                    !value.toCharArray().toList().contains(segmentToCode[3]) -> '0'
                    !value.toCharArray().toList().contains(segmentToCode[4]) -> '9'
                    else -> '6'
                }
            }
            else -> throw IllegalArgumentException()
        }
    }

    private fun handlePattern(pattern: String) {
        when (pattern.length) {
            2 -> {
                val patternOf6 = patterns
                    .filter { it.length == 6 }
                    .first { !it.toCharArray().toList().containsAll(pattern.toCharArray().toList()) }
                segmentToCode[2] = pattern.toCharArray().first { it !in patternOf6.toCharArray() }
                segmentToCode[5] = pattern.first { it !in codeValues }
            }
            3 -> {
                segmentToCode[0] = pattern.first { it !in codeValues }
            }
            4 -> {
                val newCodes = pattern.toCharArray().toList().filter { it !in codeValues }
                val patternOf0 = patterns
                    .filter { it.length == 6 }
                    .first { !it.toCharArray().toList().containsAll(newCodes) }
                segmentToCode[3] = newCodes.first { it !in patternOf0.toCharArray() }
                segmentToCode[1] = newCodes.first { it !in codeValues }
            }
            5 -> {
                if (segmentToCode[6] != null || codeValues.count { it !in pattern.toCharArray() } > 1) return
                if (pattern.firstOrNull { it !in codeValues } == null) {
                    println()
                }
                segmentToCode[6] = pattern.first { it !in codeValues }
            }
            7 -> {
                segmentToCode[4] = pattern.first { it !in codeValues }
            }
        }
    }

}