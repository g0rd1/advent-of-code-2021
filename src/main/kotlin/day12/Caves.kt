package day12

class Caves(CaveConnectionInfo: List<String>) {

    private val caves: List<Cave>
    private val caveToConnectedCaves: Map<Cave, List<Cave>>

    init {
        val startToEnds = CaveConnectionInfo.map {
            val split = it.split("-")
            split[0] to split[1]
        }
        caves = getCaves(startToEnds)
        caveToConnectedCaves = caves.associateWith {
            getConnectedCaves(it, startToEnds)
        }
    }

    fun getRoutes(smallCaveVisitTimes: Int, startRoutes: List<List<Cave>> = emptyList()): List<List<Cave>> {
        if (startRoutes.isEmpty()) return getRoutes(
            smallCaveVisitTimes,
            listOf(listOf(caves.first { it.isStartCave() }))
        )
        if (startRoutes.all { it.size > 1 && it.first().isStartCave() && it.last().isEndCave() }) return startRoutes
        val newRoutes: MutableList<List<Cave>> = startRoutes.toMutableList()
        startRoutes.forEach { route ->
            if (route.last().isEndCave()) return@forEach
            getConnectedCaves(route.last())
                .filter { cave ->
                    if (cave.isStartCave()) return@filter false
                    if (cave.isEndCave()) return@filter true
                    if (!cave.isSmall()) return@filter true
                    if (!route.contains(cave)) return@filter true
                    route.filter { it.isSmall() }.groupingBy { it }.eachCount().none { (_, count) ->
                        count >= smallCaveVisitTimes
                    }
                }
                .forEach caveForEach@{ cave ->
                    newRoutes.add((route + cave))
                }
            newRoutes.remove(route)
        }
        return getRoutes(smallCaveVisitTimes, newRoutes)
    }

    private fun getConnectedCaves(cave: Cave): List<Cave> {
        return caveToConnectedCaves[cave]!!
    }

    private fun getCaves(startToEnds: List<Pair<String, String>>): List<Cave> {
        return startToEnds.flatMap { it.toList() }.distinct().map { name ->
            getCaveForName(name)
        }
    }

    private fun getConnectedCaves(cave: Cave, startToEnds: List<Pair<String, String>>): List<Cave> {
        return startToEnds.filter { (start, end) -> start == cave.name || end == cave.name }
            .flatMap { it.toList() }
            .distinct()
            .filter { it != cave.name }
            .map { getCaveForName(it) }
    }

    private fun getCaveForName(name: String): Cave {
        return if (name.toUpperCase() == name) {
            Cave.Big(name)
        } else {
            Cave.Small(name)
        }
    }

}
