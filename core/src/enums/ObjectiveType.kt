package enums

enum class ObjectiveType(val value : Int) {

    CHAINED(0),
    GLASS_LITE(1),
    GLASS_STRONG(2),
    BARREL(3);

    companion object {
        fun from(findValue: Int): MatchType = MatchType.values().first { it.value == findValue }
    }
}