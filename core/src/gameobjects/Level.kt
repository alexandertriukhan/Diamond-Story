package gameobjects

import java.util.*

data class Level(val gridTemplate: Array<IntArray> = arrayOf(IntArray(8),
        IntArray(8),IntArray(8),IntArray(8),IntArray(8),IntArray(8),IntArray(8),IntArray(8)),
            val objectives : Array<Objective> = arrayOf<Objective>(),
            val starsScores : IntArray = intArrayOf(0,0,0)) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Level

        if (!Arrays.equals(gridTemplate, other.gridTemplate)) return false
        if (!Arrays.equals(objectives, other.objectives)) return false
        if (!Arrays.equals(starsScores, other.starsScores)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = Arrays.hashCode(gridTemplate)
        result = 31 * result + Arrays.hashCode(objectives)
        result = 31 * result + Arrays.hashCode(starsScores)
        return result
    }
}