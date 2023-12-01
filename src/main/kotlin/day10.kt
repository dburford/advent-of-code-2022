package day10

import aoc.util.geom.*

val inputFile = "/day10.txt"

val samples = """
noop
addx 3
addx -5
    """.trimIndent()

val samples2 = """
addx 15
addx -11
addx 6
addx -3
addx 5
addx -1
addx -8
addx 13
addx 4
noop
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx -35
addx 1
addx 24
addx -19
addx 1
addx 16
addx -11
noop
noop
addx 21
addx -15
noop
noop
addx -3
addx 9
addx 1
addx -3
addx 8
addx 1
addx 5
noop
noop
noop
noop
noop
addx -36
noop
addx 1
addx 7
noop
noop
noop
addx 2
addx 6
noop
noop
noop
noop
noop
addx 1
noop
noop
addx 7
addx 1
noop
addx -13
addx 13
addx 7
noop
addx 1
addx -33
noop
noop
noop
addx 2
noop
noop
noop
addx 8
noop
addx -1
addx 2
addx 1
noop
addx 17
addx -9
addx 1
addx 1
addx -3
addx 11
noop
noop
addx 1
noop
addx 1
noop
noop
addx -13
addx -19
addx 1
addx 3
addx 26
addx -30
addx 12
addx -1
addx 3
addx 1
noop
noop
noop
addx -9
addx 18
addx 1
addx 2
noop
noop
addx 9
noop
noop
noop
addx -1
addx 2
addx -37
addx 1
addx 3
noop
addx 15
addx -21
addx 22
addx -6
addx 1
noop
addx 2
addx 1
noop
addx -10
noop
noop
addx 20
addx 1
addx 2
addx 2
addx -6
addx -11
noop
noop
noop
    """.trimIndent()

data class Register(val cycle: Int, val value:Int)

fun readData(str: String,) =
  str
    .lines()
    .asSequence()
    .map {
      it.split(" ")
    }


fun solution1(instructions: Sequence<List<String>>) : Int {

  val samplePoints: List<Int> = listOf(20, 60, 100, 140, 180, 220)
  val sampleMap = mutableMapOf<Int, Int>()

  instructions
    .fold(Register(1, 1)) { reg, instr ->

      val delta = when (instr[0]) {
        "addx" -> Register(2, instr[1].toInt())
        else -> Register(1, 0)
      }

      (0 until delta.cycle).forEach {
        if ( reg.cycle + it in samplePoints) {
          sampleMap[reg.cycle+it] = reg.value
        }
      }

      Register(reg.cycle + delta.cycle, reg.value + delta.value)
    }

  println(sampleMap)

  return sampleMap
    .map {
        (sample, value) -> sample*value
    }
    .sum()
}


fun solution2(instructions: Sequence<List<String>>) : Int {

  val grid = MutableList(6) { MutableList(40) { '.' } }

  instructions
    .fold(Register(1, 1)) { reg, instr ->

      val delta = when (instr[0]) {
        "addx" -> Register(2, instr[1].toInt())
        else -> Register(1, 0)
      }

      (0 until delta.cycle).forEach {
        val pixel = reg.cycle - 1 + it
        // if cycle-1 is in range of X-1 to X+1, draw
        if (pixel % 40 in reg.value - 1 .. reg.value + 1) {
          grid[pixel / 40][pixel % 40] = '#'
        }
      }

      Register(reg.cycle + delta.cycle, reg.value + delta.value)
    }

  printCharGrid(grid)

  return 0
}

fun main() {
  val instructions = readData(samples2)

  println( solution1(instructions) )
  println( solution2(instructions) )
}

