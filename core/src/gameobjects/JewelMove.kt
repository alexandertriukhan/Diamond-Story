package gameobjects

import enums.Axis

class JewelMove(var xStart : Float,
                var yStart : Float,
                val xEnd : Float,
                val yEnd : Float,
                val jewel : Jewel) {

    var startBigger = false
    var movingAxis = Axis.Y
    var destroyOnEnd = false // TODO : implement logic
    var endMove = false

    init {
        if (xStart != xEnd) {
            movingAxis = Axis.X
        }
        if (movingAxis == Axis.X) {
            if (xStart > xEnd) {
                startBigger = true
            }
        } else {
            if (yStart > yEnd) {
                startBigger = true
            }
        }
    }

    fun perform(delta : Float, speed : Float) {
        if (!endMove) {
            if (movingAxis == Axis.X) {
                if (!startBigger) {
                    xStart += delta * speed
                    if (xStart >= xEnd) {
                        endMove = true
                        xStart = xEnd
                    }
                } else {
                    xStart -= delta * speed
                    if (xStart <= xEnd) {
                        endMove = true
                        xStart = xEnd
                    }
                }
            } else {
                if (!startBigger) {
                    yStart += delta * speed
                    if (yStart >= yEnd) {
                        endMove = true
                        yStart = yEnd
                    }
                } else {
                    yStart -= delta * speed
                    if (yStart <= yEnd) {
                        endMove = true
                        yStart = yEnd
                    }
                }
            }
        }
    }

}

