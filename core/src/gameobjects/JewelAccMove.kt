package gameobjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import physics.AcceleratedLinearMovement

class JewelAccMove(xFrom: Float, yFrom: Float, xTo: Float, yTo: Float, val jewel : Jewel)
    : AcceleratedLinearMovement(xFrom, yFrom, xTo, yTo) {

    var destroyOnEnd = false

    fun draw(batch : SpriteBatch, size : Float, delta : Float, gridOffset : Float) {
        jewel.draw(batch, xFrom * size, (yFrom * size) + gridOffset, size, delta)
    }
}