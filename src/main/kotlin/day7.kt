package day7

val inputFile = "/day7.txt"

val samples = """
  ${'$'} cd /
  ${'$'} ls
  dir a
  14848514 b.txt
  8504156 c.dat
  dir d
  ${'$'} cd a
  ${'$'} ls
  dir e
  29116 f
  2557 g
  62596 h.lst
  ${'$'} cd e
  ${'$'} ls
  584 i
  ${'$'} cd ..
  ${'$'} cd ..
  ${'$'} cd d
  ${'$'} ls
  4060174 j
  8033020 d.log
  5626152 d.ext
  7214296 k
    """.trimIndent()

data class File(val name: String, val size: Int)

data class Directory(
  val subdirs: MutableMap<String, Directory>? = mutableMapOf <String, Directory>(),
  val files: MutableList<File>? = mutableListOf<File>(),
  val parent: Directory? = null,
  var size: Int = 0
)
{
  override fun toString() : String {
    return "${subdirs.toString()}, ${files.toString()}"
  }
}

fun readData(str: String) : Directory {
  // println(str)
  val lines = str.lines()

  var current : Directory? = Directory()
  val root = current

  var i=0
  while (i < lines.size) {
    val line = lines[i]

    //handle command
    if ( line.startsWith("$") ) {
      val command = line.substring(2).trim()

      if (command.startsWith("cd")) {
        val (_, target) = command.split(" ")
        current = when (target) {
          "/" -> root
          ".." -> current?.parent
          else -> current?.subdirs!![target]
        }
      }
      else if (command == "ls") {
        // add files and directories to current
        var next = i
        while (next < lines.size-1 && !lines[next + 1].startsWith("$")) {
          next++
          // if starts with dir, add dir
          if (lines[next].startsWith("dir")) {
            val (_, name) = lines[next].split(" ")
            current?.subdirs!![name]= Directory( parent = current)
          }
          // else assume file
          else {
            val (size, name) = lines[next].split(" ")
            current?.files?.add(File(name, size.toInt()))
          }
        }
        i = next
      }
    }
    i++
  }
  root?: throw Exception("Bad Tree!")
  return root
}

fun sizeDir(dir: Directory, list: MutableList<Directory>): Int {
  val sizeFiles = dir.files?.sumOf { it.size } ?: 0
  val sizeDirs = dir.subdirs?.values?.sumOf{ sizeDir(it, list) } ?: 0
  val total = sizeFiles + sizeDirs
  dir.size = total
  list.add(dir)
  return total
}

fun solution1(tree : Directory) : Int {
  // walk tree DFS, summing directory sizes recursively
  // if dir <= 100,000, add to list
  val list: MutableList<Directory> = mutableListOf<Directory>()
  sizeDir(tree, list)
  return list.filter{ it.size <= 100000 }.sumOf { it.size }
}

fun solution2(tree : Directory) : Int {
  val list: MutableList<Directory> = mutableListOf<Directory>()
  sizeDir(tree, list)
  val rootSize = tree.size

  val avail = 70000000 - rootSize
  val needed = 30000000 - avail
  // find smallest dir >= needed
  return list.map { it.size }.sorted().first { it >= needed }
}

fun main() {
    val tree = readData(samples)
    println( solution1(tree) )
    println( solution2(tree) )
}
