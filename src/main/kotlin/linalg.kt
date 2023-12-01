package aoc.util.linalg

fun printMatrix(mat : Array<IntArray>) {
  mat.forEach {
    it.forEach {
      val s = "$it".padStart(2, '0')
      if (it >= 1000000)
        print("∞ \t")
      else
        print("$s\t") }
    println()
  }
}