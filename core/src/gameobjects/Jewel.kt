package gameobjects

import com.badlogic.gdx.graphics.g2d.TextureRegion
import enums.EffectType
import enums.JewelType
import utils.TexturesLoader

class Jewel(var jewelType : JewelType) {

    var animated = false
    var effect = EffectType.NONE
    set(value) {
        field = value
        animated = (field == EffectType.NONE)
    }
    //private val fireTextures = TexturesLoader.instance.fireAnim

    constructor(jewelType: JewelType, effectType: EffectType) : this (jewelType) {
        effect = effectType
    }

    fun texture() : TextureRegion {
        return when (jewelType) {
            JewelType.RED -> TexturesLoader.instance.redGem
            JewelType.GREEN -> TexturesLoader.instance.greenGem
            JewelType.BLUE -> TexturesLoader.instance.blueGem
            JewelType.PURPLE -> TexturesLoader.instance.purpleGem
            JewelType.YELLOW -> TexturesLoader.instance.yellowGem
            JewelType.NO_JEWEL -> TexturesLoader.instance.noGem
        }
    }

//    fun animation(delta : Float) : TextureRegion {
//        if (effect == EffectType.FIRE)
//            return fireTextures.getKeyFrame(delta)
//        return TexturesLoader.instance.noGem
//    }

}