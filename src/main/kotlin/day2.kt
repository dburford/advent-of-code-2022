package day2

val inputFile = "/day2.txt"

val samples = """
        A Y
        B X
        C Z
    """.trimIndent()

data class Game( val p: Int, val q: Int)

fun mapLetter(s: String) =
    when(s) {
        "A", "X" -> 1   // rock
        "B", "Y" -> 2   // paper
        "C", "Z" -> 3   // scissors
        else -> 0
    }

enum class Hand(val v: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    // I'll win against this
    fun winsAgainst() =
        when(this) {
            Hand.ROCK -> Hand.SCISSORS
            Hand.PAPER -> Hand.ROCK
            Hand.SCISSORS -> Hand.PAPER
        }
    // Hand.fromVal( ((me.val + 1) % 3) + 1 )  // value that will lose

    // I'll lose against this
    fun losesAgainst() =
        when(this) {
            Hand.ROCK -> Hand.PAPER
            Hand.PAPER -> Hand.SCISSORS
            Hand.SCISSORS -> Hand.ROCK
        }

    companion object {
        fun fromVal(v: Int) =
            when(v) {
                1 -> ROCK
                2 -> PAPER
                3 -> SCISSORS
                else -> ROCK
            }
    }
}

enum class Result( val v: Int, val score: Int) {
    LOSE(1, 0),
    DRAW(2, 3),
    WIN(3, 6);

    companion object {
        fun fromVal(v: Int) =
            when(v) {
                1 -> Result.LOSE
                2 -> Result.DRAW
                3 -> Result.WIN
                else -> Result.LOSE
            }
    }
}

fun readData(str: String): List<Game> {
    return str
        .lines()
        .map { it.split(" ") }
        .map {
            it.map(::mapLetter)
        }
        .map { (p, q) -> Game(p, q) }
}

// day 2 #1
fun solution1(items: List<Game>) =
    items
        .map { (p,q) ->
            Pair(
                Hand.fromVal(p),
                Hand.fromVal(q)
            )
        }
        .fold( 0) {
            score, (you, me) ->
                when (you) {
                    me                  -> Result.DRAW
                    me.winsAgainst()    -> Result.WIN
                    else                -> Result.LOSE
                }.score + me.v + score
        }

// day 2 #2
fun solution2(items: List<Game>) =
    items
        .map { (p,q) ->
            Pair<Hand, Result>(
                Hand.fromVal(p),
                Result.fromVal(q)
            )
        }
        .fold( 0) {
            score, (you, result) ->
             when(result) {
                    Result.LOSE -> you.winsAgainst()
                    Result.DRAW -> you
                    Result.WIN  -> you.losesAgainst()
             }.v + result.score + score
        }

fun main() {
    Hand.values().forEach {
        println("$it wins against ${it.winsAgainst()}" );
        println("$it loses against ${it.losesAgainst()}" );
    }

//    val filedata = File("src/main/resources/day2.txt").readText()
//    val input = readData(filedata)

    val input = readData(samples)
    println(solution1(input))
    println(solution2(input))
}