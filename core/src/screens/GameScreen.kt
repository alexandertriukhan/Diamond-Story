package screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import debug.ScreenshotFactory
import enums.Axis
import enums.EffectType
import enums.JewelType
import enums.MatchType
import gameobjects.GameGrid
import gameobjects.Jewel
import gameobjects.JewelMove
import gameobjects.Match
import utils.InputHandler
import utils.TexturesLoader
import java.util.*


class GameScreen : Screen {

    private val gameGrid = GameGrid(gridTypes.square())
    private val MAX_ROWS = gameGrid.cells.count()
    private val gemSize = Gdx.graphics.width.toFloat() / MAX_ROWS
    private val MAX_COLS = (Gdx.graphics.height.toFloat() / gemSize)
    private val batcher = SpriteBatch()
    private val gridOffset = (Gdx.graphics.height.toFloat() - (gemSize * gameGrid.cells[0].count())) / 2
    private val moves = mutableListOf<JewelMove>()
    private val itemsToCheck = mutableListOf<JewelMove>()

    private val cam = OrthographicCamera()
    private var selectedXY = Vector3()
    private var isSelected = false
    private var performingMoves = false


    init {
        cam.setToOrtho(false, MAX_ROWS.toFloat(), MAX_COLS)
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0f)
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
                    batcher.draw(gameGrid.cells[i][j].jewel.texture(), i.toFloat() * gemSize,
                            (j.toFloat() * gemSize) + gridOffset, gemSize, gemSize)
                }
            }
        }
        performingMoves = moves.isNotEmpty()
        drawMoves(delta * 4)
        if (!performingMoves && itemsToCheck.isNotEmpty())
            checkMatches()
        if (isSelected) {
            batcher.draw(TexturesLoader.instance.selectedGem, selectedXY.x * gemSize,
                    (selectedXY.y * gemSize) + gridOffset, gemSize, gemSize)
        }
        batcher.end()
    }

    private fun getSelected() : Vector3 {
        val touch = Vector3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat() + gridOffset, 0f)
        cam.unproject(touch)
        if (touch.x < 0)
            touch.x = -1f
        if (touch.y < 0)
            touch.y = -1f
        return Vector3(touch.x.toInt().toFloat(), touch.y.toInt().toFloat(),0f)
    }

    fun onClick() {
        if (!performingMoves) {
            val testTouch = getSelected()
            if (gameGrid.inRange(testTouch.x.toInt(), testTouch.y.toInt())) {
                if (!isSelected) {
                    selectedXY = testTouch
                    isSelected = true
                } else {
                    if (gameGrid.isAdjacent(testTouch.x.toInt(), testTouch.y.toInt(), selectedXY.x.toInt(), selectedXY.y.toInt())) {
                        moves.add(JewelMove(testTouch.x, testTouch.y, selectedXY.x, selectedXY.y,
                                Jewel(gameGrid.cells[testTouch.x.toInt()][testTouch.y.toInt()].jewel.jewelType,gameGrid.cells[testTouch.x.toInt()][testTouch.y.toInt()].jewel.effect)))
                        moves.add(JewelMove(selectedXY.x, selectedXY.y, testTouch.x, testTouch.y,
                                Jewel(gameGrid.cells[selectedXY.x.toInt()][selectedXY.y.toInt()].jewel.jewelType,gameGrid.cells[selectedXY.x.toInt()][selectedXY.y.toInt()].jewel.effect)))
                        gameGrid.cells[testTouch.x.toInt()][testTouch.y.toInt()].jewel.jewelType = JewelType.NO_JEWEL
                        gameGrid.cells[selectedXY.x.toInt()][selectedXY.y.toInt()].jewel.jewelType = JewelType.NO_JEWEL
                        isSelected = false
                    } else {
                        selectedXY = testTouch
                        isSelected = true
                    }
                }
            }
        }
    }

    private fun drawMoves(delta : Float) {
        var endMove = false
        val iterator = moves.iterator()
        while (iterator.hasNext()) {
            val move = iterator.next()
            if (move.movingAxis == Axis.X) {
                if (!move.startBigger) {
                    move.xStart = move.xStart + delta
                    if (move.xStart > move.xEnd) {
                        endMove = true
                        move.xStart = move.xEnd
                    }
                } else {
                    move.xStart = move.xStart - delta
                    if (move.xStart < move.xEnd) {
                        endMove = true
                        move.xStart = move.xEnd
                    }
                }
            } else {
                if (!move.startBigger) {
                    move.yStart = move.yStart + delta
                    if (move.yStart > move.yEnd) {
                        endMove = true
                        move.yStart = move.yEnd
                    }
                } else {
                    move.yStart = move.yStart - delta
                    if (move.yStart < move.yEnd) {
                        endMove = true
                        move.yStart = move.yEnd
                    }
                }
            }
            batcher.draw(move.jewel.texture(), move.xStart * gemSize, (move.yStart * gemSize) + gridOffset,
                    gemSize, gemSize)
            if (endMove) {
                gameGrid.cells[move.xEnd.toInt()][move.yEnd.toInt()].jewel.jewelType = move.jewel.jewelType
                itemsToCheck.add(move)
                iterator.remove()
            }
        }
    }

    // TODO: work with vertical rows
    private fun prepareMoves(match : Match) {
        gameGrid.removeMatch(match)
        for (m in match.gemsInMatch) {
            var y = m.y
            while (y < gameGrid.cells[0].count() - 1) {
                moves.add(JewelMove(m.x, (y + 1), m.x, y, Jewel(gameGrid.cells[m.x.toInt()][(y.toInt() + 1)].jewel.jewelType,
                            gameGrid.cells[m.x.toInt()][(y.toInt() + 1)].jewel.effect)))
                gameGrid.cells[m.x.toInt()][(y + 1).toInt()].jewel.jewelType = JewelType.NO_JEWEL
                y++
            }
            moves.add(JewelMove(m.x, gameGrid.cells[0].count().toFloat(), m.x, y, Jewel(JewelType.from(Random().nextInt(5)),
                        EffectType.NONE)))
        }
    }

    private fun checkMatches() {
        val iterator = itemsToCheck.iterator()
        while (iterator.hasNext()) {
            val move = iterator.next()
            val match = gameGrid.createsMatch(move.xEnd.toInt(), move.yEnd.toInt(),
                    gameGrid.cells[move.xEnd.toInt()][move.yEnd.toInt()].jewel.jewelType)
            if (match.matchType != MatchType.NO_MATCH) {
                prepareMoves(match)
                break
            } else {
                iterator.remove()
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