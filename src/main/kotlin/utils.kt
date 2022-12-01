data class Point(val x: Int, val y: Int) {
    fun print() {
        print("(${x},${y})")
    }
}

val neighborOffsets = listOf(
    Pair(-1, 0),
    Pair(1, 0),
    Pair(0, -1),
    Pair(0, 1)
)

val allNeighborOffsets = listOf(
    Pair(-1, -1),
    Pair(-1, 0),
    Pair(-1, 1),
    Pair(0, -1),
    Pair(0, 1),
    Pair(1, -1),
    Pair(1, 0),
    Pair(1, 1)
)

fun loadFromResource(res: String): String {
    return object {}.javaClass.getResource(res).readText()
}

fun List<Long>.median(): Long {
    val n = count()
    val sorted = sorted()
    return when (n % 2) {
        1 -> sorted[n / 2]
        0 -> (sorted[n / 2] + sorted[(n / 2) - 1]) / 2
        else -> throw Exception("Unexpected number")
    }
}

fun <T : Comparable<T>> Iterable<T>.mapToFrequencies() =
    fold(mutableMapOf<T, Int>(), { f, i ->
        f.merge(i, 1, Int::plus)
        f
    })

fun <T : Comparable<T>> Iterable<T>.mapToFrequenciesLong() =
    fold(mutableMapOf<T, Long>(), { f, i ->
        f.merge(i, 1, Long::plus)
        f
    })

fun printGrid(grid: List<List<Int>>) {
    grid.forEach {
        it.forEach {
            print("$it")
        }
        println()
    }
}

fun printCharGrid(grid: List<List<Char>>) {
    grid.forEach {
        it.forEach {
            print("$it")
        }
        println()
    }
}

fun printPoints(points: List<Point>) {
    val maxX = points.maxOf { (x, _) -> x }
    val maxY = points.maxOf { (_, y) -> y }

    val grid = MutableList(maxY + 1) { MutableList(maxX + 1) { '.' } }
    points.forEach { (x, y) -> grid[y][x] = '#' }

    printCharGrid(grid)
}
