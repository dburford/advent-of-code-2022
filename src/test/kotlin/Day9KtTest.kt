import day9.*
import aoc.util.loadFromResource

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class Day9KtTest {

    @Test
    fun `should handle part 1 sample test data`() {
        assertEquals(13, solution1(readData(samples)))
    }

    @Test
    fun `should handle part 1 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(5907, solution1(testData))
    }

    @Test
    fun `should handle part 2 sample test data`() {
        assertEquals(1, solution2(readData(samples)))
    }

    @Test
    fun `should handle part 2 sample2 test data`() {
        assertEquals(36, solution2(readData(samples2)))
    }

    @Test
    fun `should handle part 2 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(2303, solution2(testData))
    }
}
