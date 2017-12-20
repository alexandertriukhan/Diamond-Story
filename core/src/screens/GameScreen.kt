package screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import enums.GridType
import gameobjects.JewelGrid

class GameScreen : Screen {

    private val COLS = 10
    private val screenWidth = Gdx.graphics.width.toFloat()
    private val screenHeight = Gdx.graphics.height.toFloat()
    private val gameGrid = JewelGrid(COLS, GridType.SQUARE)
    private val batcher = SpriteBatch()
    private val gemSize = Vector2(screenWidth/COLS,screenWidth/COLS)

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
                batcher.draw(gameGrid.cells[i][j].texture, i.toFloat() * 32, j.toFloat() * 32,
                        gemSize.x, gemSize.y)
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