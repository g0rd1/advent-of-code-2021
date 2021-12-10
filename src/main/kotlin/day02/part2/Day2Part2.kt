import java.io.File

fun main() {
    val path = "src\\main\\kotlin\\day2\\input.txt"
    val commands = File(path).readLines()
    val position = commands.fold(Triple(0, 0, 0)) { (horizontalPosition, depth, aim), command ->
        val split = command.split(" ")
        val commandType = split[0]
        val value = split[1].toInt()
        when (commandType) {
            "forward" -> Triple(horizontalPosition + value, depth + aim * value, aim)
            "down" -> Triple(horizontalPosition, depth, aim + value)
            "up" -> Triple(horizontalPosition, depth, aim - value)
            else -> Triple(horizontalPosition, depth, aim)
        }
    }
    println(position.first * position.second)
}