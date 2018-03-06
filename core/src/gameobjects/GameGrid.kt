package gameobjects

import collections.DestroyAnimsList
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import enums.EffectType
import enums.JewelType
import enums.MatchType
import utils.TexturesLoader
import java.util.*

// TODO: implement intArrayOf(0, 1, 1, 1) etc., also consider refactoring
class GameGrid(private val gridType : Array<IntArray>) {

    private val destroyAnimations = DestroyAnimsList()
    private val fallDownAcceleration = 0.45f  // ORIGINAL: 0.45f
    val lastMoves = mutableListOf<JewelMove>()

    var cells = Array(gridType.count(), {_ -> Array(gridType[0].count()
            , {_ -> Cell(false, Jewel(JewelType.from(Random().nextInt(5))),
            TexturesLoader.instance.tileBlank)})})
    val rows = cells.count()
    val cols = cells[0].count()
    var isFilled = false

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
                                        cells[i][j].jewel = Jewel(JewelType.from(Random().nextInt(5)))
                                }
                    }
                    if (j > 1) {
                        if (cells[i][j - 1].isPlaying)
                            if (cells[i][j - 2].isPlaying)
                                if (cells[i][j - 1].jewel.jewelType == cells[i][j].jewel.jewelType) {
                                    while (cells[i][j - 2].jewel.jewelType == cells[i][j].jewel.jewelType)
                                        cells[i][j].jewel = Jewel(JewelType.from(Random().nextInt(5)))
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
                        cells[i][j].tileTexture = TexturesLoader.instance.tileBlankLite
                    }
                }
                else if (i % 2 == 0) {
                    cells[i][j].tileTexture = TexturesLoader.instance.tileBlankLite
                }
            }
        }
        isFilled = true
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
                                                Jewel(JewelType.from(Random().nextInt(5))),8f))
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
                                                Jewel(JewelType.from(Random().nextInt(5))),8f))
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

    fun draw(batcher : SpriteBatch, delta: Float, gemSize : Float, gridOffset : Float) {
        for (i in cells.indices) {
            for (j in cells[i].indices) {
                if (cells[i][j].isPlaying) {
                    cells[i][j].drawTile(batcher,i.toFloat() * gemSize,
                            (j.toFloat() * gemSize) + gridOffset, gemSize)
                }
            }
        }
        for (i in cells.indices) {
            for (j in cells[i].indices) {
                if (cells[i][j].isPlaying) {
                    cells[i][j].drawJewel(batcher,i.toFloat() * gemSize,
                            (j.toFloat() * gemSize) + gridOffset, gemSize, delta)
                }
            }
        }
        drawDestroyAnimations(batcher,delta,gemSize,gridOffset)
    }

    fun isDrawing() : Boolean {
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

    fun swapCells(x1 : Int, y1 : Int, x2 : Int, y2 : Int) {
        val tmpCell = cells[x1][y1].jewel
        cells[x1][y1].jewel = cells[x2][y2].jewel
        cells[x2][y2].jewel = tmpCell
    }

    fun createsMatch(x : Int, y : Int, jewelType: JewelType) : Match {
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

    fun removeMatch(match : Match) : List<JewelMove> {
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
                                Jewel(cells[gem.x.toInt()][gem.y.toInt()].jewel.jewelType), 8f))  // ORIGINAL : 8f
                        moves.last().destroyOnEnd = true
                    }
                    cells[gem.x.toInt()][gem.y.toInt()].jewel.jewelType = JewelType.NO_JEWEL
                    cells[gem.x.toInt()][gem.y.toInt()].jewel.effect = EffectType.NONE
                }
            } else {
                destroyAnimations.add(DestroyAnimation(gem.x,gem.y,
                        Jewel(cells[gem.x.toInt()][gem.y.toInt()].jewel.jewelType),EffectType.NONE))
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
            destroyAnimations.add(DestroyAnimation(xy.x,xy.y,
                    Jewel(cells[xy.x.toInt()][xy.y.toInt()].jewel.jewelType),EffectType.FIRE))
            cells[xy.x.toInt()][xy.y.toInt()].jewel.jewelType = JewelType.NO_JEWEL
        }
    }

    private fun crossDestroy(x: Float, y: Float) {
        cells[x.toInt()][y.toInt()].jewel.effect = EffectType.NONE
        destroyAnimations.add(DestroyAnimation(x,y,
                Jewel(cells[x.toInt()][y.toInt()].jewel.jewelType),EffectType.CROSS))  // TODO: dont add for x y
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
            destroyAnimations.add(DestroyAnimation(row.toFloat(),y,
                    Jewel(cells[row][y.toInt()].jewel.jewelType),EffectType.FIRE))
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
            destroyAnimations.add(DestroyAnimation(x,col.toFloat(),
                    Jewel(cells[x.toInt()][col].jewel.jewelType),EffectType.FIRE))
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

    private fun drawDestroyAnimations(batch: SpriteBatch, delta: Float, size: Float, gridOffset: Float) {
        if (destroyAnimations.list.isNotEmpty()) {
            val iterator = destroyAnimations.list.iterator()
            while (iterator.hasNext()) {
                val anim = iterator.next()
                if (anim.isStopped()) {
                    iterator.remove()
                } else {
                    anim.draw(batch, delta, size, gridOffset)
                }
            }
        }
    }

    fun fallDownRow(row : Int) : List<JewelMove> {
        val moves = mutableListOf<JewelMove>()
        for (i in cells.indices) {
            var leftTurn = false
            var rightTurn = false
            if (cells[i][row].isPlaying && cells[i][row].jewel.jewelType == JewelType.NO_JEWEL) {
                if (row < cells[0].count() - 1) {
                    if (!cells[i][row + 1].isBlocked()) {
                        moves.add(JewelMove(i.toFloat(), (row + 1).toFloat(), i.toFloat(), row.toFloat(),
                                Jewel(cells[i][row + 1].jewel.jewelType,
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
                                        Jewel(cells[i - 1][row + 1].jewel.jewelType,
                                                cells[i - 1][row + 1].jewel.effect), 5f))
                                cells[i - 1][row + 1].jewel.jewelType = JewelType.NO_JEWEL
                            } else {
                                moves.add(JewelMove((i + 1).toFloat(), (row + 1).toFloat(), i.toFloat(), row.toFloat(),
                                        Jewel(cells[i + 1][row + 1].jewel.jewelType,
                                                cells[i + 1][row + 1].jewel.effect), 5f))
                                cells[i + 1][row + 1].jewel.jewelType = JewelType.NO_JEWEL
                            }
                        } else if (leftTurn) {
                            moves.add(JewelMove((i - 1).toFloat(), (row + 1).toFloat(), i.toFloat(), row.toFloat(),
                                    Jewel(cells[i - 1][row + 1].jewel.jewelType,
                                            cells[i - 1][row + 1].jewel.effect), 5f))
                            cells[i - 1][row + 1].jewel.jewelType = JewelType.NO_JEWEL
                        } else if (rightTurn) {
                            moves.add(JewelMove((i + 1).toFloat(), (row + 1).toFloat(), i.toFloat(), row.toFloat(),
                                    Jewel(cells[i + 1][row + 1].jewel.jewelType,
                                            cells[i + 1][row + 1].jewel.effect), 5f))
                            cells[i + 1][row + 1].jewel.jewelType = JewelType.NO_JEWEL
                        }
                    }
                } else {
                    moves.add(JewelMove(i.toFloat(), cells[0].count().toFloat(),
                            i.toFloat(), row.toFloat(), Jewel(JewelType.from(Random().nextInt(5)),
                            EffectType.NONE),5f))
                }
            }
        }
        return moves
    }

//    private fun prevMoveStartSpeed(xFrom : Float, yFrom : Float) : Float {
//        for (lastMove in lastMoves) {
//            if (lastMove.xTo == xFrom && lastMove.yTo == yFrom) {
//                return lastMove.currentSpeed
//            }
//        }
//        return 0f
//    }

}
