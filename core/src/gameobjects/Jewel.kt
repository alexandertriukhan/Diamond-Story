package gameobjects

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import enums.EffectType
import enums.JewelType
import utils.TexturesLoader

class Jewel(var jewelType : JewelType) {

    private val animationSpeed = 80f
    private var animRotation = 0f
    private val selectionSpeed = 40f
    private var selectionRotation = 0f
    private var isAnimated = false

    var effect = EffectType.NONE
    set(value) {
        field = value
        isAnimated = (field != EffectType.NONE)
        animRotation = 0f
    }
    var isSelected = false

    constructor(jewelType: JewelType, effectType: EffectType) : this (jewelType) {
        effect = effectType
    }

    fun draw(batch: Batch, x : Float, y : Float, size : Float, delta : Float) {
        if (isSelected) {
            selectionRotation += delta
            batch.draw(selection(), x, y, size / 2f, size / 2f, size, size,
                    1f, 1f, -selectionRotation * selectionSpeed)
        }
        batch.draw(texture(), x, y, size, size)
        if (isAnimated) {
            animRotation += delta
            batch.draw(animation(), x, y, size / 2f, size / 2f, size, size,
                    1f, 1f, -animRotation * animationSpeed)
        }
    }

    private fun texture() : TextureRegion {
        return when (jewelType) {
            JewelType.RED -> TexturesLoader.instance.redGem
            JewelType.GREEN -> TexturesLoader.instance.greenGem
            JewelType.BLUE -> TexturesLoader.instance.blueGem
            JewelType.PURPLE -> TexturesLoader.instance.purpleGem
            JewelType.YELLOW -> TexturesLoader.instance.yellowGem
            JewelType.NO_JEWEL -> TexturesLoader.instance.noGem
        }
    }

    private fun animation() : TextureRegion {
        if (effect == EffectType.FIRE)
            return TexturesLoader.instance.fireAnim
        return TexturesLoader.instance.noGem
    }

    private fun selection() : TextureRegion {
        if (effect == EffectType.NONE)
            return TexturesLoader.instance.selectedMoveSpell
        if (effect == EffectType.FIRE)
            return TexturesLoader.instance.selectedFireSpell
        return TexturesLoader.instance.noGem
    }

}