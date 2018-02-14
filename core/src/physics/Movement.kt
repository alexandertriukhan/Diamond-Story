package physics

import enums.Axis

open class Movement(val xFrom: Float,
                    val yFrom: Float,
                    val xTo: Float,
                    val yTo: Float,
                    private val startSpeed: Float = 0f,
                    private val topSpeed: Float = startSpeed,
                    private val acceleration: Float = 0f) {

    var xCurrent = xFrom
    var yCurrent = yFrom

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
                    xCurrent += delta * currentSpeed
                    if (xCurrent >= xTo) {
                        endMove = true
                        xCurrent = xTo
                    }
                } else {
                    xCurrent -= delta * currentSpeed
                    if (xCurrent <= xTo) {
                        endMove = true
                        xCurrent = xTo
                    }
                }
            } else {
                if (!startBigger) {
                    yCurrent += delta * currentSpeed
                    if (yCurrent >= yTo) {
                        endMove = true
                        yCurrent = yTo
                    }
                } else {
                    yCurrent -= delta * currentSpeed
                    if (yCurrent <= yTo) {
                        endMove = true
                        yCurrent = yTo
                    }
                }
            }
        }
    }

}