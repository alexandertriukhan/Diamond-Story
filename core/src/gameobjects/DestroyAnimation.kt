package gameobjects

import animations.Shrink
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import enums.EffectType
import utils.TexturesLoader

class DestroyAnimation(val x : Float, val y : Float, jewel: Jewel, val effect: EffectType) {

    private val shrinkAnim = Shrink(jewel.texture(),5f,false)  // ORIGINAL : 5f
    private val explodeAnim = ParticleEffect()

    init {
        explodeAnim.load(Gdx.files.internal("graphics/effects/explosion.p"),TexturesLoader.instance.textureAtlas)
        explodeAnim.start()
    }

    fun draw(batch : SpriteBatch, delta : Float, size : Float, gridOffset : Float) {
        when (effect) {
            EffectType.NONE -> shrinkAnim.draw(batch,x * size,(y * size) + gridOffset,size,delta)
            EffectType.FIRE -> {
                explodeAnim.setPosition((x * size) + size / 2,((y * size) + gridOffset) + size / 2)
                explodeAnim.draw(batch, delta * 2)
            }
            EffectType.CROSS -> shrinkAnim.draw(batch,x * size,(y * size) + gridOffset,size,delta)  // TODO: change
        }
    }

    fun isStopped() : Boolean {
        when (effect) {
            EffectType.NONE -> return shrinkAnim.isStopped
            EffectType.FIRE -> return explodeAnim.isComplete
            EffectType.CROSS -> return shrinkAnim.isStopped  // TODO: change
        }
        return false
    }

}