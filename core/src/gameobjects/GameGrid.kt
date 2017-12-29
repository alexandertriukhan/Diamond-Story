package gameobjects

import com.badlogic.gdx.math.Vector2
import enums.EffectType
import enums.JewelType
import enums.MatchType
import utils.TexturesLoader
import java.util.*
import kotlin.system.measureTimeMillis

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

    // TODO: implement check whether this position creates any match
    fun createsMatch(x : Int, y : Int, jewelType: JewelType) : Match {
        var up = 0
        var down = 0
        val gems = mutableListOf(Vector2(x.toFloat(),y.toFloat()))
        val match = Match(mutableListOf(),MatchType.NO_MATCH)
        if (x > 0) {
            if (cells[x - 1][y].jewel.jewelType == jewelType) {
                gems.add(Vector2((x - 1).toFloat(),y.toFloat()))
                if (x > 1)
                    if (cells[x - 2][y].jewel.jewelType == jewelType)
                        gems.add(Vector2((x - 2).toFloat(),y.toFloat()))
            }
        }
        if ((x + 1) < cells.count()) {
            if (cells[x + 1][y].jewel.jewelType == jewelType) {
                gems.add(Vector2((x + 1).toFloat(),y.toFloat()))
                if ((x + 2) < cells.count())
                    if (cells[x + 2][y].jewel.jewelType == jewelType)
                        gems.add(Vector2((x + 2).toFloat(),y.toFloat()))
            }
        }
        if (gems.count() < 3) {
            gems.clear()
            gems.add(Vector2(x.toFloat(),y.toFloat()))
        }
        if (y > 0) {
            if (cells[x][y - 1].jewel.jewelType == jewelType) {
                gems.add(Vector2(x.toFloat(),(y - 1).toFloat()))
                down++
                if (y > 1)
                    if (cells[x][y - 1].jewel.jewelType == jewelType) {
                        gems.add(Vector2(x.toFloat(), (y - 2).toFloat()))
                        down++
                    }
            }
        }
        if ((y + 1) < cells[0].count()) {
            if (cells[x][y + 1].jewel.jewelType == jewelType) {
                gems.add(Vector2(x.toFloat(),(y + 1).toFloat()))
                up++
                if ((y + 2) < cells[0].count())
                    if (cells[x][y + 2].jewel.jewelType == jewelType) {
                        gems.add(Vector2(x.toFloat(), (y + 2).toFloat()))
                        up++
                    }
            }
        }
        if ((down == 1 && up == 0) || (up == 1 && down == 0))
            gems.removeAt(gems.count() - 1)
        if (down == 1 && up == 1) {
            gems.removeAt(gems.count() - 1)
            gems.removeAt(gems.count() - 1)
        }
        if (gems.count() == 3)
            match.matchType = MatchType.MATCH3
        if (gems.count() == 4)
            match.matchType = MatchType.MATCH4
        if (gems.count() >= 5) {
            if (down + up < 2)
                match.matchType = MatchType.MATCH5
            else
                match.matchType = MatchType.MATCH_CROSS
        }
        if (match.matchType != MatchType.NO_MATCH) {
            for (gem in gems)
                match.gemsInMatch.add(Vector2(gem.x, gem.y))
        }
        return match
    }

    fun removeMatch(match : Match) {
        for (gem in match.gemsInMatch)
            cells[gem.x.toInt()][gem.y.toInt()].jewel.effect = EffectType.NOT_DRAW
    }

}
