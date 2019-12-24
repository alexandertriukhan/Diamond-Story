package gameobjects

import collections.DestroyAnimsList
import collections.MatchList
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import enums.EffectType
import enums.JewelType
import enums.MatchType
import utils.GameScreenAssets
import constants.Values
import java.util.*

// TODO: implement intArrayOf(0, 1, 1, 1) etc., also consider refactoring
class GameGrid(private val levelData : Level, private val assets: GameScreenAssets) {

    private val gridType = levelData.gridTemplate
    private val destroyAnimations = DestroyAnimsList()
    private val fallDownAcceleration = 0.45f  // ORIGINAL: 0.45f
    private val itemsToCheck = mutableListOf<JewelMove>()
    private var needMoves = false
    private var makeCheck = false
    private val moveSpeed = Values.JEWEL_MOVEMENT_SPEED  // ORIGINAL: 8f

    val lastMoves = mutableListOf<JewelMove>()

    var score = 0f
    private var moveScore = 0f
    private var scoreMultiplier = 1f

    val specialMoves = mutableListOf<JewelMove>()
    val moves = mutableListOf<JewelMove>()
    var isFilled = false
    var cells = Array(gridType.count(), {_ -> Array(gridType[0].count()
            , {_ -> Cell(false, Jewel(JewelType.from(Random().nextInt(5)),assets),
            assets.tileBlank, assets)})})
    val MAX_ROWS = cells.count()
    val gemSize = Gdx.graphics.width.toFloat() / MAX_ROWS
    val MAX_COLS = Gdx.graphics.height.toFloat() / gemSize
    private val gridOffset = (Gdx.graphics.height.toFloat() - (gemSize * cells[0].count())) / 2
    var movesLeft = 30

    var relativeCols = Gdx.graphics.height.toFloat() / gemSize
    private var relativeGemSize = Gdx.graphics.width.toFloat() / MAX_ROWS
    var touchOffsetY = (Gdx.graphics.height.toFloat() - (relativeGemSize * cells[0].count())) / 2

    init {
        for (i in gridType.indices) {
            for (j in gridType[i].indices) {
                cells[i][j].isPlaying = (gridType[i][j] == 1)

                // Making check no occurrences of 3 gems are presented
                if (cells[i][j].isPlaying) {
                    if (i > 1) {
                        if (cells[i - 1][j].isPlaying)
                            if (cells[i - 2][j].isPlaying)
                                if (cells[i - 1][j].jewel.jewelType == cells[i][j].jewel.jewelType) {
                                    while (cells[i - 2][j].jewel.jewelType == cells[i][j].jewel.jewelType)
                                        cells[i][j].jewel = Jewel(JewelType.from(Random().nextInt(5)),assets)
                                }
                    }
                    if (j > 1) {
                        if (cells[i][j - 1].isPlaying)
                            if (cells[i][j - 2].isPlaying)
                                if (cells[i][j - 1].jewel.jewelType == cells[i][j].jewel.jewelType) {
                                    while (cells[i][j - 2].jewel.jewelType == cells[i][j].jewel.jewelType)
                                        cells[i][j].jewel = Jewel(JewelType.from(Random().nextInt(5)),assets)
                                }
                    }
                } else {
                    cells[i][j].jewel.jewelType = JewelType.NO_JEWEL
                }

                val borders = getBorders(i, j, gridType)
                if (borders[3] == 1) {
                    cells[i][j].isTop = true
                }
                if (borders[0] == 1) {
                    cells[i][j].isBottom = true
                }
                if (borders[1] == 1) {
                    cells[i][j].isRight = true
                }
                if (borders[2] == 1) {
                    cells[i][j].isLeft = true
                }
                if (j == 0) {
                    cells[i][j].isBottomEdge = true
                }
                if (j == cells[0].count() - 1) {
                    cells[i][j].isTopEdge = true
                }
                if (j % 2 == 0) {
                    if (i % 2 != 0) {
                        cells[i][j].tileTexture = assets.tileBlankLite
                    }
                }
                else if (i % 2 == 0) {
                    cells[i][j].tileTexture = assets.tileBlankLite
                }
            }
        }
        isFilled = true
    }

