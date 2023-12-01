import day5.*
import aoc.util.loadFromResource

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class Day5KtTest {

    @Test
    fun `should handle part 1 sample test data`() {
        assertEquals("CMZ", solution1(readData(samples)))
    }

    @Test
    fun `should handle part 1 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals("JDTMRWCQJ", solution1(testData))
    }

    @Test
    fun `should handle part 2 sample test data`() {
        assertEquals("MCD", solution2(readData(samples)))
    }

    @Test
    fun `should handle part 2 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals("VHJDDCWRD", solution2(testData))
    }
}
