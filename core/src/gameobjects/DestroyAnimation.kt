package gameobjects

import animations.Shrink
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import enums.EffectType

class DestroyAnimation(val x : Float, val y : Float, val jewel: Jewel) {

    private val shrinkAnim = Shrink(jewel.texture(),5f,false)

    fun draw(batch : SpriteBatch, delta : Float, size : Float, gridOffset : Float) {
        when (jewel.effect) {
            EffectType.NONE -> shrinkAnim.draw(batch,x * size,(y * size) + gridOffset,size,delta)
            EffectType.CROSS -> shrinkAnim.isStopped  // TODO: change
        }
    }

    fun isStopped() : Boolean {
        when (jewel.effect) {
            EffectType.NONE -> return shrinkAnim.isStopped
            EffectType.CROSS -> return shrinkAnim.isStopped  // TODO: change
        }
        return false
    }

}