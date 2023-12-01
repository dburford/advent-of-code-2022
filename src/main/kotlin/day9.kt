package day9

import aoc.util.geom.*
import kotlin.math.abs

val inputFile = "/day9.txt"

val samples = """
    R 4
    U 4
    L 3
    D 1
    R 4
    D 1
    L 5
    R 2
    """.trimIndent()

val samples2 = """
    R 5
    U 8
    L 8
    D 3
    R 17
    D 10
    L 25
    U 20
    """.trimIndent()


val directions = hashMapOf<String, Point>(
  "L" to Point(-1, 0),
  "R" to Point( 1, 0),
  "U" to Point(0, 1),
  "D" to Point(0, -1)
)

fun readData(str: String) =
  str
    .lines()
    .map {
      it.split(" ")
    }
    .map {
        (dir, number) ->
      Pair(directions[dir]!!, number.toInt())
    }

fun solution1(moves: List<Pair<Point, Int>>) : Int {

  val visited = mutableSetOf<Point>()

  // init s, H, T to bottom left grid[grid.size-1][0]
  // for each move
  //  for each iteration
  //    calc new H
  //    determine dx, dy btw H and T
  //    decide new T
  //      if |dx| >= 2 or |dy| >= 2
  //        dT.x = dx / |dx|
  //        dT.y = dy / |dy|
  //    update T = T + dT
  //    record T in set

  var s = Point(0, 0)
  var H = s
  var T = s

  visited.add(T)

  for (move in moves) {
    val (delta, count) = move
    for (i in 1.. count) {
      H += delta
      val (dx, dy) = H - T
      val absdx = abs(dx)
      val absdy = abs(dy)
      if (absdx >= 2 || absdy >= 2) {
        val dT = Point(
          if (absdx > 0) dx / abs(dx) else 0,
          if (absdy > 0) dy / abs(dy) else 0
        )
        T += dT
        visited.add(T)
      }
    }
  }

  return visited.size
}


fun solution2(moves: List<Pair<Point, Int>>) : Int {
  val visited = mutableSetOf<Point>()

  var s = Point(0, 0)
  var nodes = MutableList<Point>(10) { s }

  visited.add(s)

  for (move in moves) {
    val (delta, count) = move
    for (i in 1.. count) {
      nodes[0] += delta
      for (i in 1 until nodes.size) {
        val (dx, dy) = nodes[i-1] - nodes[i]
        val absdx = abs(dx)
        val absdy = abs(dy)
        if (absdx >= 2 || absdy >= 2) {
          val dT = Point(
            if (absdx > 0) dx / abs(dx) else 0,
            if (absdy > 0) dy / abs(dy) else 0
          )
          nodes[i] += dT
        }
        visited.add(nodes[nodes.size-1])
      }
    }
  }

  println(visited)
  printPoints(visited.toList())
  return visited.size
}

fun main() {
  val moves = readData(samples)
  println( solution1(moves) )

  val moves2 = readData(samples2)
  println( solution2(moves2) )
}

