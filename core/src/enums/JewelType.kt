package enums

enum class JewelType(val value: Int) {
    RED(0),
    GREEN(1),
    BLUE(2),
    PURPLE(3),
    YELLOW(4);

    companion object {
        fun from(findValue: Int): JewelType = JewelType.values().first { it.value == findValue }
    }
}
