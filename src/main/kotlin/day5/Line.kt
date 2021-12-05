package day5

data class Line(val startPoint: Point, val endPoint: Point) {

    fun getPoints(): List<Point> {
        return getPoints(true)
    }

    fun getPointsWithoutDiagonals(): List<Point> {
        return getPoints(false)
    }

    private fun getPoints(withDiagonal: Boolean): List<Point> {
        return when {
            startPoint.x == endPoint.x -> getVerticalLinePoints()
            startPoint.y == endPoint.y -> getHorizontalLinePoints()
            else -> if (withDiagonal) getDiagonalPoints() else emptyList()
        }
    }

    private fun getVerticalLinePoints(): List<Point> {
        val range = if (startPoint.y < endPoint.y) {
            (startPoint.y..endPoint.y)
        } else {
            (startPoint.y downTo endPoint.y)
        }
        return range.map { Point(startPoint.x, it) }
    }

    private fun getHorizontalLinePoints(): List<Point> {
        val range = if (startPoint.x < endPoint.x) {
            (startPoint.x..endPoint.x)
        } else {
            (startPoint.x downTo endPoint.x)
        }
        return range.map { Point(it, startPoint.y) }
    }

    private fun getDiagonalPoints(): List<Point> {
        val diffX = endPoint.x - startPoint.x
        val diffY = endPoint.y - startPoint.y
        val xAdditions = (if (diffX > 0) (0..diffX) else (0 downTo diffX)).toList()
        val yAdditions = (if (diffY > 0) (0..diffY) else (0 downTo diffY)).toList()
        return (xAdditions.indices).map { index ->
            Point(startPoint.x + xAdditions[index], startPoint.y + yAdditions[index])
        }
    }
}
