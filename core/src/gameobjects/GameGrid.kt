package gameobjects

import enums.EffectType
import enums.JewelType
import java.util.*


class GameGrid(val gridType : Array<IntArray>) {
    // TODO: implement different types of grid
    var cells = Array<Array<Cell>>(gridType[0].count(), {_ -> Array<Cell>(gridType[0].count()
            , {_ -> Cell(false, Jewel(JewelType.from(Random().nextInt(5).toInt()),EffectType.NONE))})})

    init {
        for (i in gridType.indices) {
            for (j in gridType[i].indices) {
                cells[i][j].isPlaying = (gridType[i][j] == 1)
            }
        }
    }

    fun initSquare() {

    }

    fun initCross() {

    }

}