    fun resize(width: Int, height: Int) {
        relativeGemSize = width.toFloat() / MAX_ROWS
        relativeCols = height.toFloat() / relativeGemSize
        touchOffsetY = (height.toFloat() - (relativeGemSize * cells[0].count())) / 2
    }

    fun swapField() : List<JewelMove> {
        val moves = mutableListOf<JewelMove>()
        for (i in gridType.indices) {
            for (j in gridType[i].indices) {
                if (cells[i][j].isPlaying) {
                    if (i > 1) {
                        if (cells[i - 1][j].isPlaying)
                            if (cells[i - 2][j].isPlaying)
                                if (cells[i - 1][j].jewel.jewelType == cells[i][j].jewel.jewelType) {
                                    while (cells[i - 2][j].jewel.jewelType == cells[i][j].jewel.jewelType) {
                                        moves.add(JewelMove(Gdx.graphics.width.toFloat() / 2, Gdx.graphics.height.toFloat() / 2,
                                                i.toFloat(), j.toFloat(),
                                                Jewel(JewelType.from(Random().nextInt(5)),assets),8f))
                                    }
                                }
                    }
                    if (j > 1) {
                        if (cells[i][j - 1].isPlaying)
                            if (cells[i][j - 2].isPlaying)
                                if (cells[i][j - 1].jewel.jewelType == cells[i][j].jewel.jewelType) {
                                    while (cells[i][j - 2].jewel.jewelType == cells[i][j].jewel.jewelType) {
                                        moves.add(JewelMove(Gdx.graphics.width.toFloat() / 2, Gdx.graphics.height.toFloat() / 2,
                                                i.toFloat(), j.toFloat(),
                                                Jewel(JewelType.from(Random().nextInt(5)),assets),8f))
                                    }
                                }
                    }
                } else {
                    cells[i][j].jewel.jewelType = JewelType.NO_JEWEL
                }
            }
        }
        return moves
    }

    // 1 if tile needs border, DOWN, RIGHT, LEFT, TOP
    private fun getBorders(i : Int, j : Int, cells : Array<IntArray>) : IntArray {
        val borders = intArrayOf(0, 0, 0, 0)
        if (i != 0) {
            if (cells[i - 1][j] == 0) {
                borders[2] = 1
            }
        } else {
            borders[2] = 1
        }
        if (j != 0) {
            if (cells[i][j - 1] == 0) {
                borders[0] = 1
            }
        } else {
            borders[0] = 1
        }
        if (i < cells.count() - 1) {
            if (cells[i + 1][j] == 0) {
                borders[1] = 1
            }
        } else {
            borders[1] = 1
        }
        if (j < cells[0].count() - 1) {
            if (cells[i][j + 1] == 0) {
                borders[3] = 1
            }
        } else {
            borders[3] = 1
        }
        return borders
    }

    fun draw(batcher : SpriteBatch, delta: Float) {
        if (needMoves) {
            fallDownMoves()
            needMoves = false
        }
        updateScore()
        drawTiles(batcher)
        drawJewels(batcher,delta)
        drawDestroyAnimations(batcher,delta)
        drawMoves(batcher,delta)
        drawSpecialMoves(batcher,delta)
        if (makeCheck) {
            checkMatches()
            if (isGameOver()) {
                movesLeft = 30 // TODO: implement
            }
        }
    }

    private fun drawTiles(batcher : SpriteBatch) {
        for (i in cells.indices) {
            for (j in cells[i].indices) {
                if (cells[i][j].isPlaying) {
                    cells[i][j].drawTile(batcher,i.toFloat() * gemSize,
                            (j.toFloat() * gemSize) + gridOffset, gemSize)
                }
            }
        }
    }

