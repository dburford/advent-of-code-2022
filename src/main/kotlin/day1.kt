package day1

import java.io.File

val inputFile = "/day1.txt"

val samples = """
        1000
        2000
        3000

        4000

        5000
        6000

        7000
        8000
        9000

        10000
    """.trimIndent()

fun readData(str: String): List<List<Int>> {
    return str
        .split("\n\n")
        .map{ it.split("\n").map{ it.toInt() } }
        // .filterNot { it.isBlank() }
        // .map { it.toInt() }
}

// day 1 #1
fun solution1(items: List<List<Int>>) =
    items
        .map { it.sum() }
        .maxOrNull()

fun main() {
    val filedata = File("../resources/day1.txt").readText()
    // val input = readData(samples)
    val input = readData(filedata)
    // print(input)
    println(solution1(input))
    println(solution2(input))

}


// day 1 #2
fun solution2(items: List<List<Int>>) =
    items
        .map { it.sum() }
        .sorted()
        .takeLast(3)
        .sum()
