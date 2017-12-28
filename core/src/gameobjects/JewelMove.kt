package gameobjects

import enums.Axis

class JewelMove(var xStart : Float,
                var yStart : Float,
                val xEnd : Float,
                val yEnd : Float,
                val jewel : Jewel) {

    var startBigger = false
    var movingAxis = Axis.X

    init {
        if (xStart > xEnd) {
            startBigger = true
        }
        if (yStart > yEnd) {
            startBigger = true
        }
        if (xStart == xEnd) {
            movingAxis = Axis.Y
        }
    }

}

