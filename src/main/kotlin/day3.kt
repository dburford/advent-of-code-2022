package day3

val inputFile = "/day3.txt"

val samples = """
        vJrwpWtwJgWrhcsFMMfFFhFp
        jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
        PmmdzqPrVvPwwTWBwg
        wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
        ttgJtRGJQctTZtZT
        CrZsJsPPZsGzwwsLwLmpwMDw
    """.trimIndent()

fun charToInt(c : Char) =
    when {
      c > 'a' -> (c - 'a')
      else -> (c - 'A') + 26
    }.toInt() + 1

fun String.stringToIntList() =
    this.toList().map ( ::charToInt  )

fun readData(str: String) =
    str
        .lines()
        .map{
            Pair(
                it.substring(0, it.length / 2)
                    .stringToIntList(),
                it.substring(it.length/2)
                    .stringToIntList()
            )
        }

fun solution1(sacks: List<Pair<List<Int>, List<Int>>>) =
    sacks.sumOf { (p, q) ->
        q.first { p.toHashSet().contains(it) }
    }

fun setNthBit(n: ULong, k: Int) =  (1UL shl k or n )

// determine position of first set bit
fun log2(n : ULong ) = n.countTrailingZeroBits()

fun solution2Bitmaps(sacks: List<Pair<List<Int>, List<Int>>>) =
    sacks
        .asSequence()
        // combine compartments
        .map {
            it.first + it.second
        }
        // consider group of 3 elves
        .chunked(3)
        .map { group ->
            group
                .map { elf ->
                    // construct bitmap of 64 bits, with position set if the corresponding letter appears (a=1 .. Z=52)
                    elf.fold(0UL) { acc, i ->
                        setNthBit(acc, i)
                    }
                }
                // determine which bit place is set for all items
                .fold(ULong.MAX_VALUE) { acc, i ->
                    acc and i
                }
        }
        // determine position of bit that's set - this indicates the letter that's common across all 3 elves
        .map ( ::log2 )
        .sum()

fun solution2HashSets(sacks: List<Pair<List<Int>, List<Int>>>) =
    sacks
        .asSequence()
        // combine compartments
        .map {
            (it.first + it.second).toHashSet()
        }
        // consider group of 3 elves
        .chunked(3)
        .sumOf { group ->
            group
                // find intersection (common letters) of collections
                .reduce { acc, i ->
                    HashSet<Int>(acc.intersect(i))
                }
                .first()
        }

val solution2 = ::solution2HashSets

fun main() {
    val sacks = readData(samples)
    println( solution1(sacks) )
    println( solution2(sacks) )
}
