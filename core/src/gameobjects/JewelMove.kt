package gameobjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import physics.Movement

class JewelMove(xFrom: Float, yFrom: Float, xTo: Float, yTo: Float, val jewel : Jewel,
                startSpeed: Float, topSpeed: Float = startSpeed, acceleration: Float = 0f) :
        Movement(xFrom, yFrom, xTo, yTo, startSpeed, topSpeed, acceleration) {

    var destroyOnEnd = false

    fun draw(batch : SpriteBatch, size : Float, delta : Float, gridOffset : Float) {
        nextPosition(delta)
        jewel.draw(batch, xFrom * size, (yFrom * size) + gridOffset, size, delta)
    }

}

