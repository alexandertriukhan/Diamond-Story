package gameobjects

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import enums.EffectType
import enums.JewelType
import utils.TexturesLoader

class DestroyAnimation(val x : Float, val y : Float, jewel: Jewel, val effect: EffectType) {

    private val explodeAnim = ParticleEffect()
    private val crossAnim = ParticleEffect()

    init {
        when (jewel.jewelType) {
            JewelType.RED -> explodeAnim.load(Gdx.files.internal("graphics/effects/explosion_red.p"),TexturesLoader.instance.textureAtlas)
            JewelType.BLUE -> explodeAnim.load(Gdx.files.internal("graphics/effects/explosion_blue.p"),TexturesLoader.instance.textureAtlas)
            JewelType.GREEN -> explodeAnim.load(Gdx.files.internal("graphics/effects/explosion_green.p"),TexturesLoader.instance.textureAtlas)
            JewelType.YELLOW -> explodeAnim.load(Gdx.files.internal("graphics/effects/explosion_yellow.p"),TexturesLoader.instance.textureAtlas)
            JewelType.PURPLE -> explodeAnim.load(Gdx.files.internal("graphics/effects/explosion_purple.p"),TexturesLoader.instance.textureAtlas)
        }
        if (effect == EffectType.CROSS) {
            crossAnim.load(Gdx.files.internal("graphics/effects/cross.p"),TexturesLoader.instance.textureAtlas)
            crossAnim.scaleEffect(TexturesLoader.instance.animScaleFactor)
            crossAnim.start()
        }
        explodeAnim.scaleEffect(TexturesLoader.instance.animScaleFactor * 0.8f)
        explodeAnim.start()
    }

    fun draw(batch : SpriteBatch, delta : Float, size : Float, gridOffset : Float) {
        when (effect) {
            EffectType.NONE -> {
                explodeAnim.setPosition((x * size) + size / 2,(((y * size) + gridOffset) + size / 2) - delta * 4)
                explodeAnim.draw(batch, delta * 2)
            }
            EffectType.FIRE -> {
                explodeAnim.setPosition((x * size) + size / 2,(((y * size) + gridOffset) + size / 2) - delta * 4)
                explodeAnim.draw(batch, delta * 2)
            }
            EffectType.CROSS -> {
                crossAnim.setPosition((x * size) + size / 2,(((y * size) + gridOffset) + size / 2) - delta * 4)
                crossAnim.draw(batch, delta * 2)
            }
        }
    }

    fun isStopped() : Boolean {
        when (effect) {
            EffectType.NONE -> return explodeAnim.isComplete
            EffectType.FIRE -> return explodeAnim.isComplete
            EffectType.CROSS -> return crossAnim.isComplete
        }
        return false
    }

}