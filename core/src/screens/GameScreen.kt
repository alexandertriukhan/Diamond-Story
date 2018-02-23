package screens

import collections.MatchList
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import enums.EffectType
import enums.JewelType
import enums.MatchType
import gameobjects.*
import utils.InputHandler
import utils.TexturesLoader
import java.util.*


class GameScreen : Screen {

    private val gameGrid = GameGrid(gridTypes.puzzle())
    private val MAX_ROWS = gameGrid.cells.count()
    private val gemSize = Gdx.graphics.width.toFloat() / MAX_ROWS
    private val MAX_COLS = (Gdx.graphics.height.toFloat() / gemSize)
    private val batcher = SpriteBatch()
    private val gridOffset = (Gdx.graphics.height.toFloat() - (gemSize * gameGrid.cells[0].count())) / 2
    private val moves = mutableListOf<JewelMove>()
    private val specialMoves = mutableListOf<JewelMove>()
    private val itemsToCheck = mutableListOf<JewelMove>()
    private val cam = OrthographicCamera()

    private var selectedXY = Vector3()
    private var isSelected = false
    private var makeCheck = false

    private val fallDownAcceleration = 0.45f  // ORIGINAL: 0.45f
    private val moveSpeed = 8f  // ORIGINAL: 8f

    init {
        cam.setToOrtho(false, MAX_ROWS.toFloat(), MAX_COLS)
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0f)
        cam.update()
        TexturesLoader.instance.border.projectionMatrix = batcher.projectionMatrix
        Gdx.input.inputProcessor = InputHandler(this)
    }

    override fun hide() {

    }

    override fun show() {

    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(135/255f, 206/255f, 235/255f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batcher.begin()
        gameGrid.draw(batcher,delta,gemSize,gridOffset)
        drawMoves(delta)
        drawSpecialMoves(delta)
        if (makeCheck)
            checkMatches()
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
        if (moves.isEmpty() && specialMoves.isEmpty()) {
            val testTouch = getSelected()
            if (gameGrid.inRange(testTouch.x.toInt(), testTouch.y.toInt())) {
                if (gameGrid.cells[testTouch.x.toInt()][testTouch.y.toInt()].isPlaying) {
                    if (!isSelected) {
                        selectedXY = testTouch
                        isSelected = true
                        gameGrid.cells[selectedXY.x.toInt()][selectedXY.y.toInt()].jewel.isSelected = true
                    } else {
                        if (gameGrid.isAdjacent(testTouch.x.toInt(), testTouch.y.toInt(), selectedXY.x.toInt(), selectedXY.y.toInt())) {

                            moves.add(JewelMove(selectedXY.x, selectedXY.y, testTouch.x, testTouch.y,
                                    Jewel(gameGrid.cells[selectedXY.x.toInt()][selectedXY.y.toInt()].jewel.jewelType,
                                            gameGrid.cells[selectedXY.x.toInt()][selectedXY.y.toInt()].jewel.effect), moveSpeed))
                            moves.add(JewelMove(testTouch.x, testTouch.y, selectedXY.x, selectedXY.y,
                                    Jewel(gameGrid.cells[testTouch.x.toInt()][testTouch.y.toInt()].jewel.jewelType,
                                            gameGrid.cells[testTouch.x.toInt()][testTouch.y.toInt()].jewel.effect), moveSpeed))
                            gameGrid.cells[testTouch.x.toInt()][testTouch.y.toInt()].jewel.jewelType = JewelType.NO_JEWEL
                            gameGrid.cells[selectedXY.x.toInt()][selectedXY.y.toInt()].jewel.jewelType = JewelType.NO_JEWEL
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

    private fun drawMoves(delta : Float) {
        val iterator = moves.iterator()
        while (iterator.hasNext()) {
            val move = iterator.next()
            if (gameGrid.isDrawing()) {
                move.drawFromPosition(batcher, gemSize, delta, gridOffset)
            } else {
                move.draw(batcher, gemSize, delta, gridOffset)
            }
            if (move.endMove) {
                if (!move.destroyOnEnd) {
                    gameGrid.cells[move.xTo.toInt()][move.yTo.toInt()].jewel = move.jewel
                    itemsToCheck.add(move)
                }
                iterator.remove()
                if (moves.isEmpty()) {
                    makeCheck = true
                }
            }
        }
    }

    private fun drawSpecialMoves(delta : Float) {
        val iterator = specialMoves.iterator()
        while (iterator.hasNext()) {
            val move = iterator.next()
            move.draw(batcher, gemSize, delta, gridOffset)
            if (move.endMove) {
                if (!move.destroyOnEnd) {
                    gameGrid.cells[move.xTo.toInt()][move.yTo.toInt()].jewel = move.jewel
                    itemsToCheck.add(move)
                }
                iterator.remove()
                if (specialMoves.isEmpty()) {
                    prepareFalldownMoves()
                }
            }
        }
    }

    private fun removeMatches(match : List<Match>) {
        if (match.isNotEmpty()) {
            for (m in match) {
                val tmp = gameGrid.removeMatch(m)
                for (move in tmp)
                    specialMoves.add(move)
            }
        }
    }

    private fun prepareFalldownMoves() {
        for (i in gameGrid.cells.indices) {
            for (j in gameGrid.cells[i].indices) {
                if (gameGrid.cells[i][j].isPlaying) {
                    if (gameGrid.cells[i][j].jewel.jewelType == JewelType.NO_JEWEL && gameGrid.cells[i][j].isPlaying) {
                        if (j < gameGrid.cells[0].count() - 1) {
                            val highestJewel = gameGrid.getHighestJewel(i, j)
                            if (highestJewel == gameGrid.cells[0].count()) {
                                val highestNotPlaying = gameGrid.getHighestIsNotPlaying(i, j)
                                if (highestNotPlaying == gameGrid.cells[0].count()) {
                                    moves.add(JewelMove(i.toFloat(), gameGrid.cells[0].count().toFloat(),
                                            i.toFloat(), j.toFloat(), Jewel(JewelType.from(Random().nextInt(5)),
                                            EffectType.NONE),0f,18f,fallDownAcceleration))
                                } else {
                                    moves.add(JewelMove(i.toFloat(), highestNotPlaying.toFloat(),
                                            i.toFloat(), j.toFloat(), Jewel(JewelType.from(Random().nextInt(5)),
                                            EffectType.NONE),0f,18f,fallDownAcceleration))
                                }
                            } else {
                                moves.add(JewelMove(i.toFloat(), highestJewel.toFloat(), i.toFloat(), j.toFloat(),
                                        Jewel(gameGrid.cells[i][highestJewel].jewel.jewelType,
                                                gameGrid.cells[i][highestJewel].jewel.effect),0f,18f,fallDownAcceleration))
                                gameGrid.cells[i][highestJewel].jewel.jewelType = JewelType.NO_JEWEL
                            }
                        } else {
                            moves.add(JewelMove(i.toFloat(), gameGrid.cells[0].count().toFloat(),
                                        i.toFloat(), j.toFloat(), Jewel(JewelType.from(Random().nextInt(5)),
                                        EffectType.NONE),0f,18f,fallDownAcceleration))
                        }
                    }
                }
            }
        }
    }

    private fun checkMatches() {
        val listOfMatches = MatchList()
        val iterator = itemsToCheck.iterator()
        while (iterator.hasNext()) {
            val move = iterator.next()
            val match = gameGrid.createsMatch(move.xTo.toInt(), move.yTo.toInt(),
                    gameGrid.cells[move.xTo.toInt()][move.yTo.toInt()].jewel.jewelType)
            if (match.matchType != MatchType.NO_MATCH) {
                listOfMatches.add(match)
            }
            iterator.remove()
        }
        makeCheck = false
        removeMatches(listOfMatches.get())
        if (specialMoves.isEmpty())
            prepareFalldownMoves()
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