package gameobjects

import enums.EffectType
import enums.GridType
import enums.JewelType
import java.util.*

class JewelGrid(val size : Int, val type : GridType) {

    // TODO: implement different types of grid
    public val cells = Array<Array<Jewel>>(size,{_ -> Array<Jewel>(size,
            {_ -> Jewel(JewelType.from(Random().nextInt(5)), EffectType.NONE)})})

    init {
        if (type == GridType.SQUARE) {
            initSquare()
        }
        if (type == GridType.CROSS) {
            initCross()
        }
    }

    fun initSquare() {

    }

    fun initCross() {

    }

}