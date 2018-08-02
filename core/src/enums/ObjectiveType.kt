package enums

enum class ObjectiveType(val value : Int) {

    NONE(0),
    CHAINED(1),
    GLASS_LITE(2),
    GLASS_STRONG(3),
    BARREL(4),
    BOMB(5);

    companion object {
        fun from(findValue: Int): MatchType = MatchType.values().first { it.value == findValue }
    }
}