package day4

val inputFile = "/day4.txt"

val samples = """
        2-4,6-8
        2-3,4-5
        5-7,7-9
        2-8,3-7
        6-6,4-6
        2-6,4-8
    """.trimIndent()

data class Element ( val x: Int, val node: Int )

fun readData(str: String) =
    str
        .lines()
        .map {
            it.split(",")
                .map {
                    it
                        .split("-")
                        .map(String::toInt)
                }
                .mapIndexed { i, e ->
                    e.map {
                        Element(it, i)
                    }
                }
                .flatMap {
                    it
                }
        }
        .map {
            it.sortedBy { it.x }
        }


fun solution1(items : List<List<Element>>) =
    items.count {
        // middle values from same nodes
        it[1].node == it[2].node ||
                // OR first two values from different nodes, but equal
                ((it[0].node != it[1].node) && (it[0].x == it[1].x)) || (it[0].x == it[1].x && it[1].x == it[2].x) ||
                // OR last two values from different nodes, but equal
                ((it[2].node != it[3].node) && (it[2].x == it[3].x)) || (it[1].x == it[2].x && it[2].x == it[3].x)
    }

fun solution2(items : List<List<Element>>) =
    items.filter {
        // middle values from different nodes
        it[1].node == it[2].node ||
                // OR first two values from different nodes, but equal
                ((it[0].node != it[1].node) && (it[0].x == it[1].x)) || (it[0].x == it[1].x && it[1].x == it[2].x) ||
                // OR last two values from different nodes, but equal
                ((it[2].node != it[3].node) && (it[2].x == it[3].x)) || (it[1].x == it[2].x && it[2].x == it[3].x) ||
                // partial overlap
                ( it[0].node != it[1].node && it[1].node != it[2].node) || (it[1].node != it[2].node && it[1].x == it[2].x)
    }
    // .onEach { println(it) }
    .count()

fun main() {
    val sacks = readData(samples)
    println( solution1(sacks) )
    println( solution2(sacks) )
}