    private fun drawJewels(batcher : SpriteBatch, delta: Float) {
        for (i in cells.indices) {
            for (j in cells[i].indices) {
                if (cells[i][j].isPlaying) {
                    cells[i][j].drawJewel(batcher,i.toFloat() * gemSize,
                            (j.toFloat() * gemSize) + gridOffset, gemSize, delta)
                }
            }
        }
    }

    private fun drawSpecialMoves(batcher : SpriteBatch, delta : Float) {
        val iterator = specialMoves.iterator()
        while (iterator.hasNext()) {
            val move = iterator.next()
            move.draw(batcher, gemSize, delta, gridOffset)
            if (move.endMove()) {
                if (!move.destroyOnEnd) {
                    cells[move.xTo.toInt()][move.yTo.toInt()].jewel = move.jewel
                    itemsToCheck.add(move)
                }
                iterator.remove()
                if (specialMoves.isEmpty()) {
                    fallDownMoves()
                }
            }
        }
    }

    private fun drawMoves(batcher : SpriteBatch, delta : Float) {
        val iterator = moves.iterator()
        while (iterator.hasNext()) {
            val move = iterator.next()
            if (isDrawing()) {
                move.drawFromPosition(batcher, gemSize, delta, gridOffset)
            } else {
                move.draw(batcher, gemSize, delta, gridOffset)
            }
            if (move.endMove()) {
                if (!move.destroyOnEnd) {
                    cells[move.xTo.toInt()][move.yTo.toInt()].jewel = move.jewel
                    itemsToCheck.add(move)
                    //gameGrid.lastMoves.add(JewelMove(move.xFrom,move.yFrom,move.xTo,move.yTo,move.jewel,
                    //        move.currentSpeed,18f,0.45f))
                }
                iterator.remove()
                if (moves.isEmpty()) {
                    if (isFilled) {
                        makeCheck = true
                    }
                    needMoves = !isFilled
                }
            }
        }
    }

    private fun fallDownMoves() {
        isFilled = false
        for (j in (cells[0].count() - 1) downTo 0) {
            val tmpMoves = fallDownRow(j)
            for (move in tmpMoves) {
                moves.add(move)
            }
        }
        if (moves.count() == 0) {
            isFilled = true
            makeCheck = true
        }
        //gameGrid.lastMoves.clear()
    }

    private fun checkMatches() {
        val listOfMatches = MatchList()
        val iterator = itemsToCheck.iterator()
        var matchesFound = false
        while (iterator.hasNext()) {
            val move = iterator.next()
            val match = createsMatch(move.xTo.toInt(), move.yTo.toInt())
            if (match.matchType != MatchType.NO_MATCH) {
                listOfMatches.add(match)
                matchesFound = true
            }
            iterator.remove()
        }
        makeCheck = false
        removeMatches(listOfMatches.get())
        if (specialMoves.isEmpty()) {
            if (matchesFound) {
                fallDownMoves()
            }
        }
    }

    private fun removeMatches(match : List<Match>) {
        needMoves = false
        if (match.isNotEmpty()) {
            for (m in match) {
                val tmp = removeMatch(m)
                for (move in tmp)
                    specialMoves.add(move)
            }
        }
    }

    private fun isDrawing() : Boolean {
        return destroyAnimations.list.isNotEmpty()
    }

    fun inRange(x : Int, y : Int) : Boolean {
        return x >= 0 && y >= 0 && x < gridType.count() && y < gridType[0].count()
    }

    fun isAdjacent(x1 : Int, y1 : Int, x2 : Int, y2 : Int) : Boolean {
        if (((x1 - x2) <= 1 && (x1 - x2) >= -1)  &&
                ((y1 - y2) <= 1 && (y1 - y2) >= -1)) {
            if (!isDiagonalAdjacent(x1,y1,x2,y2)) {
                return true
            }
        }
        return false
    }

