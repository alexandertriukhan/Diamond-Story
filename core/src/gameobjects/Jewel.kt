package gameobjects

import com.badlogic.gdx.graphics.g2d.TextureRegion
import enums.EffectType
import enums.JewelType
import utils.TexturesLoader

class Jewel(val jewelType : JewelType, val effect : EffectType) {

    val texture : TextureRegion = when (jewelType) {
        JewelType.RED -> TexturesLoader.instance.redGem
        JewelType.GREEN -> TexturesLoader.instance.greenGem
        JewelType.BLUE -> TexturesLoader.instance.blueGem
        JewelType.PURPLE -> TexturesLoader.instance.purpleGem
        JewelType.YELLOW -> TexturesLoader.instance.yellowGem
    }

}