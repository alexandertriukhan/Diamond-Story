package gameobjects

import animations.Flash
import animations.Rotation
import animations.Shrink
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.graphics.g2d.TextureRegion
import enums.EffectType
import enums.JewelType
import utils.TexturesLoader

class Jewel(var jewelType : JewelType) {

    private var isAnimated = false

    private val fireGemAnim = ParticleEffect()

    private val fireSelectionAnim = Rotation(TexturesLoader.instance.selectedFireSpell,40f,true)
    private val moveSelectionAnim = Rotation(TexturesLoader.instance.selectedMoveSpell,40f,true)
    private val crossGemAnim = Flash(TexturesLoader.instance.crossAnim,0.9f, true)
    private val superGemAnim = Flash(TexturesLoader.instance.superGemGlow,1.2f,true)

    init {
        fireGemAnim.load(Gdx.files.internal("graphics/effects/fire.p"),TexturesLoader.instance.textureAtlas)
        fireGemAnim.scaleEffect(TexturesLoader.instance.animScaleFactor)
    }

    var effect = EffectType.NONE
    set(value) {
        field = value
        isAnimated = (field != EffectType.NONE)
    }

    var isSelected = false

    constructor(jewelType: JewelType, effectType: EffectType) : this (jewelType) {
        effect = effectType
    }

    fun draw(batch: Batch, x : Float, y : Float, size : Float, delta : Float) {
        if (isSelected) {
            selection(batch,x,y,size,delta)
        }
        if (jewelType != JewelType.NO_JEWEL) {
            batch.draw(texture(), x, y, size, size)
        }
        if (isAnimated) {
            animation(batch,x,y,size,delta)
        }
    }

    fun texture() : TextureRegion {
        return when (jewelType) {
            JewelType.RED -> TexturesLoader.instance.redGem
            JewelType.GREEN -> TexturesLoader.instance.greenGem
            JewelType.BLUE -> TexturesLoader.instance.blueGem
            JewelType.PURPLE -> TexturesLoader.instance.purpleGem
            JewelType.YELLOW -> TexturesLoader.instance.yellowGem
            JewelType.NO_JEWEL -> TexturesLoader.instance.noGem
            JewelType.SUPER_GEM -> TexturesLoader.instance.superGem
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

    private fun selection(batch: Batch, x : Float, y : Float, size : Float, delta : Float) {
        when (effect) {
            EffectType.NONE -> moveSelectionAnim.draw(batch,x,y,size,delta)
            EffectType.FIRE -> fireSelectionAnim.draw(batch,x,y,size,delta)
        }
    }

}