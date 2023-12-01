package day5

val inputFile = "/day5.txt"

val samples = """
    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2
    """.trimIndent()

data class StackMove(val count : Int, val src: Int, val dest:Int)

fun readData(str: String) =
    str
        .split("\n\n")
        .let {
            (stacks, moves) ->
                Pair(
                    stacks.lines()
                        // create 9 stacks
                        .fold( MutableList<MutableList<Char>>(9) { mutableListOf() }) {
                            acc, s ->
                                // capture "[A]" style elements
                                s.windowed(3, 4, true)
                                    .forEachIndexed { index, s ->
                                        // add to stack if not empty or number
                                        if (s.matches(Regex("\\[.*\\]")))
                                            acc[index].add(s[1])
                                    }
                            acc
                        }
                    ,
                    moves.lines()
                        .map {
                            // capture moves
                            val match = Regex("move (\\d+) from (\\d+) to (\\d+)").find(it)!!
                            match
                                .destructured
                                .let { (count, src, dest) ->
                                    StackMove(count.toInt(), src.toInt(), dest.toInt())
                                }
                        }
                )
        }

fun solution1(items : Pair<MutableList<MutableList<Char>>, List<StackMove>>) =
      items.let {
          (stacks, moves) ->
          moves
              .fold(stacks) {
                  acc, m ->
                      // pop from src, push to dest
                      (1..m.count).forEach {
                          acc[m.dest-1].add(0, acc[m.src-1].removeFirst())
                      }
                      acc
          }
      }
      .fold("") { acc, stack ->
          acc + stack.getOrElse(0) {""}
      }

fun solution2(items : Pair<MutableList<MutableList<Char>>, List<StackMove>>) =
    items
        .let {
            (stacks, moves) ->
                moves
                    .fold(stacks) {
                        acc, m ->

                        val src = acc[m.src-1]
                        val dest = acc[m.dest-1]
                        dest.addAll(0, src.subList(0, m.count))
                        acc[m.src-1] = src.subList(m.count, src.size)

                        acc
        }
    }
    .fold("") { acc, stack ->
      acc + stack.getOrElse(0) {""}
    }

fun main() {
    val sacks = readData(samples)
    // problem - using mutable lists means we can't run soln 2 after soln 1 without resetting data
    // println( solution1(sacks) )
    println( solution2(sacks) )
}
