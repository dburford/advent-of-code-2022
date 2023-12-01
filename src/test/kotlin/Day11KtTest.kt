import day11.*
import aoc.util.loadFromResource

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class Day11KtTest {

    @Test
    fun `should handle part 1 sample test data`() {
        assertEquals(10605, solution1(readData(samples)))
    }

    @Test
    fun `should handle part 1 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(58322, solution1(testData))
    }

    @Test
    fun `should handle part 2 sample test data`() {
        assertEquals(2713310158, solution2(readData(samples)))
    }

    @Test
    fun `should handle part 2 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(13937702909, solution2(testData))
    }
}
