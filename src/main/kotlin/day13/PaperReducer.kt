package day13

object PaperReducer {

    fun splitAlongY(paper: Paper, y: Int): Paper {
        val positions = paper.positions
        val up = positions.filterIndexed { rowIndex, _ ->
            rowIndex < y
        }
        val newPositions = up.mapIndexed { rowIndex, row ->
            row.mapIndexed { columnIndex, value ->
                val mirrorRowIndex = 2 * y - rowIndex
//                val mirrorPositionValue = if (mirrorRowIndex < positions.size) {
//                    positions[mirrorRowIndex][columnIndex]
//                } else {
//                    throw IllegalArgumentException()
//                }
                value || positions[mirrorRowIndex][columnIndex]
            }
        }
        return Paper(newPositions)
    }

    fun splitAlongX(paper: Paper, x: Int): Paper {
        val positions = paper.positions
        val left = positions.map { rows ->
            rows.filterIndexed { columnIndex, _ ->
                columnIndex < x
            }
        }
        val newPositions = left.mapIndexed { rowIndex, row ->
            row.mapIndexed { columnIndex, value ->
                val mirrorColumnIndex = 2 * x - columnIndex
//                val mirrorPositionValue = if (mirrorColumnIndex < positions[0].size) {
//                    positions[rowIndex][mirrorColumnIndex]
//                } else {
//                    throw IllegalArgumentException()
//                }
                value || positions[rowIndex][mirrorColumnIndex]
            }
        }
        return Paper(newPositions)
    }

}