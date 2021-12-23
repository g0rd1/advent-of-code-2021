package day16.part1

import java.io.File

fun main() {
    val path = "src\\main\\kotlin\\day16\\input.txt"
    val input = File(path).readText()
    val binaryInput = input.map { hexToBinary(it) }.joinToString("")
    val answer = getPacketsToLevel(binaryInput).sumOf { (packet, _) -> packet.substring(0, 3).toInt(2) }
    println(answer)
}

private fun hexToBinary(hex: Char): String {
    var result = Integer.toBinaryString(hex.toString().toInt(16))
    while (result.length < 4) {
        result = "0$result"
    }
    return result
}

private fun getPacketsToLevel(input: String, level: Int = 0, count: Int? = null): List<Pair<String, Int>> {
    val packets = mutableListOf<Pair<String, Int>>()
    var notHandledInput = input
    var counter = 0
    while (notHandledInput.isNotEmpty() && notHandledInput.any { it != '0' }) {
        val typeID = notHandledInput.substring(3, 6).toInt(2)
        if (typeID == 4) {
            val fives = notHandledInput.substring(6).chunked(5)
            val firstEndFiveIndex = fives.indexOfFirst { it[0] == '0' }
            val subPacketEndIndex = 6 + (5 * (firstEndFiveIndex + 1))
            packets.add(notHandledInput.substring(0, subPacketEndIndex) to level)
            counter++
            if (count != null && counter == count) return packets
            notHandledInput = notHandledInput.substring(subPacketEndIndex)
        } else {
            if (notHandledInput[6] == '0') {
                val lengthOfSubPackets = notHandledInput.substring(7, 22).toInt(2)
                val rawSubPackets = notHandledInput.substring(22, 22 + lengthOfSubPackets)
                val subPacketsOfSubPacket = getPacketsToLevel(rawSubPackets, level + 1)
                packets.addAll(subPacketsOfSubPacket)
                packets.add(notHandledInput.substring(0, 22 + lengthOfSubPackets) to level)
                counter++
                if (count != null && counter == count) return packets
                notHandledInput = notHandledInput.substring(22 + lengthOfSubPackets)
            } else {
                val countOfSubPackets = notHandledInput.substring(7, 18).toInt(2)
                val rawSubPackets = notHandledInput.substring(18)
                val subPacketsOfSubPacket = getPacketsToLevel(rawSubPackets, level + 1, countOfSubPackets)
                val subPacketsOfSubPacketLevelAbove = subPacketsOfSubPacket
                    .filter { (_, subPacketLevel) -> subPacketLevel == level + 1 }
                val lengthOfSubPackets = subPacketsOfSubPacketLevelAbove
                    .sumOf { (subPacket, _) -> subPacket.length }
                packets.addAll(subPacketsOfSubPacket)
                val rawSubPacketsOfSubPacket = subPacketsOfSubPacketLevelAbove
                    .joinToString("") { (subPacket, _) -> subPacket }
                packets.add(notHandledInput.substring(0, 18) + rawSubPacketsOfSubPacket to level)
                counter++
                if (count != null && counter == count) return packets

                notHandledInput = notHandledInput.substring(18 + lengthOfSubPackets)
            }
        }
    }
    return packets
}

