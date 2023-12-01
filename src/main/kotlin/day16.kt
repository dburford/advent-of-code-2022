package day16

import aoc.util.graphs.allPairs
import aoc.util.linalg.printMatrix

val inputFile = "/day16.txt"

val samples = """
    Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
    Valve BB has flow rate=13; tunnels lead to valves CC, AA
    Valve CC has flow rate=2; tunnels lead to valves DD, BB
    Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
    Valve EE has flow rate=3; tunnels lead to valves FF, DD
    Valve FF has flow rate=0; tunnels lead to valves EE, GG
    Valve GG has flow rate=0; tunnels lead to valves FF, HH
    Valve HH has flow rate=22; tunnel leads to valve GG
    Valve II has flow rate=0; tunnels lead to valves AA, JJ
    Valve JJ has flow rate=21; tunnel leads to valve II
    """.trimIndent()

data class Valve(val name: String, val rate: Int, val index: Int, val neighbours: List<String>)

fun readData(str: String): Map<String, Valve> {

  // build adjacency list and value lookup
  val valves = str
    .lines().mapIndexed { i, it ->

      val match = Regex(
        "Valve (\\w*) has flow rate=(\\d*); tunnel[s]? lead[s]? to valve[s]? (.*)"
      ).find(it)!!
      match.destructured
        .let { (valve, flowRate, neighbours) ->
          valve to Valve(valve, flowRate.toInt(), i, neighbours.split(",").map(String::trim))
        }
    }.toMap()
  return valves
}


fun buildAdjacencyMatrix(valves: Map<String, Valve>) : Array<IntArray> {

  val adjMatrix = Array<IntArray>(valves.size) { IntArray(valves.size) { 1000000 }}
  valves.forEach { (k, u) ->
    u.neighbours.forEach { wName ->
      valves[wName]?.let { w ->
        adjMatrix[u.index][w.index] = 1
      }
    }
    adjMatrix[u.index][u.index] = 0
  }
  return adjMatrix
}

fun solution1(valves: Map<String, Valve>) : Int {

  val adjMatrix = buildAdjacencyMatrix(valves)
  printMatrix(adjMatrix)

  val allPairs = allPairs(adjMatrix)
  printMatrix(allPairs)

  return 42
}

fun solution2(valves: Map<String, Valve>): Long {
  return 42
}

fun main() {
  val valves = readData(samples)
  println(valves)
  println( solution1(valves) )
  // println( solution2(sensors) )
}

/*

References:

 - https://en.wikipedia.org/wiki/Floyd%E2%80%93Warshall_algorithm

 */


