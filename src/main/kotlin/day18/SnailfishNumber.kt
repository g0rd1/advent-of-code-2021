package day18

data class SnailfishNumber(private val rawNumber: String) {

    operator fun plus(other: SnailfishNumber): SnailfishNumber {
        return reduce(SnailfishNumber("[${this.rawNumber},${other.rawNumber}]"))
    }

    fun getMagnitude(): Int {
        return getMagnitude(rawNumber)
    }

    private fun getMagnitude(rawNumber: String) : Int {
        if (rawNumber.toIntOrNull() != null) return rawNumber.toInt()
        var countOfNesting = 0
        var maxNesting = 0
        var indexOfMaxNestedPair = 0
        val rangesOfNumberPairs = Regex("\\[\\d+,\\d+]").findAll(rawNumber).map { it.range }
        for (i in rawNumber.indices) {
            countOfNesting += when (rawNumber[i]) {
                '[' -> 1
                ']' -> -1
                else -> 0
            }
            if (countOfNesting > maxNesting) {
                indexOfMaxNestedPair = i
                maxNesting = countOfNesting
            }
        }
        val rangeOfMaxNestedPair = rangesOfNumberPairs.first { it.first == indexOfMaxNestedPair }
        val rawNestedPair = rawNumber.substring(rangeOfMaxNestedPair)
        val nestedPairNumbersRanges = Regex("\\d+").findAll(rawNestedPair)
        val nestedPairFirstNumber = rawNestedPair.substring(nestedPairNumbersRanges.first().range).toInt()
        val nestedPairSecondNumber = rawNestedPair.substring(nestedPairNumbersRanges.last().range).toInt()
        return getMagnitude(
            rawNumber.replaceRange(
                rangeOfMaxNestedPair,
                ((3 * nestedPairFirstNumber) + (2 * nestedPairSecondNumber)).toString()
            )
        )
    }

    companion object {

        private fun reduce(number: SnailfishNumber): SnailfishNumber {
            var newNumber = explode(number)
            if (newNumber != number) return reduce(newNumber)
            newNumber = split(newNumber)
            if (newNumber != number) return reduce(newNumber)
            return number
        }

        private fun explode(number: SnailfishNumber): SnailfishNumber {
            val rangeOfFirstExplodePair = getRangeOfFirstExplodePairOrNull(number) ?: return number
            val rawNumber = number.rawNumber
            val rawExplodePair = rawNumber.substring(rangeOfFirstExplodePair)
            val explodePairNumbersRanges = Regex("\\d+").findAll(rawExplodePair)
            val explodePairFirstNumber = rawExplodePair.substring(explodePairNumbersRanges.first().range).toInt()
            val explodePairSecondNumber = rawExplodePair.substring(explodePairNumbersRanges.last().range).toInt()
            val rangesOfNumbers = Regex("\\d+").findAll(rawNumber)
            val rangeOfPreviousNumber = rangesOfNumbers
                .filter { it.range.last < rangeOfFirstExplodePair.first }
                .lastOrNull()
                ?.range
            val rangeOfNextNumber = rangesOfNumbers
                .filter { it.range.first > rangeOfFirstExplodePair.last }
                .firstOrNull()
                ?.range
            var newRawNumber = number.rawNumber
            if (rangeOfNextNumber != null) {
                newRawNumber = newRawNumber.replaceRange(
                    rangeOfNextNumber,
                    (rawNumber.substring(rangeOfNextNumber).toInt() + explodePairSecondNumber).toString()
                )
            }
            newRawNumber = newRawNumber.replaceRange(
                rangeOfFirstExplodePair,
                "0"
            )
            if (rangeOfPreviousNumber != null) {
                newRawNumber = newRawNumber.replaceRange(
                    rangeOfPreviousNumber,
                    (rawNumber.substring(rangeOfPreviousNumber).toInt() + explodePairFirstNumber).toString()
                )
            }
            return SnailfishNumber(newRawNumber)
        }

        private fun getRangeOfFirstExplodePairOrNull(number: SnailfishNumber): IntRange? {
            var countOfNesting = 0
            val rangesOfNumberPairs = Regex("\\[\\d+,\\d+]").findAll(number.rawNumber).map { it.range }
            for (i in number.rawNumber.indices) {
                countOfNesting += when (number.rawNumber[i]) {
                    '[' -> 1
                    ']' -> -1
                    else -> 0
                }
                if (countOfNesting < 5) continue
                val range = rangesOfNumberPairs.firstOrNull { it.first == i }
                if (range != null) return range
            }
            return null
        }

        private fun split(number: SnailfishNumber): SnailfishNumber {
            val rawNumber = number.rawNumber
            val rangeOfBigNumber = Regex("\\d+")
                .findAll(rawNumber)
                .firstOrNull { it.range.count() > 1 }
                ?.range ?: return number
            val bigNumber = rawNumber.substring(rangeOfBigNumber).toInt()
            val firstPartOfBigNumber = bigNumber / 2
            val secondPartOfBigNumber = bigNumber - firstPartOfBigNumber
            return SnailfishNumber(
                rawNumber.replaceRange(
                    rangeOfBigNumber,
                    "[$firstPartOfBigNumber,$secondPartOfBigNumber]"
                )
            )
        }
    }

}
