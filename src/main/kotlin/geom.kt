package aoc.util.geom

fun abs(x: Int) = if (x<0) -x else x
fun abs(x: Long) = if (x<0L) -x else x

fun manhattanDistance(p: Point, q: Point) =
  abs(p.x - q.x) + abs(p.y - q.y)

data class Point(val x: Int, val y: Int) {
  fun print() {
    print("(${x},${y})")
  }

  operator fun plus(p : Point) : Point {
    return Point(this.x+p.x, this.y+p.y)
  }

  operator fun minus(p : Point) : Point {
    return Point(this.x-p.x, this.y-p.y)
  }
}

val neighborOffsets = listOf(
  Point(-1, 0),
  Point(1, 0),
  Point(0, -1),
  Point(0, 1)
)

val allNeighborOffsets = listOf(
  Point(-1, -1),
  Point(-1, 0),
  Point(-1, 1),
  Point(0, -1),
  Point(0, 1),
  Point(1, -1),
  Point(1, 0),
  Point(1, 1)
)

data class Line(val s: Point, val e: Point) {
  private fun vertical() = s.x == e.x
  private fun horizontal() = s.y == e.y

  fun intersects(p: Point) =
    when {
      vertical() -> p.x == s.x && p.y >= s.y && p.y <= e.y
      horizontal() -> p.y == s.y && p.x >= s.x && p.x <= e.x
      else -> throw Exception("Strange Line!")
    }

  fun walkTheLine(): Sequence<Point> =
    sequence() {
      val xrange = (e.x - s.x)
      val yrange = (e.y - s.y)
      val range = Integer.max(abs(xrange), abs(yrange))
      val dx = xrange / range
      val dy = yrange / range

      (0..range).map { step ->
        yield(Point(s.x + step * dx, s.y + step * dy))
      }
    }

  companion object {
    fun create( p: Point, q: Point) =
      // vertical
      if (p.x == q.x) {
        if (p.y < q.y) {
          Line(p,q)
        } else {
          Line(q,p)
        }
      }
      // horizontal
      else {
        if (p.x < q.x) {
          Line(p,q)
        } else {
          Line(q,p)
        }
      }


    fun mergeHorizontalSegments(segments: List<Line>) : List<Line> {
      val sortedSegments = segments.sortedBy { it.s.x }

      val mergedSegments = sortedSegments.fold( Pair(mutableListOf<Line>(), sortedSegments[0]) ) {
          (mergedSegments, current), next ->
        if (next.s.x <= current.e.x) {
          val end = Point(kotlin.math.max(current.e.x, next.e.x), current.e.y)
          Pair(mergedSegments, Line(current.s, end))
        }
        else {
          mergedSegments.add(current)
          Pair(mergedSegments, next)
        }
      }
      .let {
          (mergedSegments, last) ->
            mergedSegments.add(last)
            mergedSegments
      }

      return mergedSegments
    }

  }

}

data class Bounds(val upperLeft: Point, val lowerRight: Point) {
  fun contains(p: Point) =
    p.x >= upperLeft.x && p.x <= lowerRight.x &&
      p.y >= upperLeft.y && p.y <= lowerRight.y

  fun height() = lowerRight.y - upperLeft.y
  fun width() = lowerRight.x - upperLeft.x

  companion object {
    fun create(points: List<Point>) : Bounds {
      val maxX = points.maxOf { (x, _) -> x }
      val maxY = points.maxOf { (_, y) -> y }
      val minX = points.minOf { (x, _) -> x }
      val minY = points.minOf { (_, y) -> y }
      return Bounds( Point(minX, minY), Point(maxX, maxY))
    }
  }
}

fun printGrid(grid: List<List<Int>>) {
  grid.forEach {
    it.forEach {
      print("$it")
    }
    println()
  }
}

fun printCharGrid(grid: List<List<Char>>) {
  grid.forEach {
    it.forEach {
      print("$it")
    }
    println()
  }
}


fun printPoints(points: List<Point>, showNumbers: Boolean = false) {
  val bounds = Bounds.create(points)

  var numbers = CharArray(50)
  (0..9).forEach { numbers[it] = "$it"[0] }
  (10 .. 35).forEach { numbers[it] = 'a' + (it-10) }
  (36 .. 49).forEach { numbers[it] = 'A' + (it-36) }

  val grid = MutableList(bounds.height() + 2) { MutableList(bounds.width() + 2) { '.' } }
  points.forEachIndexed() { i, (x, y) ->
    val c = if (showNumbers) numbers[i % 50] else '#'
    grid[y-bounds.upperLeft.y][x-bounds.upperLeft.x] = c
  }

  printCharGrid(grid)
}
