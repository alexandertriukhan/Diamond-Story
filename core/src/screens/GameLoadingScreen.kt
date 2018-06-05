package screens

import com.alextriukhan.match3.DiamondStoryGame
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Interpolation

class GameLoadingScreen(private val assetManager: AssetManager, private val dGame: DiamondStoryGame) : Screen {

    private var textureAtlas = TextureAtlas()
    private var blueGem = TextureRegion()
    private val batcher = SpriteBatch()
    private var percent = 0f

    init {
        assetManager.load("graphics/LoadingScreen.atlas", TextureAtlas::class.java)
        assetManager.finishLoading()
        textureAtlas = assetManager.get("graphics/LoadingScreen.atlas", TextureAtlas::class.java)
        blueGem = textureAtlas.findRegion("blue_gem")
        dGame.setResourcesToBeLoaded()
    }

    override fun show() {
    }

    override fun hide() {
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0 / 255f, 0 / 255f, 0 / 255f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        percent = Interpolation.linear.apply(percent, assetManager.progress, 0.1f)
        println("Percentage: " + percent)
        batcher.begin()
        batcher.draw(blueGem,40f,200f)
        batcher.end()

        if (assetManager.update()) {
            dGame.replaceScreen(GameScreen(gridTypes.square(),assetManager))
        }
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
    }

    override fun dispose() {
        assetManager.unload("graphics/LoadingScreen.atlas")
    }

}