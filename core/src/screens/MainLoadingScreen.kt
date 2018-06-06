package screens

import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion

class MainLoadingScreen(private val assetManager: AssetManager) : Screen {

    override fun hide() {
    }

    init {
        // TODO: add loading graphics atlas
        assetManager.load("graphics/atlas.atlas", TextureAtlas::class.java)
        assetManager.finishLoading()

        val textureAtlas : TextureAtlas = assetManager.get("graphics/atlas.atlas", TextureAtlas::class.java)
        val blueGem : TextureRegion = textureAtlas.findRegion("gems/blue_gem")
        //TODO: assign loading textures to variables
    }

    override fun show() {
    }

    override fun render(delta: Float) {
        //TODO: show progress
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
    }

    override fun dispose() {
        assetManager.unload("graphics/loading.atlas")
    }

}