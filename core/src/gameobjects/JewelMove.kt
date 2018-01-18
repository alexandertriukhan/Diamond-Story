package gameobjects

import enums.Axis

class JewelMove(var xStart : Float,
                var yStart : Float,
                val xEnd : Float,
                val yEnd : Float,
                val jewel : Jewel) {

    var startBigger = false
    var movingAxis = Axis.Y

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

}

