package gameobjects

import com.badlogic.gdx.math.Vector2
import enums.MatchType

class Match(val gemsInMatch : MutableList<Vector2>, var matchType : MatchType) {

    fun firstGem() : Vector2 {
        return gemsInMatch.first()
    }

    fun maxY() : Int {
        var maxY = Int.MIN_VALUE
        for (gem in gemsInMatch)
            if (gem.y > maxY)
                maxY = gem.y.toInt()
        return maxY
    }

    fun minY() : Int {
        var minY = Int.MAX_VALUE
        for (gem in gemsInMatch)
            if (gem.y < minY)
                minY = gem.y.toInt()
        return minY
    }

    fun mergeIn(match : Match) {
        for (gem in match.gemsInMatch)
            gemsInMatch.add(gem)
    }


}