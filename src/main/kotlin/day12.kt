package day12

import aoc.util.geom.*
import java.util.PriorityQueue

val inputFile = "/day12.txt"

val samples = """
    Sabqponm
    abcryxxl
    accszExk
    acctuvwj
    abdefghi
    """.trimIndent()

data class Hill(var start: Point, val end: Point, val topo: Array<IntArray>, val lowPoints: List<Point>)

fun readData(str: String,): Hill {

  var start = Point(0, 0)
  var end = Point(0, 0)
  var lowPoints = mutableListOf<Point>()

  val topo = str
    .lines()
    .map {
      it.toList()
    }
    .mapIndexed { r, l ->
      l.mapIndexed { c, ch ->
        when (ch) {
          'S' -> { start = Point(c, r); lowPoints.add(start); 0 }
          'E' -> { end = Point(c, r); 25 }
          'a' -> { lowPoints.add(Point(c,r)); 0}
          else -> (ch - 'a').toInt()
        }
      }
    }
    .map {
      it.toIntArray()
    }
    .toTypedArray()

  // printPoints( lowPoints )

  return Hill(start, end, topo, lowPoints)
}

data class Node(val p: Point, val d : Int)

fun returnPath(endPoint: Point, previousNode: Map<Point, Point>): List<Node> {
  val path = mutableListOf<Node>(Node(endPoint, 0))
  var current = previousNode[endPoint]
  var distanceToEnd = 1
  while (current != null) {
    path += Node(current, distanceToEnd)
    current = previousNode[current]
    distanceToEnd++
  }
  return path.reversed()
}

fun shortestPath(start: Point, end: Point, topo: Array<IntArray>) : List<Node> {
  var queue = PriorityQueue<Node> { a, b -> a.d - b.d }

  queue.add(Node(start, 0))
  val distances = mutableMapOf<Point, Int>()
  val pathTo = mutableMapOf<Point, Point>()
  distances[start] = 0

  while (queue.isNotEmpty()) {
    val current = queue.poll()

    if (current.p == end) {
      break;
    }

    neighborOffsets
      .mapNotNull { dP ->
        val neighbour = current.p + dP
        topo.getOrNull(neighbour.y)?.getOrNull(neighbour.x)
          ?.let {
            if (it <= topo[current.p.y][current.p.x] + 1) {
              val d = current.d + 1
              if (distances.getOrDefault(neighbour, Int.MAX_VALUE) > d) {
                distances[neighbour] = d
                pathTo[neighbour] = current.p
                queue.add(Node(neighbour, d))
              }
            }
          }
      }
  }

  val path = returnPath(end, pathTo)
  if (path.size > 1 ) printPoints(path.map{it.p}, true)
  return path
}

// data structure
data class PathNode( val node: Node, var next: PathNode? )

data class PathCache(var lookup: MutableMap<Point, PathNode>) {
  // path is list of Nodes (points and distance to end)
  fun addPath(path: List<Node>) {
    // lastNode will be connecting node to remainder of path
    //   and will have distance to end of base path in lookup table
    val lastPoint = path.last().p
    val linkingNode = lookup.get(lastPoint)
    val remainingDist = linkingNode
      ?.let {
        it.node.d
      } ?: 0
      // .getOrElse(lastPoint) { throw Exception("linking point not found in cache!")}
      // .node.d

    var prev : PathNode? = null
    // if we have a linkingNode, we process nodes up to second last, else we process all
    val limit = linkingNode?.let{ path.size - 2 } ?: path.size - 1
    for ( i in 0 .. limit ) { //node in path) {
      // construct new Node (updating distance) and PathNode
      val node = path[i]
      val newNode = PathNode(Node(node.p, node.d + remainingDist), null)
      prev?.let {
        it.next = newNode
      }
      lookup[node.p] = newNode
      prev = newNode
    }

    prev?.let {it.next = linkingNode }
  }

  fun getPath(p : Point) : List<Node> {
    val start = lookup.getOrElse(p) { throw Exception("linking point $p not found in cache!")}
    var list = mutableListOf<Node>()

    // iterate from start to end, adding elements to List
    var current: PathNode? = start
    while(current != null) {
      list.add(current.node)
      current = current.next
    }
    return list
  }

  companion object {
    fun create() =
      PathCache(mutableMapOf<Point, PathNode>())
  }

}

fun shortestPathCached(start: Point, end: Point, topo: Array<IntArray>, cache: PathCache) : List<Node> {
  var queue = PriorityQueue<Node> { a, b -> a.d - b.d }

  queue.add(Node(start, 0))
  val distances = mutableMapOf<Point, Int>()
  val pathTo = mutableMapOf<Point, Point>()
  distances[start] = 0

  while (queue.isNotEmpty()) {
    val current = queue.poll()

    if (current.p == end) {
      break;
    }

    if (current.p in cache.lookup) {
      // add pathTo to cache
      if (pathTo.isNotEmpty())
        cache.addPath(returnPath(current.p, pathTo))
      // return constructed path
      val path = cache.getPath(start)
      if (path.size > 1 ) printPoints(path.map{it.p}, true)
      return path
    }

    neighborOffsets
      .mapNotNull { dP ->
        val neighbour = current.p + dP
        topo.getOrNull(neighbour.y)?.getOrNull(neighbour.x)
          ?.let {
            if (it <= topo[current.p.y][current.p.x] + 1) {
              val d = current.d + 1
              if (distances.getOrDefault(neighbour, Int.MAX_VALUE) > d) {
                distances[neighbour] = d
                pathTo[neighbour] = current.p
                queue.add(Node(neighbour, d))
              }
            }
          }
      }
  }

  val path = returnPath(end, pathTo)
  cache.addPath(path)
  if (path.size > 1 ) printPoints(path.map{it.p}, true)
  println()
  return path
}

fun solution1(hill: Hill) : Int {
  return shortestPath(hill.start, hill.end, hill.topo).first().let{ it.d }
}

fun solution2(hill: Hill) : Int {
  return hill
    .lowPoints
    .map {
      shortestPath(it, hill.end, hill.topo)
        .first()
        .let{ it.d }
        .also { println(it) }
    }
    .filter{ it > 0 }
    .minOf{ it }
}

fun solution2Cached(hill : Hill) : Int {
  // cache = empty list
  // for all lowPoints:
  //  shortestPath = find shortest path (hill, cache)
  //  update cache with shortestPath

  val cache = PathCache.create()

  return hill
    .lowPoints
    .map {
      shortestPathCached(it, hill.end, hill.topo, cache)
        .first()
        .let{ it.d }
        .also { println(it) }

    }
    .filter{ it > 0 }
    .minOf{ it }
}

fun main() {
  val hill = readData(samples)

  println( solution1(hill) )
  println( solution2(hill) )
  // println( solution2Cached(hill) )
}

