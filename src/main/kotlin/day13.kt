package day13

val inputFile = "/day13.txt"

val samples = """
[1,1,3,1,1]
[1,1,5,1,1]

[[1],[2,3,4]]
[[1],4]

[9]
[[8,7,6]]

[[4,4],4,4]
[[4,4],4,4,4]

[7,7,7,7]
[7,7,7]

[]
[3]

[[[]]]
[[]]

[1,[2,[3,[4,[5,6,7]]]],8,9]
[1,[2,[3,[4,[5,6,0]]]],8,9]
    """.trimIndent()

fun parseList(str: String) : Pair<List<Any>, Int> {

  var i = 0
  var numStr = ""
  var list = mutableListOf<Any>()

  while (i < str.length) {
    if (str[i] == '[') {
      val (l, endIndex) = parseList( str.substring(i+1))
      list.add(l)
      i += endIndex
    }
    else if (str[i].isDigit()) {
      numStr += str[i]
    }
    else if (str[i] == ',') {
      if (numStr.isNotEmpty())
        list.add(numStr.toInt())
      numStr = ""
    }
    else if (str[i] == ']') {
      if (numStr.isNotEmpty())
        list.add(numStr.toInt())
      return Pair(list, i+1)
    }

    i++
  }

  if (numStr.isNotEmpty())
    list.add(numStr.toInt())
  return Pair(list, str.length)
}

fun readData(str: String,): List<Pair< List<Any>, List<Any> > > {
  return str
    .split("\n\n")
    .map {
      it.lines()
        .map{
          parseList(it.substring(1,it.length-1)).first
        }
        .let {
          (a,b) -> Pair(a,b)
        }
    }
}

fun correctOrder(left: Any?, right: Any?) : Int {

  return when {
    left is Int && right is Int -> left.compareTo(right)
    left is Int && right is List<*> -> correctOrderLists(listOf(left), right)
    left is List<*> && right is Int -> correctOrderLists(left, listOf(right))
    left is List<*> && right is List<*> -> correctOrderLists(left, right)
    else -> { throw Exception("Unexpected case")}
  }

}

fun correctOrderLists(left: List<*>, right: List<*>) : Int {
  val comparison = left.zip(right) {
    a, b -> correctOrder(a,b)
  }

  var i = 0
  while (i < comparison.size) {
    if (comparison[i] > 0)
      return 1
    else if (comparison[i] < 0)
      return -1
    i++
  }

  return (left.size - right.size)
}


fun solution1(pairs: List<Pair< List<Any>, List<Any> > >) : Int {
  val results = pairs
    .map {
        (left, right) -> correctOrderLists(left, right)
    }

  return results
    .mapIndexed { i, b ->
      if (b<0) i+1 else 0
    }
    .sum()
}

fun readData2(str: String) : List<List<Any>> {
  return (str + "\n[[2]]\n[[6]]")
    .lines()
    .filterNot { it.isBlank() }
    .map {
          parseList(it.substring(1,it.length-1)).first
    }
}

fun solution2(lists: List<List<Any>>) : Int {
  val newLists = lists.sortedWith {
    a, b -> correctOrderLists(a,b)
  }

  val i = newLists.indexOf(listOf(listOf(2))) + 1
  val j = newLists.indexOf(listOf(listOf(6))) + 1
  return i*j
}

fun main() {
  val lists = readData(samples)
  println(lists)
  println( solution1(lists) )

  val all = readData2(samples)
  println( solution2(all) )
}


