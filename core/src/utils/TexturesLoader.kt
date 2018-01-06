package utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion

class TexturesLoader {
    private val textureAtlas = TextureAtlas(Gdx.files.internal("graphics/atlas.atlas"))

    val redGem : TextureRegion = textureAtlas.findRegion("red_gem")
    val greenGem : TextureRegion = textureAtlas.findRegion("green_gem")
    val blueGem : TextureRegion = textureAtlas.findRegion("blue_gem")
    val purpleGem : TextureRegion = textureAtlas.findRegion("purple_gem")
    val yellowGem : TextureRegion = textureAtlas.findRegion("yellow_gem")
    val noGem : TextureRegion = textureAtlas.findRegion("no_gem")

    val tileBlank : TextureRegion = textureAtlas.findRegion("tile_blank")
    val tileTop : TextureRegion = textureAtlas.findRegion("tile_top")
    val tileDown : TextureRegion = textureAtlas.findRegion("tile_down")
    val tileRight : TextureRegion = textureAtlas.findRegion("tile_right")
    val tileLeft : TextureRegion = textureAtlas.findRegion("tile_left")
    val tileCornerLeftTop : TextureRegion = textureAtlas.findRegion("tile_corner_left_top")
    val tileCornerRightTop : TextureRegion = textureAtlas.findRegion("tile_corner_right_top")
    val tileCornerLeftDown : TextureRegion = textureAtlas.findRegion("tile_corner_left_down")
    val tileCornerRightDown : TextureRegion = textureAtlas.findRegion("tile_corner_right_down")

    val selectedGem : TextureRegion = textureAtlas.findRegion("selected")

    //val fireAnim = Animation<TextureRegion>(0.033f, textureAtlas.findRegions("fire"), Animation.PlayMode.LOOP)

    private object Holder { val INSTANCE = TexturesLoader() }

    companion object {
        val instance: TexturesLoader by lazy { Holder.INSTANCE }
    }
}