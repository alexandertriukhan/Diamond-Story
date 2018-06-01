package screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.math.Vector3
import debug.FrameRate
import gameobjects.*
import utils.InputHandler
import utils.GameScreenAssets


class GameScreen(layout : Array<IntArray>, assetManager: AssetManager) : Screen {

    private val gameScreenAssets = GameScreenAssets(assetManager)
    private val gameGrid = GameGrid(layout,gameScreenAssets)
    private val batcher = SpriteBatch()
    private val cam = OrthographicCamera()
    private val menuBar = GameScreenMenuBars(gameScreenAssets,gameGrid)

    private var selectedXY = Vector3()
    private var isSelected = false

    private val frameRate = FrameRate()

    init {
        cam.setToOrtho(false, gameGrid.MAX_ROWS.toFloat(), gameGrid.MAX_COLS)
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0f)
        cam.update()
        gameScreenAssets.border.projectionMatrix = batcher.projectionMatrix
        Gdx.input.inputProcessor = GestureDetector(InputHandler(this))
    }

    override fun hide() {

    }

    override fun show() {

    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(127/255f, 100/255f, 127/255f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        frameRate.update()
        frameRate.render()
        batcher.begin()
        menuBar.drawTopBar(batcher)
        menuBar.drawBottomBar(batcher)
        gameGrid.draw(batcher,delta)
        batcher.end()
    }

    private fun getSelected() : Vector3 {
        val touch = Vector3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat() + gameGrid.gridOffset, 0f)
        cam.unproject(touch)
        if (touch.x < 0)
            touch.x = -1f
        if (touch.y < 0)
            touch.y = -1f
        return Vector3(touch.x.toInt().toFloat(), touch.y.toInt().toFloat(),0f)
    }

    fun onClick() {
        if (gameGrid.moves.isEmpty() && gameGrid.specialMoves.isEmpty() && gameGrid.isFilled) {
            val testTouch = getSelected()
            if (gameGrid.inRange(testTouch.x.toInt(), testTouch.y.toInt())) {
                if (gameGrid.cells[testTouch.x.toInt()][testTouch.y.toInt()].isPlaying) {
                    if (!isSelected) {
                        selectedXY = testTouch
                        isSelected = true
                        gameGrid.cells[selectedXY.x.toInt()][selectedXY.y.toInt()].jewel.isSelected = true
                    } else {
                        if (gameGrid.isAdjacent(testTouch.x.toInt(), testTouch.y.toInt(), selectedXY.x.toInt(), selectedXY.y.toInt())) {
                            gameGrid.swapActions(testTouch.x, testTouch.y, selectedXY.x, selectedXY.y)
                            gameGrid.cells[selectedXY.x.toInt()][selectedXY.y.toInt()].jewel.isSelected = false
                            isSelected = false
                        } else {
                            gameGrid.cells[selectedXY.x.toInt()][selectedXY.y.toInt()].jewel.isSelected = false
                            selectedXY = testTouch
                            gameGrid.cells[selectedXY.x.toInt()][selectedXY.y.toInt()].jewel.isSelected = true
                            isSelected = true
                        }
                    }
                }
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

    }
}