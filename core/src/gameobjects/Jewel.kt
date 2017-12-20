package gameobjects

import com.badlogic.gdx.graphics.g2d.TextureRegion
import enums.EffectType
import enums.JewelType
import utils.TexturesLoader

class Jewel(val jewelType : JewelType, val effect : EffectType) {

    val texture : TextureRegion = when (jewelType) {
        JewelType.RED -> TexturesLoader.instance.red_gem
        JewelType.GREEN -> TexturesLoader.instance.green_gem
        JewelType.BLUE -> TexturesLoader.instance.blue_gem
        JewelType.PURPLE -> TexturesLoader.instance.purple_gem
        JewelType.YELLOW -> TexturesLoader.instance.yellow_gem
    }

}