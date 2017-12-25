package gameobjects

import com.badlogic.gdx.math.Vector2
import enums.EffectType
import enums.JewelType
import utils.TexturesLoader
import java.util.*

// TODO: implement intArrayOf(0, 1, 1, 1) etc., also consider refactoring
class GameGrid(private val gridType : Array<IntArray>) {

    var cells = Array(gridType.count(), {_ -> Array(gridType[0].count()
            , {_ -> Cell(false, Jewel(JewelType.from(Random().nextInt(5)),
            EffectType.NONE),TexturesLoader.instance.tileBlank)})})

    init {
        for (i in gridType.indices) {
            for (j in gridType[i].indices) {
                cells[i][j].isPlaying = (gridType[i][j] == 1)

                // Making check no occurrences of 3 gems are presented
                if (i > 1) {
                    if (cells[i - 1][j].isPlaying)
                        if (cells[i - 2][j].isPlaying)
                            while (cells[i - 2][j].jewel.jewelType == cells[i][j].jewel.jewelType)
                                cells[i][j].jewel = Jewel(JewelType.from(Random().nextInt(5)), EffectType.valueOf("NONE"))
                }
                if (j > 1) {
                    if (cells[i][j - 1].isPlaying)
                        if (cells[i][j - 2].isPlaying)
                            while (cells[i][j - 2].jewel.jewelType == cells[i][j].jewel.jewelType)
                                cells[i][j].jewel = Jewel(JewelType.from(Random().nextInt(5)), EffectType.valueOf("NONE"))
                }

                val borders = getBorders(i, j, gridType)
                if (borders.contentEquals(intArrayOf(0, 0, 0, 1))) {
                    cells[i][j].tileTexture = TexturesLoader.instance.tileTop
                }
                if (borders.contentEquals(intArrayOf(0, 1, 0, 0))) {
                    cells[i][j].tileTexture = TexturesLoader.instance.tileRight
                }
                if (borders.contentEquals(intArrayOf(1, 0, 0, 0))) {
                    cells[i][j].tileTexture = TexturesLoader.instance.tileDown
                }
                if (borders.contentEquals(intArrayOf(0, 0, 1, 0))) {
                    cells[i][j].tileTexture = TexturesLoader.instance.tileLeft
                }
                if (borders.contentEquals(intArrayOf(1, 1, 0, 0))) {
                    cells[i][j].tileTexture = TexturesLoader.instance.tileCornerRightDown
                }
                if (borders.contentEquals(intArrayOf(1, 0, 1, 0))) {
                    cells[i][j].tileTexture = TexturesLoader.instance.tileCornerLeftDown
                }
                if (borders.contentEquals(intArrayOf(0, 0, 1, 1))) {
                    cells[i][j].tileTexture = TexturesLoader.instance.tileCornerLeftTop
                }
                if (borders.contentEquals(intArrayOf(0, 1, 0, 1))) {
                    cells[i][j].tileTexture = TexturesLoader.instance.tileCornerRightTop
                }
            }
        }
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

    fun inRange(x : Int, y : Int) : Boolean {
        return x >= 0 && y >= 0 && x < gridType.count() && y < gridType[0].count()
    }

    fun swapCells(x1 : Int, y1 : Int, x2 : Int, y2 : Int) {
        val tmpCell = cells[x1][y1].jewel
        cells[x1][y1].jewel = cells[x2][y2].jewel
        cells[x2][y2].jewel = tmpCell
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

    fun hasMatches() : MutableList<Match> {
        // TODO: change fun to find 4 in a row and 5 in a row
        val matches = mutableListOf<Match>()
        for (i in cells.indices) {
            for (j in cells[i].indices) {
                if (cells[i][j].isPlaying) {
                    if (i > 1) {
                        val gem1 = cells[i][j]
                        val gem2 = cells[i - 1][j]
                        val gem3 = cells[i - 2][j]
                        if (gem1.jewel.jewelType == gem2.jewel.jewelType && gem1.jewel.jewelType == gem3.jewel.jewelType)
                            matches.add(Match(Vector2(i.toFloat(), j.toFloat()),
                                    Vector2((i - 1).toFloat(), j.toFloat()), Vector2((i - 2).toFloat(), j.toFloat())))
                    }
                    if (j > 1) {
                        val gem1 = cells[i][j]
                        val gem2 = cells[i][j - 1]
                        val gem3 = cells[i][j - 2]
                        if (gem1.jewel.jewelType == gem2.jewel.jewelType && gem1.jewel.jewelType == gem3.jewel.jewelType)
                            matches.add(Match(Vector2(i.toFloat(), j.toFloat()),
                                    Vector2(i.toFloat(), (j - 1).toFloat()), Vector2(i.toFloat(), (j - 2).toFloat())))
                    }
                }
            }
        }
        return matches
    }

    fun removeMatches(matches : MutableList<Match>) {
        for (match in matches) {
            // TODO: change to a falling down gems algorythm
            cells[match.gem1.x.toInt()][match.gem1.y.toInt()].jewel = Jewel(JewelType.from(Random().nextInt(5)),EffectType.NONE)
            cells[match.gem2.x.toInt()][match.gem2.y.toInt()].jewel = Jewel(JewelType.from(Random().nextInt(5)),EffectType.NONE)
            cells[match.gem3.x.toInt()][match.gem3.y.toInt()].jewel = Jewel(JewelType.from(Random().nextInt(5)),EffectType.NONE)
            matches.remove(match)
        }
    }

}