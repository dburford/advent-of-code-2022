package day11

import aoc.util.geom.*

val inputFile = "/day11.txt"

val samples = """
Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3

Monkey 1:
  Starting items: 54, 65, 75, 74
  Operation: new = old + 6
  Test: divisible by 19
    If true: throw to monkey 2
    If false: throw to monkey 0

Monkey 2:
  Starting items: 79, 60, 97
  Operation: new = old * old
  Test: divisible by 13
    If true: throw to monkey 1
    If false: throw to monkey 3

Monkey 3:
  Starting items: 74
  Operation: new = old + 3
  Test: divisible by 17
    If true: throw to monkey 0
    If false: throw to monkey 1
    """.trimIndent()

data class Operation(val op: String, val operand: String)

data class Monkey(
  val items: MutableList<Long>,
  val op: Operation,
  val testDivisor: Long,
  val trueMonkey: Int,
  val falseMonkey: Int,
  var inspections: Long = 0L
) {

  fun processRound(divisor: Long, modder: Long) : Pair<List<Long>, List<Long>> {
    val levels = items.map {
      val operand = if (op.operand == "old") it else op.operand.toLong()
      when(op.op) {
        "+" -> (it + operand) % (modder*divisor)
        "*" -> (it * operand) % (modder*divisor)
        else -> throw java.lang.Exception("Huh?!")
      }

    }.map {
     (it / divisor) % (modder*divisor)
    }
    .fold( Pair(mutableListOf<Long>(), mutableListOf<Long>()) ) {
        (tm, fm), level ->
        if (level % testDivisor == 0L) tm.add(level) else fm.add(level)
        Pair(tm, fm)
    }

    inspections += items.size

    return levels
  }


  companion object {
    fun fromString(fields: List<String>) : Monkey {
      val items = fields[0].split(":")[1].split(",").map(String::trim).map(String::toLong).toMutableList()
      val op = fields[1].split(":")[1].split("= old")[1].trim().split(" ").let {
        (o, operand) -> Operation(o, operand)
      }
      val td = fields[2].split(":")[1].split("by")[1].trim().toLong()
      val tm = fields[3].split("monkey")[1].trim().toInt()
      val fm = fields[4].split("monkey")[1].trim().toInt()

      return Monkey(items, op, td, tm, fm)
    }
  }
}

fun readData(str: String,) =
  str
    .split("\n\n")
    .map {
      it.lines()
    }
    .map{
      it.subList(1, it.size)
    }
    .map {
      Monkey.fromString(it)
    }

fun solution1(monkeys: List<Monkey>) : Long {
  val modder = monkeys.map { it.testDivisor}.reduce { p, m -> p*m }

  repeat(20) {
    for (m in monkeys) {
      val output = m.processRound(3, modder)
      monkeys[m.trueMonkey].items.addAll(output.first)
      monkeys[m.falseMonkey].items.addAll(output.second)
      m.items.clear()
    }
  }

  return monkeys.map{ it.inspections}.sortedDescending().take(2).let{
    it[0] * it[1]
  }
}

fun solution2(monkeys: List<Monkey>) : Long {
  val modder = monkeys.map { it.testDivisor}.reduce { p, m -> p*m }

  repeat(10000) {
    for (m in monkeys) {
      val output = m.processRound(1L, modder)
      monkeys[m.trueMonkey].items.addAll(output.first)
      monkeys[m.falseMonkey].items.addAll(output.second)
      m.items.clear()
    }
  }

  return monkeys.map{ it.inspections}.sortedDescending().take(2).let{
    it[0] * it[1]
  }
}

fun main() {
  val monkeys = readData(samples)

  // println( solution1(monkeys) )
  println( solution2(monkeys) )
}

