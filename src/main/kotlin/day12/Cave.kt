package day12

sealed class Cave(
    open val name: String,
) {
    data class Big(
        override val name: String,
    ) : Cave(name)

    data class Small(
        override val name: String,
    ) : Cave(name)

    fun isStartCave() = this is Small && this.name == "start"
    fun isEndCave() = this is Small && this.name == "end"
    fun isSmall() = this is Small
}
