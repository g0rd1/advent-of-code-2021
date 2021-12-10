import java.io.File

fun main() {
    val path = "src\\main\\kotlin\\day2\\input.txt"
    val commands = File(path).readLines()
    val position = commands.fold(0 to 0) { (horizontalPosition, depth), command ->
        val split = command.split(" ")
        val commandType = split[0]
        val value = split[1].toInt()
        when (commandType) {
            "forward" -> horizontalPosition + value to depth
            "down" -> horizontalPosition to depth + value
            "up" -> horizontalPosition to depth - value
            else -> horizontalPosition to depth
        }
    }
    println(position.first * position.second)
}