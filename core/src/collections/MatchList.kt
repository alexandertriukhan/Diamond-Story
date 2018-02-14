package collections

import com.badlogic.gdx.math.Vector2
import gameobjects.Match

class MatchList {

    private val list = mutableListOf<Match>()

    fun add(match: Match) {
        if (!contains(match)) {
            list.add(match)
        }
    }

    fun get() : List<Match> {
        return list
    }

    private fun contains(match: Match) : Boolean {
        for (m in list) {
            if (equalMatches(m,match)) {
                return true
            }
        }
        return false
    }

    private fun equalMatches(match1: Match, match2: Match) : Boolean {
        if (match1.gemsInMatch.count() == match2.gemsInMatch.count()) {
            for (xy in match1.gemsInMatch) {
                if (!containsVect(match2.gemsInMatch,xy)) {
                    return false
                }
            }
            return true
        }
        return false
    }

    private fun containsVect(list : List<Vector2>, xy : Vector2) : Boolean {
        for (item in list) {
            if (item.x == xy.x) {
                if (item.y == xy.y) {
                    return true
                }
            }
        }
        return false
    }

}