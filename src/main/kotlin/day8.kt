package day8

import aoc.util.geom.*

val inputFile = "/day8.txt"

val samples = """
30373
25512
65332
33549
35390
    """.trimIndent()

fun readData(str: String) =
  str
    .lines()
    .map(String::trim)
    .map {
      it.toList().map { it.toString().toInt() }.toIntArray()
    }.toTypedArray()

fun solution1(grid: Array<IntArray>) : Int {
  var countVisible = 0

  val visible = mutableSetOf<Pair<Int,Int>>()

  for (i in grid.indices) {
    var curMax = -1
    var rowMaxIndex = 0
    for (j in 0 until grid[i].size) {
      if ( grid[i][j] > curMax) {
        curMax = grid[i][j]
        // countVisible++
        rowMaxIndex = j
        visible.add(Pair(i,j))
      }
    }
    curMax = -1
    for (j in grid[i].size - 1 downTo rowMaxIndex ) {
      if ( grid[i][j] > curMax) {
        curMax = grid[i][j]
        // countVisible++
        visible.add(Pair(i, j))
      }
    }
  }

  for (j in grid[0].indices) {
    var colMaxIndex = 0
    var curMax = -1
    for (i in grid.indices) {
      if ( grid[i][j] > curMax) {
        curMax = grid[i][j]
        // countVisible++
        colMaxIndex = i
        visible.add(Pair(i, j))
      }
    }
    curMax = -1
    for (i in grid.size - 1 downTo colMaxIndex ) {
      if ( grid[i][j] > curMax) {
        curMax = grid[i][j]
        // countVisible++
        visible.add(Pair(i,j))
      }
    }
  }

  return visible.size
}

fun Array<IntArray>.cellOrNull(p : Point) : Int? {
  return this.getOrNull(p.y)?.getOrNull(p.x)
}

fun solution2(grid: Array<IntArray>) : Int {

  var max = 0
  // for each cell
  for (i in grid.indices) {
    for (j in grid[i].indices) {
      //  for each dir
      var total = 1
      val start =  Point(j,i)
      val startVal = grid[i][j]
      neighborOffsets.forEach {
        delta ->
          var cur = start
          var curVal: Int? = startVal
          var count = 0
          do {
            count++
            cur += delta
            curVal = grid.cellOrNull(cur)
            //    scan until cur >= cell
          } while ( curVal != null && curVal < startVal )
          if (curVal == null)
            count--

        total *= count
      }
      if (total > max)
        max = total
    }
  }

  return max
}

fun main() {
    val tree = readData(samples)
    println(tree)
    println( solution1(tree) )
    println( solution2(tree) )
}
