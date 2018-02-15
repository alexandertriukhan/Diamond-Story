package collections

import gameobjects.DestroyAnimation

class DestroyAnimsList {

    val list = mutableListOf<DestroyAnimation>()

    fun add(anim: DestroyAnimation) {
        if (!contains(anim)) {
            list.add(anim)
        }
    }

    fun get() : List<DestroyAnimation> {
        return list
    }

    private fun contains(anim: DestroyAnimation) : Boolean {
        for (a in list) {
            if (equalAnims(a,anim)) {
                return true
            }
        }
        return false
    }

    private fun equalAnims(anim1: DestroyAnimation, anim2: DestroyAnimation) : Boolean {
        if (anim1.x == anim2.x) {
            if (anim1.y == anim2.y) {
                return true
            }
        }
        return false
    }

}