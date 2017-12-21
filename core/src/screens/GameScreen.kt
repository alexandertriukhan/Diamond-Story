package screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import gameobjects.GameGrid

class GameScreen : Screen {

    private val gameGrid = GameGrid((gridTypes.circle()))
    private val COLS = 10
    private val batcher = SpriteBatch()
    private val gemSize = Gdx.graphics.width.toFloat() / COLS
    private val gridOffset = (Gdx.graphics.height.toFloat() - (gemSize * COLS)) / 2

    private val cam = OrthographicCamera()

    init {
        val width = Gdx.graphics.width
        val height = Gdx.graphics.height
        val w = COLS
        val h = w * (height / width)

        cam.setToOrtho(false, w.toFloat(), h.toFloat())
        cam.position.set(COLS * 0.5f, COLS * 0.5f, 0f)
        cam.update()
    }

    override fun hide() {

    }

    override fun show() {

    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        batcher.begin()
        for (i in gameGrid.cells.indices) {
            for (j in gameGrid.cells[i].indices) {
                if (gameGrid.cells[i][j].isPlaying) {
                    batcher.draw(gameGrid.cells[i][j].tileTexture, i.toFloat() * gemSize,
                            (j.toFloat() * gemSize) + gridOffset, gemSize, gemSize)
                    batcher.draw(gameGrid.cells[i][j].jewel.texture, i.toFloat() * gemSize,
                            (j.toFloat() * gemSize) + gridOffset, gemSize, gemSize)
                }
            }
        }
        batcher.end()
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun resize(width: Int, height: Int) {

    }

    override fun dispose() {

    }
}