import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

import day1.*
import aoc.util.loadFromResource

internal class Day1KtTest {

    @Test
    fun `should handle part 1 sample test data`() {
        assertEquals(24000, solution1(readData(samples)))
    }

    @Test
    fun `should handle part 1 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(73211, solution1(testData))
    }

    @Test
    fun `should handle part 2 sample test data`() {
        assertEquals(45000, solution2(readData(samples)))
    }

    @Test
    fun `should handle part 2 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(213958, solution2(testData))
    }
}