    private fun isDiagonalAdjacent(x1 : Int, y1 : Int, x2 : Int, y2 : Int) : Boolean {
        if ((x1 - x2) == (y1 - y2))
            return true
        if ((x1 + y1) == (x2 + y2))
            return true
        return false
    }

    private fun swapCells(x1 : Int, y1 : Int, x2 : Int, y2 : Int) {
        val tmpCell = cells[x1][y1].jewel
        cells[x1][y1].jewel = cells[x2][y2].jewel
        cells[x2][y2].jewel = tmpCell
    }

    private fun createsMatch(x : Int, y : Int) : Match {
        val jewelType = cells[x][y].jewel.jewelType
        val matchHorizontal = getHorizontalMatch(x,y,jewelType)
        val matchVertical = getVerticalMatch(x,y,jewelType)
        val resultingMatch = Match(MutableList(1,{ _ -> Vector2(x.toFloat(),y.toFloat()) }),MatchType.NO_MATCH,
                cells[x][y].jewel.jewelType)
        if (matchHorizontal.gemsInMatch.count() > 1) {
            if (matchVertical.gemsInMatch.count() > 1) {
                matchHorizontal.mergeIn(matchVertical)
                resultingMatch.mergeIn(matchHorizontal)
                resultingMatch.matchType = MatchType.MATCH_CROSS
                return resultingMatch
            } else {
                resultingMatch.mergeIn(matchHorizontal)
            }
        } else {
            if (matchVertical.gemsInMatch.count() > 1)
                resultingMatch.mergeIn(matchVertical)
        }
        var size = resultingMatch.gemsInMatch.count()
        if (size > 5) size = 5
        if (size < 3) size = 0
        resultingMatch.matchType = MatchType.from(size)
        return resultingMatch
    }

    private fun getHorizontalMatch(x : Int, y : Int, jewelType: JewelType) : Match {
        var counter = 1
        val match = Match(mutableListOf(), MatchType.NO_MATCH, JewelType.NO_JEWEL)
        if (jewelType != JewelType.NO_JEWEL) {
            while ((x - counter) >= 0) {
                if (cells[x - counter][y].jewel.jewelType == jewelType) {
                    match.gemsInMatch.add(Vector2((x - counter).toFloat(), y.toFloat()))
                } else break
                counter++
            }
            counter = 1
            while ((x + counter) < cells.count()) {
                if (cells[x + counter][y].jewel.jewelType == jewelType) {
                    match.gemsInMatch.add(Vector2((x + counter).toFloat(), y.toFloat()))
                } else break
                counter++
            }
        }
        return match
    }

    private fun getVerticalMatch(x : Int, y : Int, jewelType: JewelType) : Match {
        var counter = 1
        val match = Match(mutableListOf(), MatchType.NO_MATCH, JewelType.NO_JEWEL)
        if (jewelType != JewelType.NO_JEWEL) {
            while ((y - counter) >= 0) {
                if (cells[x][y - counter].jewel.jewelType == jewelType) {
                    match.gemsInMatch.add(Vector2(x.toFloat(), (y - counter).toFloat()))
                } else break
                counter++
            }
            counter = 1
            while ((y + counter) < cells[0].count()) {
                if (cells[x][y + counter].jewel.jewelType == jewelType) {
                    match.gemsInMatch.add(Vector2(x.toFloat(), (y + counter).toFloat()))
                } else break
                counter++
            }
        }
        return match
    }

    private fun removeColor(jewelType : JewelType) {
        val colorMatch = Match(mutableListOf(), MatchType.MATCH3, JewelType.NO_JEWEL)
        for (i in cells.indices) {
            for (j in cells[i].indices) {
                if (cells[i][j].jewel.jewelType == jewelType) {
                    colorMatch.gemsInMatch.add(Vector2(i.toFloat(),j.toFloat()))
                }
            }
        }
        removeMatch(colorMatch)
        fallDownMoves()
    }

