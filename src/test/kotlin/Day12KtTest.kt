import day12.*
import aoc.util.loadFromResource

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class Day12KtTest {

    @Test
    fun `should handle part 1 sample test data`() {
        assertEquals(31, solution1(readData(samples)))
    }

    @Test
    fun `should handle part 1 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(472, solution1(testData))
    }

    @Test
    fun `should handle part 2 sample test data`() {
        assertEquals(29, solution2(readData(samples)))
    }

    @Test
    fun `should handle part 2 cached sample test data`() {
        assertEquals(29, solution2Cached(readData(samples)))
    }

    @Test
    fun `should handle part 2 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(465, solution2(testData))
    }

    @Test
    fun `should handle part 2 cached full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(465, solution2Cached(testData))
    }
}
