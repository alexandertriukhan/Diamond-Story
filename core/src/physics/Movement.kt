package physics

import enums.Axis

open class Movement(var xFrom: Float,
                    var yFrom: Float,
                    val xTo: Float,
                    val yTo: Float,
                    private val startSpeed: Float = 0f,
                    private val topSpeed: Float = startSpeed,
                    private val acceleration: Float = 0f) {

    var startBigger = false
    var endMove = false
    var movingAxis = Axis.Y
    var currentSpeed = startSpeed

    init {
        if (xFrom != xTo) {
            movingAxis = Axis.X
        }
        if (movingAxis == Axis.X) {
            if (xFrom > xTo) {
                startBigger = true
            }
        } else {
            if (yFrom > yTo) {
                startBigger = true
            }
        }
    }

    fun nextPosition(delta : Float) {
        if (!endMove) {
            if (currentSpeed < topSpeed) {
                currentSpeed += acceleration
                if (currentSpeed > topSpeed) {
                    currentSpeed = topSpeed
                }
            }
            if (movingAxis == Axis.X) {
                if (!startBigger) {
                    xFrom += delta * currentSpeed
                    if (xFrom >= xTo) {
                        endMove = true
                        xFrom = xTo
                    }
                } else {
                    xFrom -= delta * currentSpeed
                    if (xFrom <= xTo) {
                        endMove = true
                        xFrom = xTo
                    }
                }
            } else {
                if (!startBigger) {
                    yFrom += delta * currentSpeed
                    if (yFrom >= yTo) {
                        endMove = true
                        yFrom = yTo
                    }
                } else {
                    yFrom -= delta * currentSpeed
                    if (yFrom <= yTo) {
                        endMove = true
                        yFrom = yTo
                    }
                }
            }
        }
    }

}