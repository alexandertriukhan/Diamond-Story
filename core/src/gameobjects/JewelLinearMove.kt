package gameobjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import physics.LinearMovement

class JewelLinearMove(xFrom: Float, yFrom: Float, xTo: Float, yTo: Float, val jewel : Jewel) :
        LinearMovement(xFrom, yFrom, xTo, yTo) {

    var destroyOnEnd = false

    fun draw(batch : SpriteBatch, size : Float, delta : Float, gridOffset : Float) {
        jewel.draw(batch, xFrom * size, (yFrom * size) + gridOffset, size, delta)
    }

}

