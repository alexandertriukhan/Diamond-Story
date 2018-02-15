package gameobjects

import animations.Shrink
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import enums.EffectType
import utils.TexturesLoader

class DestroyAnimation(val x : Float, val y : Float, val jewel: Jewel, val effect: EffectType) {

    private val shrinkAnim = Shrink(jewel.texture(),2f,false)  // ORIGINAL : 5f
    private val explodeAnim = Shrink(TexturesLoader.instance.explosion,1f,false)  // ORIGINAL : 4f

    fun draw(batch : SpriteBatch, delta : Float, size : Float, gridOffset : Float) {
        when (effect) {
            EffectType.NONE -> shrinkAnim.draw(batch,x * size,(y * size) + gridOffset,size,delta)
            EffectType.FIRE -> explodeAnim.draw(batch,x * size,(y * size) + gridOffset,size,delta)
            EffectType.CROSS -> shrinkAnim.draw(batch,x * size,(y * size) + gridOffset,size,delta)  // TODO: change
        }
    }

    fun isStopped() : Boolean {
        when (effect) {
            EffectType.NONE -> return shrinkAnim.isStopped
            EffectType.FIRE -> return explodeAnim.isStopped
            EffectType.CROSS -> return shrinkAnim.isStopped  // TODO: change
        }
        return false
    }

}