package day16.part2

import java.lang.IllegalStateException

sealed class Packet {

    abstract fun execute(): Long

    data class Operation(private val rawPacket: String) : Packet() {
        private val subPackets: List<Packet>

        private val typeId: Int = rawPacket.substring(3, 6).toInt(2)

        init {
            subPackets = if (rawPacket[6] == '0') {
                val lengthOfSubPackets = rawPacket.substring(7, 22).toInt(2)
                val rawSubPackets = rawPacket.substring(22, 22 + lengthOfSubPackets)
                getPacketsToLevel(rawSubPackets, 1)
                    .filter { (_, level) -> level == 1 }
                    .map { (subPacket, _) -> create(subPacket) }
            } else {
                val countOfSubPackets = rawPacket.substring(7, 18).toInt(2)
                val rawSubPackets = rawPacket.substring(18)
                getPacketsToLevel(rawSubPackets, 1, countOfSubPackets)
                    .filter { (_, level) -> level == 1 }
                    .map { (subPacket, _) -> create(subPacket) }
            }
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

        override fun execute(): Long {
            return when (typeId) {
                0 -> subPackets.sumOf { it.execute() }
                1 -> subPackets.map { it.execute() }.reduce { acc, value -> acc * value }
                2 -> subPackets.minOf { it.execute() }
                3 -> subPackets.maxOf { it.execute() }
                5 -> if (subPackets[0].execute() > subPackets[1].execute()) 1 else 0
                6 -> if (subPackets[0].execute() < subPackets[1].execute()) 1 else 0
                7 -> if (subPackets[0].execute() == subPackets[1].execute()) 1 else 0
                else -> throw IllegalStateException()
            }
        }
    }

    data class Value(private val rawPackage: String) : Packet() {

        private val value: Long = rawPackage
            .substring(6)
            .chunked(5).joinToString("") { it.substring(1) }
            .toLong(2)

        override fun execute(): Long {
            return value
        }

    }

    companion object {

        fun create(rawPackage: String): Packet {
            return if (rawPackage.substring(3, 6).toInt(2) == 4) {
                Value(rawPackage)
            } else {
                Operation(rawPackage)
            }
        }
    }

}