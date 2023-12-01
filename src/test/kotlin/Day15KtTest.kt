import day15.*
import aoc.util.loadFromResource

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class Day15KtTest {

    @Test
    fun `should handle part 1 sample test data`() {
        assertEquals(26, solution1(readData(samples), 10))
    }

    @Test
    fun `should handle part 1 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(4793062, solution1(testData, 2000000))
    }

    @Test
    fun `should handle part 2 sample test data`() {
        assertEquals(56000011, solution2(readData(samples),20))
    }

    @Test
    fun `should handle part 2 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(10826395253551, solution2(testData, 4000000))
    }

}
