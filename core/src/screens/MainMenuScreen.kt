package screens

import com.alextriukhan.match3.DiamondStoryGame
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.net.HttpRequestBuilder
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import enums.Screens
import gameobjects.Level
import utils.GlobalAssets
import utils.setOnClickListener

/*
    This Screen should only be disposed only on quit game probably,
    as it won't have too much of resources, but that is
    a thing to be be considered.
 */
class MainMenuScreen(private val assetManager: AssetManager) : Screen {

    private val assets        = GlobalAssets(assetManager)
    private val table         = Table()
    private val title         = Label("Diamond Story", assets.skin)
    private val newGameButton = TextButton("Story Mode", assets.skin)
    private val stage         = Stage()
    private val exitButton    = TextButton("Quit", assets.skin)
    val levelsData = mutableListOf<Level>()

    init {
        // TODO: this should be moved to GameMapScreen and receive start level to load and range (all is got from stage info file)
        var levelNumber = 1
        while (true) {
            val levelFile = Gdx.files.internal("levels/l$levelNumber.json")
            if (levelFile.exists()) {
                levelsData.add(levelNumber - 1, HttpRequestBuilder.json.fromJson(Level::class.java,levelFile))
            } else {
                break
            }
            levelNumber++
        }

        table.setFillParent(true)
        table.add(title).padBottom(20f).row()
        newGameButton.label.setFontScale(0.2f)
        table.add(newGameButton).width(100f).padBottom(20f).row()
        exitButton.label.setFontScale(0.2f)
        table.add(exitButton).width(100f).padBottom(20f).row()
        stage.addActor(table)

        newGameButton.setOnClickListener {
            print("CLICK")
            DiamondStoryGame.pushScreen(LoadingScreen(assetManager, Screens.GAME_SCREEN, levelsData[1]))
        }
        exitButton.setOnClickListener {
            Gdx.app.exit()
        }
        Gdx.input.inputProcessor = stage
    }

    override fun hide() {
    }

    override fun show() {
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0 / 255f, 0 / 255f, 0 / 255f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.act(delta)
        stage.draw()
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
    }

    override fun dispose() {
        assets.dispose()
    }

}