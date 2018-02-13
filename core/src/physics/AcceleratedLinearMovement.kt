package physics

import enums.Axis
import interfaces.Movement

open class AcceleratedLinearMovement(override var xFrom: Float,
                                     override var yFrom: Float,
                                     override val xTo: Float,
                                     override val yTo: Float) : Movement {

    var startBigger = false
    var endMove = false
    var movingAxis = Axis.Y
    var currentSpeed = 0f

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

    fun nextPosition(delta : Float, acceleration : Float, topSpeed : Float) {
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