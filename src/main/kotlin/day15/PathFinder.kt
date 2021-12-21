package day15

class PathFinder(private val positionToRiskLevel: Map<Position, Int>) {

    private val maxX = positionToRiskLevel.keys.maxOf { it.x }

    private val maxY = positionToRiskLevel.keys.maxOf { it.y }

    fun getMinRiskLevel(): Long {
        val positionToPathRiskLevel: MutableMap<Position, Long> = mutableMapOf()
        positionToPathRiskLevel[Position(0, 0)] = 0
        val queue = ArrayDeque<Position>()
        val sortedPositionsToRiskLevel = positionToRiskLevel.keys
            .toList()
            .sortedBy { it.x + it.y }
        queue.addAll(sortedPositionsToRiskLevel)
        while (queue.isNotEmpty()) {
            val notVisitedPosition = queue.removeFirst()
            getPossibleAdjacentPositions(notVisitedPosition).forEach {
                val positionPathRiskLevel = positionToPathRiskLevel[it]
                val riskLevelForGoingFromNotVisitedPosition =
                    positionToPathRiskLevel[notVisitedPosition]!! + positionToRiskLevel[it]!!
                if (positionPathRiskLevel == null || positionPathRiskLevel > riskLevelForGoingFromNotVisitedPosition) {
                    positionToPathRiskLevel[it] = riskLevelForGoingFromNotVisitedPosition
                    if (it !in queue) queue.addFirst(it)
                }
            }
        }
        return positionToPathRiskLevel[Position(maxX, maxY)]!!
    }

    private fun getPossibleAdjacentPositions(position: Position): List<Position> {
        return listOf(
            Position(position.x, position.y + 1),
            Position(position.x, position.y - 1),
            Position(position.x - 1, position.y),
            Position(position.x + 1, position.y),
        ).filter {
            if (it.x < 0) return@filter false
            if (it.y < 0) return@filter false
            if (it.x > maxX) return@filter false
            if (it.y > maxY) return@filter false
            true
        }
    }

}
