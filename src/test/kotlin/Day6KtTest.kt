import day6.*
import aoc.util.loadFromResource

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class Day6KtTest {

    @Test
    fun `should handle part 1 sample test data`() {
        assertEquals(7, solution1(readData(samples)))
    }

    @Test
    fun `should handle part 1 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(1034, solution1(testData))
    }

    @Test
    fun `should handle part 2 sample test data`() {
        assertEquals(19, solution2(readData(samples)))
    }

    @Test
    fun `should handle part 2 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(2472, solution2(testData))
    }
}
