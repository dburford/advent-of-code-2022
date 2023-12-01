import day14.*
import aoc.util.loadFromResource

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class Day14KtTest {

    @Test
    fun `should handle part 1 sample test data`() {
        assertEquals(24, solution1(readData(samples)))
    }

    @Test
    fun `should handle part 1 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(1406, solution1(testData))
    }

    @Test
    fun `should handle part 2 sample test data`() {
        assertEquals(93, solution2(readData(samples)))
    }

    @Test
    fun `should handle part 2 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(20870, solution2(testData))
    }

}
