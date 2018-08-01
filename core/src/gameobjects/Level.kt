package gameobjects

class Level(val gridTemplate: Array<IntArray> = arrayOf(IntArray(8),
        IntArray(8),IntArray(8),IntArray(8),IntArray(8),IntArray(8),IntArray(8),IntArray(8)),
            val objectives : Array<Objective> = arrayOf<Objective>(),
            val starsScores : IntArray = intArrayOf(0,0,0))