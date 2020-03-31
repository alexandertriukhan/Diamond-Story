package gameobjects

import enums.ObjectiveType

data class Objective(val type : ObjectiveType = ObjectiveType.NONE, val count: Int = 0)
