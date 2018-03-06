package physics

open class Movement(val xFrom: Float,
                    val yFrom: Float,
                    val xTo: Float,
                    val yTo: Float,
                    private val startSpeed: Float = 0f,
                    private val topSpeed: Float = startSpeed,
                    private val acceleration: Float = 0f) {

    var currentSpeed = startSpeed
    var xCurrent = xFrom
    var yCurrent = yFrom

    var startBiggerX = false
    var startBiggerY = false
    var endMoveX = true
    var endMoveY = true
    var xAxis = false
    var yAxis = false

    init {
        if (xFrom != xTo) {
            xAxis = true
            endMoveX = false
            if (xFrom > xTo) {
                startBiggerX = true
            }
        }
        if (yFrom != yTo) {
            yAxis = true
            endMoveY = false
            if (yFrom > yTo) {
                startBiggerY = true
            }
        }
    }

    fun nextPosition(delta : Float) {
        if (!endMove()) {
            if (currentSpeed < topSpeed) {
                currentSpeed += acceleration
                if (currentSpeed > topSpeed) {
                    currentSpeed = topSpeed
                }
            }
            if (xAxis) {
                if (!startBiggerX) {
                    xCurrent += delta * currentSpeed
                    if (xCurrent >= xTo) {
                        endMoveX = true
                        xCurrent = xTo
                    }
                } else {
                    xCurrent -= delta * currentSpeed
                    if (xCurrent <= xTo) {
                        endMoveX = true
                        xCurrent = xTo
                    }
                }
            }
            if (yAxis){
                if (!startBiggerY) {
                    yCurrent += delta * currentSpeed
                    if (yCurrent >= yTo) {
                        endMoveY = true
                        yCurrent = yTo
                    }
                } else {
                    yCurrent -= delta * currentSpeed
                    if (yCurrent <= yTo) {
                        endMoveY = true
                        yCurrent = yTo
                    }
                }
            }
        }
    }

    fun endMove() : Boolean{
        return endMoveX && endMoveY
    }

}