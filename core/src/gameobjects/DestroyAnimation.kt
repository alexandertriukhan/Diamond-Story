package gameobjects

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import enums.EffectType
import enums.JewelType
import physics.Movement
import utils.TexturesLoader

class DestroyAnimation(val x : Float,
                       val y : Float,
                       jewel: Jewel,
                       private val effect: EffectType,
                       val size: Float,
                       private val gridOffset: Float,
                       private val score : Int = 0) {

    private val explodeAnim = ParticleEffect()
    private val crossAnim = ParticleEffect()
    private var font = TexturesLoader.instance.fontScore
    private val scoreMovement = Movement((x * size) + size / 2,(((y * size) + gridOffset) + size / 1.5f),
            (x * size) + size / 2,(((y * size) + gridOffset) + size),96f)

    init {
        when (jewel.jewelType) {
            JewelType.RED -> {
                explodeAnim.load(Gdx.files.internal("graphics/effects/explosion_red.p"),TexturesLoader.instance.textureAtlas)
                font.color = Color.RED
            }
            JewelType.BLUE -> {
                explodeAnim.load(Gdx.files.internal("graphics/effects/explosion_blue.p"), TexturesLoader.instance.textureAtlas)
                font.color = Color.BLUE
            }
            JewelType.GREEN -> {
                explodeAnim.load(Gdx.files.internal("graphics/effects/explosion_green.p"),TexturesLoader.instance.textureAtlas)
                font.color = Color.GREEN
            }
            JewelType.YELLOW -> {
                explodeAnim.load(Gdx.files.internal("graphics/effects/explosion_yellow.p"),TexturesLoader.instance.textureAtlas)
                font.color = Color.YELLOW
            }
            JewelType.PURPLE -> {
                explodeAnim.load(Gdx.files.internal("graphics/effects/explosion_purple.p"),TexturesLoader.instance.textureAtlas)
                font.color = Color.PURPLE
            }
            // TODO: change
            JewelType.SUPER_GEM -> {
                explodeAnim.load(Gdx.files.internal("graphics/effects/explosion_purple.p"),TexturesLoader.instance.textureAtlas)
                font.color = Color.BLACK
            }
        }
        if (effect == EffectType.CROSS) {
            crossAnim.load(Gdx.files.internal("graphics/effects/cross.p"),TexturesLoader.instance.textureAtlas)
            crossAnim.scaleEffect(TexturesLoader.instance.animScaleFactor)
            crossAnim.start()
        }
        explodeAnim.scaleEffect(TexturesLoader.instance.animScaleFactor * 0.8f)
        explodeAnim.start()
    }

    fun draw(batch : SpriteBatch, delta : Float) {
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
        drawScore(batch,delta)
    }

    fun isStopped() : Boolean {
        when (effect) {
            EffectType.NONE -> return explodeAnim.isComplete
            EffectType.FIRE -> return explodeAnim.isComplete
            EffectType.CROSS -> return crossAnim.isComplete
        }
        return false
    }

    private fun drawScore(batch : SpriteBatch, delta : Float) {
        if (score != 0) {
            scoreMovement.nextPosition(delta)
            font.draw(batch,"+" + score.toString(),scoreMovement.xCurrent,scoreMovement.yCurrent)
        }
    }

}