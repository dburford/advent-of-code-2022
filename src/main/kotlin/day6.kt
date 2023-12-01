package day6

val inputFile = "/day6.txt"

val samples = """
      mjqjpqmgbljsphdztnvjfqwrcgsmlb
    """.trimIndent()

fun readData(str: String) =
    str

fun firstUniqueSet(items : String, size: Int) =
  items
    .windowedSequence(size)
    .indexOfFirst {
      it.toHashSet().size == size
    } + size

fun solution1(items : String) =
  firstUniqueSet(items, 4)

fun solution2(items : String) =
  firstUniqueSet(items, 14)

fun main() {
    val sacks = readData(samples)
    println( solution1(sacks) )
    println( solution2(sacks) )
}
