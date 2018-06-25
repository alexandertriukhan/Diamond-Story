package enums

enum class BonusType(val value: Int) {
    HAMMER(0),
    MASH(1),
    BOMB(2),
    COLOR_REMOVE(3);

    companion object {
        fun from(findValue: Int): JewelType = JewelType.values().first { it.value == findValue }
    }
}