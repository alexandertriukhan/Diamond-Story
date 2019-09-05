package gameobjects

import animations.Flash
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.graphics.g2d.TextureRegion
import enums.EffectType
import enums.JewelType
import utils.GameScreenAssets

class Jewel(var jewelType : JewelType, private val assets: GameScreenAssets) {

    private var isAnimated = false

    private val fireGemAnim = ParticleEffect(assets.fireAnimation)

    private val crossGemAnim = Flash(assets.crossAnim,0.9f, true)
    private val superGemAnim = Flash(assets.superGemGlow,1.2f,true)

    var effect = EffectType.NONE
    set(value) {
        field = value
        isAnimated = (field != EffectType.NONE)
    }

    var isSelected = false

    constructor(jewelType: JewelType, assets: GameScreenAssets, effectType: EffectType) : this (jewelType,assets) {
        effect = effectType
    }

    fun draw(batch: Batch, x : Float, y : Float, size : Float, delta : Float) {
        if (isSelected) {
            batch.draw(assets.selected,x,y,size,size)
        }
        if (jewelType != JewelType.NO_JEWEL) {
            batch.draw(texture(), x, y, size, size)
        }
        if (isAnimated) {
            animation(batch,x,y,size,delta)
        }
    }

    private fun texture() : TextureRegion {
        return when (jewelType) {
            JewelType.RED -> assets.redGem
            JewelType.GREEN -> assets.greenGem
            JewelType.BLUE -> assets.blueGem
            JewelType.PURPLE -> assets.purpleGem
            JewelType.YELLOW -> assets.yellowGem
            JewelType.NO_JEWEL -> assets.noGem
            JewelType.SUPER_GEM -> assets.superGem
        }
    }

    private fun animation(batch: Batch, x : Float, y : Float, size : Float, delta : Float) {
        when (effect) {
            EffectType.NONE -> return
            EffectType.FIRE -> {
                fireGemAnim.setPosition(x + size / 2,y + size / 4)
                fireGemAnim.draw(batch, delta)
            }
            EffectType.CROSS -> crossGemAnim.draw(batch,x,y,size,delta)
            EffectType.SUPER_GEM -> superGemAnim.draw(batch,x,y,size,delta)
        }
    }

}
