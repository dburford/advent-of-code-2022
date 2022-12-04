package day2

import printGrid
import java.io.File

val inputFile = "/day2.txt"

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
        .map{ 
            it.split("\n")
                .map{ it.toInt() } 
        }
}

// day 2 #1
fun solution1(items: List<List<Int>>) =
    items
        .map { it.sum() }
        .maxOrNull()

// day 2 #2
fun solution2(items: List<List<Int>>) =
    items
        .map { it.sum() }
        .sorted()
        .takeLast(3)
        .sum()

fun main() {
    val filedata = File("src/main/resources/day2.txt").readText()
    // val input = readData(samples)
    val input = readData(filedata)
    println(solution1(input))
    println(solution2(input))
}