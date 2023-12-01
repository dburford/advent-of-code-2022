package day14

import aoc.util.geom.*

val inputFile = "/day14.txt"

val samples = """
    498,4 -> 498,6 -> 496,6
    503,4 -> 502,4 -> 502,9 -> 494,9
    """.trimIndent()


fun readData(str: String,) =
  str
    .lines()
    .map {
      it
        .split("->")
        .map {
          it
            .trim()
            .split(",")
            .let {
                (a,b) -> Point (a.toInt(), b.toInt())
            }
        }
        .zipWithNext { a, b -> Line.create(a,b) }
    }
    .flatten().let {
      it
    }

val DOWN = Point(0, 1)
val DOWN_LEFT = Point(-1, 1)
val DOWN_RIGHT = Point(1, 1)

fun collision(
  p: Point,
  lines: List<Line>,
  balls: MutableSet<Point>,
  floor: Int
) = lines.any{ it.intersects(p) } || p in balls || p.y == floor

fun simulate(lines: List<Line>, addFloor: Boolean = false) : Int {
  var lineEndPoints = lines
    .map { listOf(it.s, it.e) }
    .flatten()

  lineEndPoints += Point(500, 0)
  println(lineEndPoints)
  printPoints(lineEndPoints)
  println()

  val bounds = Bounds.create(lineEndPoints)

  val floor = if (addFloor) bounds.lowerRight.y + 2 else -1

  val start = Point(500, 0)
  val balls = mutableSetOf<Point>()

  var current = start
  var new = start

  while (true) {

    new = current + DOWN
    if (collision(new, lines, balls, floor)) {
      new = current + DOWN_LEFT
      if (collision(new, lines, balls, floor)) {
        new = current + DOWN_RIGHT
        if (collision(new, lines, balls, floor)) {
          balls.add(current)
          new = start
          lineEndPoints += current
          // printPoints2(lineEndPoints)
          // println()
        }
      }
    }

    current = new

    if (!addFloor && !bounds.contains(current))
      return balls.size;

    if (start in balls) {
      printPoints(lineEndPoints)
      println()
      return balls.size
    }

  }
  return -1
}

fun solution1(lines: List<Line>) =
  simulate(lines)


fun solution2(lines: List<Line>) =
  simulate(lines, true)

fun main() {
  val lists = readData(samples)
  println(lists)
  println( solution1(lists) )
  println( solution2(lists) )
}


