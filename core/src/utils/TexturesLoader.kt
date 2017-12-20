package utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion

class TexturesLoader {
    private val textureAtlas = TextureAtlas(Gdx.files.internal("graphics/atlas.atlas"))

    val red_gem : TextureRegion = textureAtlas.findRegion("red_gem")
    val green_gem : TextureRegion = textureAtlas.findRegion("green_gem")
    val blue_gem : TextureRegion = textureAtlas.findRegion("blue_gem")
    val purple_gem : TextureRegion = textureAtlas.findRegion("blue_gem")
    val yellow_gem : TextureRegion = textureAtlas.findRegion("blue_gem")

    private object Holder { val INSTANCE = TexturesLoader() }

    companion object {
        val instance: TexturesLoader by lazy { Holder.INSTANCE }
    }
}