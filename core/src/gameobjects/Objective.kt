package gameobjects

import enums.ObjectiveType

data class Objective(val type : ObjectiveType = ObjectiveType.NONE, val posX : Int = 0, val posY : Int = 0)