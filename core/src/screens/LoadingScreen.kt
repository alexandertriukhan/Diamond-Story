package screens

import com.alextriukhan.match3.DiamondStoryGame
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader
import com.badlogic.gdx.math.Interpolation
import enums.Screens

class LoadingScreen(private val assetManager: AssetManager,
                    private val dGame: DiamondStoryGame,
                    private val screenToLoad: Screens) : Screen {

    private var textureAtlas = TextureAtlas()
    private var blueGem = TextureRegion()
    private val batcher = SpriteBatch()
    private var percent = 0f

    init {
        assetManager.load("graphics/LoadingScreen.atlas", TextureAtlas::class.java)
        assetManager.finishLoading()
        textureAtlas = assetManager.get("graphics/LoadingScreen.atlas", TextureAtlas::class.java)
        blueGem = textureAtlas.findRegion("blue_gem")
        when (screenToLoad) {
            Screens.GAME_SCREEN -> setToLoadGameScreenResources()
            Screens.MAIN_MENU_SCREEN -> setToLoadMainMenuScreenResources()
            Screens.GAME_MAP_SCREEN -> setToLoadGameMapScreenResources()
        }
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
            when (screenToLoad) {
                Screens.GAME_SCREEN -> dGame.replaceScreen(GameScreen(gridTypes.square(), assetManager))
                Screens.MAIN_MENU_SCREEN -> dGame.replaceScreen(MainMenuScreen())
                Screens.GAME_MAP_SCREEN -> dGame.replaceScreen(GameMapScreen())
            }
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

    private fun setToLoadGameScreenResources() {
        assetManager.apply {
            load("graphics/GameScreen.atlas",TextureAtlas::class.java)
            load("graphics/effects/fire.p", ParticleEffect::class.java)
            load("graphics/effects/explosion_red.p", ParticleEffect::class.java)
            load("graphics/effects/explosion_blue.p", ParticleEffect::class.java)
            load("graphics/effects/explosion_green.p", ParticleEffect::class.java)
            load("graphics/effects/explosion_yellow.p", ParticleEffect::class.java)
            load("graphics/effects/explosion_purple.p", ParticleEffect::class.java)
            load("graphics/effects/cross.p", ParticleEffect::class.java)
            val resolver = InternalFileHandleResolver()
            setLoader(FreeTypeFontGenerator::class.java, FreeTypeFontGeneratorLoader(resolver))
            setLoader(BitmapFont::class.java,".ttf", FreetypeFontLoader(resolver))
            // TODO: fonts gonna be loaded only once in DiamondStoryGame
            val font32 = FreetypeFontLoader.FreeTypeFontLoaderParameter()
            font32.fontFileName = "fonts/JollyLodger-Regular.ttf"
            font32.fontParameters.size = (32f * (Gdx.graphics.width.toFloat() / 520f)).toInt()  // 520f used as a referenced width
            font32.fontParameters.shadowOffsetX = 3
            font32.fontParameters.shadowOffsetY = 3
            load("fonts/JollyLodger-Regular.ttf", BitmapFont::class.java,font32)
        }
    }

    private fun setToLoadMainMenuScreenResources() {

    }

    private fun setToLoadGameMapScreenResources() {

    }

}