    private fun removeMatch(match : Match) : List<JewelMove> {
        val moves = mutableListOf<JewelMove>()
        var hasSpecials = false
        for (gem in match.gemsInMatch) {
            if (cells[gem.x.toInt()][gem.y.toInt()].jewel.effect != EffectType.NONE) {
                hasSpecials = true
            }
        }
        for (gem in match.gemsInMatch) {
            if (cells[gem.x.toInt()][gem.y.toInt()].jewel.effect != EffectType.NONE) {
                when (cells[gem.x.toInt()][gem.y.toInt()].jewel.effect) {
                    EffectType.FIRE -> fireDestroy(gem.x,gem.y)
                    EffectType.CROSS -> crossDestroy(gem.x,gem.y)
                }
            }
            if (match.matchType != MatchType.MATCH3) {
                if (!(gem.x == match.firstGem().x && gem.y == match.firstGem().y)) {
                    if (!hasSpecials) {
                        moves.add(JewelMove(gem.x, gem.y, match.firstGem().x, match.firstGem().y,
                                Jewel(cells[gem.x.toInt()][gem.y.toInt()].jewel.jewelType,assets), 8f))  // ORIGINAL : 8f
                        moves.last().destroyOnEnd = true
                    }
                    cells[gem.x.toInt()][gem.y.toInt()].jewel.jewelType = JewelType.NO_JEWEL
                    cells[gem.x.toInt()][gem.y.toInt()].jewel.effect = EffectType.NONE
                } else {
                    // TODO: add a score for creating fire or cross or super gem
                }
            } else {
                destroyAnimations.add(DestroyAnimation(assets,gem.x,gem.y,
                        Jewel(cells[gem.x.toInt()][gem.y.toInt()].jewel.jewelType,assets),EffectType.NONE,gemSize,
                        gridOffset,(Values.SCORE_MATCH_3 * scoreMultiplier).toInt()))
                moveScore += Values.SCORE_MATCH_3 * scoreMultiplier
                cells[gem.x.toInt()][gem.y.toInt()].jewel.jewelType = JewelType.NO_JEWEL
            }
        }
        if (match.matchType != MatchType.MATCH3) {
            cells[match.firstGem().x.toInt()][match.firstGem().y.toInt()].jewel.jewelType = match.matchColor
            if (match.matchType == MatchType.MATCH4)
                cells[match.firstGem().x.toInt()][match.firstGem().y.toInt()].jewel.effect = EffectType.FIRE
            if (match.matchType == MatchType.MATCH_CROSS)
                cells[match.firstGem().x.toInt()][match.firstGem().y.toInt()].jewel.effect = EffectType.CROSS
            if (match.matchType == MatchType.MATCH5) {
                cells[match.firstGem().x.toInt()][match.firstGem().y.toInt()].jewel.jewelType = JewelType.SUPER_GEM
                cells[match.firstGem().x.toInt()][match.firstGem().y.toInt()].jewel.effect = EffectType.SUPER_GEM
            }
        }
        return moves
    }

    private fun fireDestroy(x: Float, y: Float) {
        cells[x.toInt()][y.toInt()].jewel.effect = EffectType.NONE
        val toDestroy = getAdjacents(x.toInt(),y.toInt())
        for (xy in toDestroy) {
            if (cells[xy.x.toInt()][xy.y.toInt()].jewel.effect == EffectType.FIRE) {
                fireDestroy(xy.x,xy.y)
            }
            if (cells[xy.x.toInt()][xy.y.toInt()].jewel.effect == EffectType.CROSS) {
                crossDestroy(xy.x,xy.y)
            }
            destroyAnimations.add(DestroyAnimation(assets,xy.x,xy.y,
                    Jewel(cells[xy.x.toInt()][xy.y.toInt()].jewel.jewelType,assets),EffectType.FIRE,gemSize
                    ,gridOffset,(Values.SCORE_MATCH_3 * scoreMultiplier).toInt()))
            moveScore += Values.SCORE_MATCH_3 * scoreMultiplier
            cells[xy.x.toInt()][xy.y.toInt()].jewel.jewelType = JewelType.NO_JEWEL
        }
    }

