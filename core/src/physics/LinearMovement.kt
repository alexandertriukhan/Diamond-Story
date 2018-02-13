package physics

import enums.Axis
import interfaces.Movement

open class LinearMovement(final override var xFrom: Float,
                          final override var yFrom: Float,
                          final override val xTo: Float,
                          final override val yTo: Float) : Movement{

    var startBigger = false
    var endMove = false
    var movingAxis = Axis.Y

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

    fun nextPosition(delta : Float, speed : Float) {
        if (!endMove) {
            if (movingAxis == Axis.X) {
                if (!startBigger) {
                    xFrom += delta * speed
                    if (xFrom >= xTo) {
                        endMove = true
                        xFrom = xTo
                    }
                } else {
                    xFrom -= delta * speed
                    if (xFrom <= xTo) {
                        endMove = true
                        xFrom = xTo
                    }
                }
            } else {
                if (!startBigger) {
                    yFrom += delta * speed
                    if (yFrom >= yTo) {
                        endMove = true
                        yFrom = yTo
                    }
                } else {
                    yFrom -= delta * speed
                    if (yFrom <= yTo) {
                        endMove = true
                        yFrom = yTo
                    }
                }
            }
        }
    }

}