package day11

sealed class Energy {
    data class StoreEnergy(val energy: Int) : Energy() {
        operator fun plus(other: Int): StoreEnergy = StoreEnergy(this.energy + other)
    }

    object Flashed : Energy()
}
