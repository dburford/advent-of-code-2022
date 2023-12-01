import day10.*
import aoc.util.loadFromResource

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class Day10KtTest {

    @Test
    fun `should handle part 1 sample test data`() {
        assertEquals(13140, solution1(readData(samples2)))
    }

    @Test
    fun `should handle part 1 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(14320, solution1(testData))
    }

    @Test
    fun `should handle part 2 sample test data`() {
        assertEquals(0, solution2(readData(samples)))
    }

    @Test
    fun `should handle part 2 sample2 test data`() {
        assertEquals(0, solution2(readData(samples2)))
    }

    @Test
    // PCPBKAPJ
    fun `should handle part 2 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(0, solution2(testData))
    }
}
