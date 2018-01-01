package gameobjects

import com.badlogic.gdx.math.Vector2
import enums.MatchType

class Match(val gemsInMatch : MutableList<Vector2>, var matchType : MatchType) {

    fun maxY() : Int {
        var maxY = 0
        for (gem in gemsInMatch)
            if (gem.y > maxY)
                maxY = gem.y.toInt()
        return maxY
    }

    fun mergeIn(match : Match) {
        for (gem in match.gemsInMatch)
            gemsInMatch.add(gem)
    }


}