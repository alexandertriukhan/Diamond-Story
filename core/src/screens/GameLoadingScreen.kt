package screens

import com.alextriukhan.match3.DiamondStoryGame
import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.TextureAtlas

class GameLoadingScreen(private val assetManager:AssetManager) : Screen {

    override fun hide() {
    }

    init {
        // TODO: add loading graphics atlas
        assetManager.load("graphics/loading.atlas", TextureAtlas::class.java)
        assetManager.finishLoading()
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