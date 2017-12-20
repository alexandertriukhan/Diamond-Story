package screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import enums.GridType
import gameobjects.JewelGrid

class GameScreen : Screen {

    private val COLS = 10
    private val screenWidth = Gdx.graphics.width.toFloat()
    private val screenHeight = Gdx.graphics.height.toFloat()
    private val gameGrid = JewelGrid(COLS, GridType.SQUARE)
    private val batcher = SpriteBatch()

    private val cam = OrthographicCamera()

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
                batcher.draw(gameGrid.cells[i][j].texture, i.toFloat(), j.toFloat())
            }
        }

        batcher.end()
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun resize(width: Int, height: Int) {
        val w = COLS.toFloat()
        val h = w * (height.toFloat() / width.toFloat())

        cam.setToOrtho(false, w, h)
        cam.position.set(COLS * 0.5f, COLS * 0.5f, 0f)
        cam.update()
    }

    override fun dispose() {

    }
}