    private fun crossDestroy(x: Float, y: Float) {
        cells[x.toInt()][y.toInt()].jewel.effect = EffectType.NONE
        destroyAnimations.add(DestroyAnimation(assets,x,y,
                Jewel(cells[x.toInt()][y.toInt()].jewel.jewelType,assets),EffectType.CROSS,gemSize,gridOffset,(Values.SCORE_MATCH_3 * scoreMultiplier).toInt()))
        moveScore += Values.SCORE_MATCH_3 * scoreMultiplier
        // TODO: dont add for x y
        for (row in (0..(cells.count() - 1))) {
            if (cells[row][y.toInt()].jewel.effect != EffectType.NONE) {
                if (cells[row][y.toInt()].jewel.effect == EffectType.CROSS) {
                    if (row != x.toInt()) {
                        crossDestroy(row.toFloat(), y)
                    }
                }
                if (cells[x.toInt()][y.toInt()].jewel.effect == EffectType.FIRE) {
                    fireDestroy(row.toFloat(), y)
                }
            }
            destroyAnimations.add(DestroyAnimation(assets,row.toFloat(),y,
                    Jewel(cells[row][y.toInt()].jewel.jewelType,assets),EffectType.FIRE,gemSize,gridOffset,(Values.SCORE_MATCH_3 * scoreMultiplier).toInt()))
            moveScore += Values.SCORE_MATCH_3 * scoreMultiplier
            cells[row][y.toInt()].jewel.jewelType = JewelType.NO_JEWEL
        }
        for (col in (0..(cells[0].count() - 1))) {
            if (cells[x.toInt()][col].jewel.effect != EffectType.NONE) {
                if (cells[x.toInt()][col].jewel.effect == EffectType.CROSS) {
                    if (col != y.toInt()) {
                        crossDestroy(x, col.toFloat())
                    }
                }
                if (cells[x.toInt()][col].jewel.effect == EffectType.FIRE) {
                    fireDestroy(x, col.toFloat())
                }
            }
            destroyAnimations.add(DestroyAnimation(assets,x,col.toFloat(),
                    Jewel(cells[x.toInt()][col].jewel.jewelType,assets),EffectType.FIRE,gemSize,gridOffset,(Values.SCORE_MATCH_3 * scoreMultiplier).toInt()))
            moveScore += Values.SCORE_MATCH_3 * scoreMultiplier
            cells[x.toInt()][col].jewel.jewelType = JewelType.NO_JEWEL
        }
    }

    private fun getAdjacents(x : Int, y : Int) : List<Vector2> {
        val result = mutableListOf<Vector2>()
        if (x > 0 && y > 0) {
            if (cells[x-1][y-1].isPlaying) {
                result.add(Vector2(x-1f,y-1f))
            }
        }
        if (x > 0) {
            if (cells[x-1][y].isPlaying) {
                result.add(Vector2(x-1f,y.toFloat()))
            }
        }
        if (y > 0) {
            if (cells[x][y-1].isPlaying) {
                result.add(Vector2(x.toFloat(),y-1f))
            }
        }
        if ((x < (cells.count() - 1)) && (y < (cells[0].count() - 1))) {
            if (cells[x+1][y+1].isPlaying) {
                result.add(Vector2(x+1f,y+1f))
            }
        }
        if (x < (cells.count() - 1)) {
            if (cells[x+1][y].isPlaying) {
                result.add(Vector2(x+1f,y.toFloat()))
            }
        }
        if (y < (cells[0].count() - 1)) {
            if (cells[x][y+1].isPlaying) {
                result.add(Vector2(x.toFloat(),y+1f))
            }
        }
        if (((x - 1) >= 0) && ((y + 1) < cells[0].count())) {
            if (cells[x-1][y+1].isPlaying) {
                result.add(Vector2(x-1f,y+1f))
            }
        }
        if (((x + 1) < cells.count()) && ((y - 1) >= 0)) {
            if (cells[x+1][y-1].isPlaying) {
                result.add(Vector2(x+1f,y-1f))
            }
        }
        return result
    }

