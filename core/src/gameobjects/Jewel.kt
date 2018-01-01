package gameobjects

import com.badlogic.gdx.graphics.g2d.TextureRegion
import enums.EffectType
import enums.JewelType
import utils.TexturesLoader

class Jewel(var jewelType : JewelType, var effect : EffectType) {

    //val animationTextures = List<TextureRegion>(1, {_ -> TexturesLoader.instance.})

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

    //fun animation() : TextureRegion { }

    fun update(delta : Float) {

    }
}