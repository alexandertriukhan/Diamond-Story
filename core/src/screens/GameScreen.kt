package screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import gameobjects.GameGrid
import utils.InputHandler
import utils.TexturesLoader


class GameScreen : Screen {

    private val gameGrid = GameGrid(gridTypes.square())
    private val MAX_COLS = gameGrid.cells[0].count()
    private val MAX_ROWS = gameGrid.cells.count()
    private val batcher = SpriteBatch()
    private val gemSize = Gdx.graphics.width.toFloat() / MAX_ROWS
    private val gridOffset = (Gdx.graphics.height.toFloat() - (gemSize * MAX_COLS)) / 2

    private val cam = OrthographicCamera()
    private var selectedXY = Vector3()

    init {
        val w = MAX_ROWS
        val h = MAX_COLS

        cam.setToOrtho(false, w.toFloat(), h.toFloat())
        cam.position.set(MAX_COLS * 0.5f, MAX_ROWS * 0.5f, 0f)
        cam.update()

        Gdx.input.inputProcessor = InputHandler(this)
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
        // TODO: find a way to get proper coordinates
        batcher.draw(TexturesLoader.instance.selectedGem,selectedXY.x * gemSize,
                (selectedXY.y * gemSize) + gridOffset, gemSize, gemSize)
        batcher.end()
    }

    fun getSelected() {
        selectedXY = Vector3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f)
        cam.unproject(selectedXY)
        selectedXY = Vector3(selectedXY.x.toInt().toFloat(),selectedXY.y.toInt().toFloat(),0f)
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