    private fun drawDestroyAnimations(batch: SpriteBatch, delta: Float) {
        if (destroyAnimations.list.isNotEmpty()) {
            val iterator = destroyAnimations.list.iterator()
            while (iterator.hasNext()) {
                val anim = iterator.next()
                if (anim.isStopped()) {
                    iterator.remove()
                } else {
                    anim.draw(batch,delta)
                }
            }
        }
    }

    private fun fallDownRow(row : Int) : List<JewelMove> {
        val moves = mutableListOf<JewelMove>()
        for (i in cells.indices) {
            var leftTurn = false
            var rightTurn = false
            if (cells[i][row].isPlaying && cells[i][row].jewel.jewelType == JewelType.NO_JEWEL) {
                if (row < cells[0].count() - 1) {
                    if (!cells[i][row + 1].isBlocked()) {
                        moves.add(JewelMove(i.toFloat(), (row + 1).toFloat(), i.toFloat(), row.toFloat(),
                                Jewel(cells[i][row + 1].jewel.jewelType,assets,
                                        cells[i][row + 1].jewel.effect), 5f))
                        cells[i][row + 1].jewel.jewelType = JewelType.NO_JEWEL
                    } else if (!cells[i][row + 1].isPlaying) {
                        if (i != 0) {
                            if (!cells[i - 1][row + 1].isBlocked()) {
                                leftTurn = true
                            }
                        }
                        if (i < cells.count() - 1) {
                            if (!cells[i + 1][row + 1].isBlocked()) {
                                rightTurn = true
                            }
                        }
                        if (leftTurn && rightTurn) {
                            val randI = Random().nextInt(2)
                            if (randI == 1) {
                                moves.add(JewelMove((i - 1).toFloat(), (row + 1).toFloat(), i.toFloat(), row.toFloat(),
                                        Jewel(cells[i - 1][row + 1].jewel.jewelType,assets,
                                                cells[i - 1][row + 1].jewel.effect), 5f))
                                cells[i - 1][row + 1].jewel.jewelType = JewelType.NO_JEWEL
                            } else {
                                moves.add(JewelMove((i + 1).toFloat(), (row + 1).toFloat(), i.toFloat(), row.toFloat(),
                                        Jewel(cells[i + 1][row + 1].jewel.jewelType,assets,
                                                cells[i + 1][row + 1].jewel.effect), 5f))
                                cells[i + 1][row + 1].jewel.jewelType = JewelType.NO_JEWEL
                            }
                        } else if (leftTurn) {
                            moves.add(JewelMove((i - 1).toFloat(), (row + 1).toFloat(), i.toFloat(), row.toFloat(),
                                    Jewel(cells[i - 1][row + 1].jewel.jewelType,assets,
                                            cells[i - 1][row + 1].jewel.effect), 5f))
                            cells[i - 1][row + 1].jewel.jewelType = JewelType.NO_JEWEL
                        } else if (rightTurn) {
                            moves.add(JewelMove((i + 1).toFloat(), (row + 1).toFloat(), i.toFloat(), row.toFloat(),
                                    Jewel(cells[i + 1][row + 1].jewel.jewelType,assets,
                                            cells[i + 1][row + 1].jewel.effect), 5f))
                            cells[i + 1][row + 1].jewel.jewelType = JewelType.NO_JEWEL
                        }
                    }
                } else {
                    moves.add(JewelMove(i.toFloat(), cells[0].count().toFloat(),
                            i.toFloat(), row.toFloat(), Jewel(JewelType.from(Random().nextInt(5)),assets,
                            EffectType.NONE),5f))
                }
            }
        }
        return moves
    }

