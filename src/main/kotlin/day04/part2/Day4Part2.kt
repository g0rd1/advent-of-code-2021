import java.io.File

fun main() {
    val path = "src\\main\\kotlin\\day04\\input.txt"
    val input = File(path).readLines()
    val winNumbers = input[0].split(",")
    val boardSize = 5
    val boards = (2 until input.size step boardSize + 1).map { inputBoardFirstRowIndex ->
        (0 until boardSize).map { boardRowIndex ->
            input[inputBoardFirstRowIndex + boardRowIndex].split(" ").filterNot { it.isBlank() }
        }
    }
    val (lastWinBoard, markedNumbersCount) = getLastWinBoardAndMarkedNumbersCount(winNumbers, boards)
    val sumOfAllUnmarkedNumbers = lastWinBoard
        .flatten()
        .filter { it !in winNumbers.take(markedNumbersCount) }
        .sumOf { it.toInt() }
    println(sumOfAllUnmarkedNumbers * winNumbers[markedNumbersCount - 1].toInt())
}

private fun getLastWinBoardAndMarkedNumbersCount(
    winNumbers: List<String>,
    boards: List<List<List<String>>>
): Pair<List<List<String>>, Int> {
    for (markedNumbersCountToCheck in 1..winNumbers.size) {
        if (boards.none { !isWinBoard(it, winNumbers.take(markedNumbersCountToCheck)) }) {
            val lastLoseBoard = boards.first { !isWinBoard(it, winNumbers.take(markedNumbersCountToCheck - 1)) }
            return lastLoseBoard to markedNumbersCountToCheck
        }
    }
    throw IllegalStateException()
}

private fun isWinBoard(board: List<List<String>>, winNumbers: List<String>): Boolean {
    val boardSize = board.size
    for (row in 0 until boardSize) {
        for (column in 0 until boardSize) {
            if (board[row][column] !in winNumbers) break
            if (column == boardSize - 1) return true
        }
    }
    for (column in 0 until boardSize) {
        for (row in 0 until boardSize) {
            if (board[row][column] !in winNumbers) break
            if (row == boardSize - 1) return true
        }
    }
    return false
}