package gameobjects

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import enums.EffectType
import enums.JewelType
import physics.Movement
import utils.GameScreenAssets

class DestroyAnimation(assets: GameScreenAssets,
                       val x : Float,
                       val y : Float,
                       jewel: Jewel,
                       private val effect: EffectType,
                       val size: Float,
                       private val gridOffset: Float,
                       val score : Int = 0) {

    // TODO: font with the same position for different screen sizes
    private var explodeAnim = ParticleEffect()
    private var crossAnim = ParticleEffect()
    private var font = assets.fontScore
    private var fontColor = Color()
    private val scoreMovement = Movement((x * size) + size / 2,(((y * size) + gridOffset) + size / 2),
            (x * size) + size / 2,(((y * size) + gridOffset) + size * 2f),96f)

    init {
        when (jewel.jewelType) {
            JewelType.RED -> {
                explodeAnim =  ParticleEffect(assets.redExplosion)
                fontColor = Color.RED
            }
            JewelType.BLUE -> {
                explodeAnim =  ParticleEffect(assets.blueExplosion)
                fontColor = Color.BLUE
            }
            JewelType.GREEN -> {
                explodeAnim =  ParticleEffect(assets.greenExplosion)
                fontColor = Color.GREEN
            }
            JewelType.YELLOW -> {
                explodeAnim =  ParticleEffect(assets.yellowExplosion)
                fontColor = Color.YELLOW
            }
            JewelType.PURPLE -> {
                explodeAnim = ParticleEffect(assets.purpleExplosion)
                fontColor = Color.PURPLE
            }
            // TODO: change
            JewelType.SUPER_GEM -> {
                explodeAnim =  ParticleEffect(assets.purpleExplosion)
                fontColor = Color.BLACK
            }
        }
        if (effect == EffectType.CROSS) {
            crossAnim = ParticleEffect(assets.crossAnimation)
            crossAnim.start()
        }
        explodeAnim.scaleEffect(assets.animScaleFactor * 0.8f)
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
            font.color = fontColor
            val fontLayout =  GlyphLayout(font,"+" + score.toString())
            font.draw(batch,fontLayout,scoreMovement.xCurrent - (fontLayout.width / 2),scoreMovement.yCurrent + (fontLayout.height / 2))
        }
    }

}