    // takes coordinates of two cells and returns result of them swapping
    fun swapActions(x1 : Float, y1 : Float, x2 : Float, y2 : Float) {
            if (cells[x1.toInt()][y1.toInt()].jewel.jewelType == JewelType.SUPER_GEM
                    || cells[x2.toInt()][y2.toInt()].jewel.jewelType == JewelType.SUPER_GEM) {
                if (cells[x1.toInt()][y1.toInt()].jewel.jewelType != JewelType.SUPER_GEM) {
                    removeColor(cells[x1.toInt()][y1.toInt()].jewel.jewelType)
                    destroyAnimations.add(DestroyAnimation(assets,x2,y2,
                            Jewel(cells[x2.toInt()][y2.toInt()].jewel.jewelType,assets),EffectType.NONE,gemSize,gridOffset))
                    cells[x2.toInt()][y2.toInt()].jewel.jewelType = JewelType.NO_JEWEL
                } else {
                    removeColor(cells[x2.toInt()][y2.toInt()].jewel.jewelType)
                    destroyAnimations.add(DestroyAnimation(assets,x1,y1,
                            Jewel(cells[x1.toInt()][y1.toInt()].jewel.jewelType,assets),EffectType.NONE,gemSize,gridOffset))
                    cells[x1.toInt()][y1.toInt()].jewel.jewelType = JewelType.NO_JEWEL
                }
                movesLeft--
                return
            }
            swapCells(x1.toInt(),y1.toInt(),x2.toInt(),y2.toInt())
            if ((createsMatch(x1.toInt(), y1.toInt()).matchType != MatchType.NO_MATCH)
                    || (createsMatch(x2.toInt(), y2.toInt()).matchType != MatchType.NO_MATCH)) {
                swapCells(x1.toInt(),y1.toInt(),x2.toInt(),y2.toInt())
                moves.add(JewelMove(x1, y1, x2, y2, Jewel(cells[x1.toInt()][y1.toInt()].jewel.jewelType,assets,
                        cells[x1.toInt()][y1.toInt()].jewel.effect), moveSpeed))
                moves.add(JewelMove(x2, y2, x1, y1, Jewel(cells[x2.toInt()][y2.toInt()].jewel.jewelType,assets,
                        cells[x2.toInt()][y2.toInt()].jewel.effect), moveSpeed))
                cells[x1.toInt()][y1.toInt()].jewel.jewelType = JewelType.NO_JEWEL
                cells[x2.toInt()][y2.toInt()].jewel.jewelType = JewelType.NO_JEWEL
                movesLeft--
            } else {
                swapCells(x1.toInt(),y1.toInt(),x2.toInt(),y2.toInt())
                // RETURN_BACK
            }
    }

    private fun updateScore() {
        if (moveScore.toInt() < 0) {
            moveScore = 0f
        }
        if (moveScore.toInt() != 0) {
            if (moveScore < 100) {
                score++
                moveScore--
                return
            } else if (moveScore < 250) {
                score += 5
                moveScore -= 5
                return
            } else if (moveScore < 500) {
                score += 12
                moveScore -= 12
                return
            } else {
                score += 23
                moveScore -= 23
                return
            }

        }
    }

//    private fun prevMoveStartSpeed(xFrom : Float, yFrom : Float) : Float {
//        for (lastMove in lastMoves) {
//            if (lastMove.xTo == xFrom && lastMove.yTo == yFrom) {
//                return lastMove.currentSpeed
//            }
//        }
//        return 0f
//    }

    // TODO: implement
    fun isGameOver() : Boolean {
        return movesLeft == 0
    }

}
