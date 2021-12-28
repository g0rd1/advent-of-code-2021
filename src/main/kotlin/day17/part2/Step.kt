package day17.part2

data class Step(val x: Int, val y: Int, val xVelocity: Int, val yVelocity: Int) {

    fun willGetToCoordinates(xRange: IntRange, yRange: IntRange): Boolean {
        return willGetToCoordinates(xRange, yRange, this)
    }

    private fun willGetToCoordinates(
        xRange: IntRange,
        yRange: IntRange,
        step: Step,
    ): Boolean {
        if (step.x in xRange && step.y in yRange) return true
        if (step.x < xRange.first && step.xVelocity <= 0) return false
        if (step.x > xRange.last && step.xVelocity >= 0) return false
        if (step.y < yRange.first && step.yVelocity <= 0) return false
        return willGetToCoordinates(xRange, yRange, step.getNextStep())
    }

    private fun getNextStep(): Step {
        val nextX = this.x + xVelocity
        val nextY = this.y + yVelocity
        val nextXVelocity = when {
            this.xVelocity > 0 -> this.xVelocity - 1
            this.xVelocity < 0 -> this.xVelocity + 1
            else -> 0
        }
        val nextYVelocity = this.yVelocity - 1
        return Step(nextX, nextY, nextXVelocity, nextYVelocity)
    }

}
