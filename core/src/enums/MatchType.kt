package enums

enum class MatchType(val value : Int) {
    NO_MATCH(0),
    MATCH3(3),
    MATCH4(4),
    MATCH5(5),
    MATCH_CROSS(4);

    companion object {
        fun from(findValue: Int): MatchType = MatchType.values().first { it.value == findValue }
    }
}