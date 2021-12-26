package day16.part2

import java.io.File

fun main() {
    val path = "src\\main\\kotlin\\day16\\input.txt"
    val input = File(path).readText()
    val binaryInput = input.map { hexToBinary(it) }.joinToString("")
    val packet = Packet.create(binaryInput)
    println(packet.execute())
}

private fun hexToBinary(hex: Char): String {
    var result = Integer.toBinaryString(hex.toString().toInt(16))
    while (result.length < 4) {
        result = "0$result"
    }
    return result
}