package day15

import aoc.util.geom.*

val inputFile = "/day15.txt"

val samples = """
    Sensor at x=2, y=18: closest beacon is at x=-2, y=15
    Sensor at x=9, y=16: closest beacon is at x=10, y=16
    Sensor at x=13, y=2: closest beacon is at x=15, y=3
    Sensor at x=12, y=14: closest beacon is at x=10, y=16
    Sensor at x=10, y=20: closest beacon is at x=10, y=16
    Sensor at x=14, y=17: closest beacon is at x=10, y=16
    Sensor at x=8, y=7: closest beacon is at x=2, y=10
    Sensor at x=2, y=0: closest beacon is at x=2, y=10
    Sensor at x=0, y=11: closest beacon is at x=2, y=10
    Sensor at x=20, y=14: closest beacon is at x=25, y=17
    Sensor at x=17, y=20: closest beacon is at x=21, y=22
    Sensor at x=16, y=7: closest beacon is at x=15, y=3
    Sensor at x=14, y=3: closest beacon is at x=15, y=3
    Sensor at x=20, y=1: closest beacon is at x=15, y=3
    """.trimIndent()

data class Sensor(val position: Point, val beacon: Point) {
  val range = manhattanDistance(position, beacon)
  fun inRange(p: Point) = manhattanDistance(p, position) <= range
}

fun readData(str: String,) =
  str
    .lines()
    .map {
      val match = Regex("Sensor at x=([-+]?\\d*), y=([-+]?\\d*): closest beacon is at x=([-+]?\\d*), y=([-+]?\\d*)").find(it)!!
      match
        .destructured
        .let { (x1, y1, x2, y2) ->
          Sensor(Point(x1.toInt(),y1.toInt()), Point(x2.toInt(),y2.toInt()))
        }
    }

fun impossiblePointsBySet(sensors: List<Sensor>, beacons: HashSet<Point>,y: Int) : Int {
  val impossiblePoints = mutableSetOf<Point>()
  sensors.forEach {
    val d = abs(it.position.y - y)
    val overlap = it.range - d
    if (overlap > 0) {
      val line = Line.create(Point(it.position.x - overlap, y), Point(it.position.x + overlap, y))
      val points = line.walkTheLine()
      points.forEach { if (!beacons.contains(it)) impossiblePoints.add(it)}
    }
  }
  return impossiblePoints.size
}

fun findSegments(sensors: List<Sensor>, y: Int) : List<Line> {
  val segments = sensors.mapNotNull {
    val d = abs(it.position.y - y)
    val overlap = it.range - d
    if (overlap > 0) {
      Line.create(Point(it.position.x - overlap, y), Point(it.position.x + overlap, y))
    }
    else {
      null
    }
  }
  return Line.mergeHorizontalSegments(segments)
}

fun impossiblePointsBySegments(sensors: List<Sensor>, y: Int): Int {
  val segments = findSegments(sensors, y)
  return segments.map { it.e.x - it.s.x }.sum()
}

fun solution1(sensors: List<Sensor>, y: Int) : Int {
  return impossiblePointsBySegments(sensors, y)
}

fun solution2(sensors: List<Sensor>, maxy: Int): Long? {
  for (i in 0 .. maxy) {
    val segments = findSegments(sensors, i)
    if (segments.size > 1) {
      return Point(segments[0].e.x + 1, i).let{
        it.x * 4000000L + it.y
      }
    }
  }
  return null
}


fun main() {
  val sensors = readData(samples)
  println(sensors)
  println( solution1(sensors, 10) )
  println( solution2(sensors, 20) )
}

/*

References:
 - https://math.stackexchange.com/questions/383321/rotating-x-y-points-45-degrees
 - https://math.stackexchange.com/questions/2312409/converting-diamonds-in-a-2d-plane-into-squares
 - https://en.wikipedia.org/wiki/Rotation_matrix

 */


