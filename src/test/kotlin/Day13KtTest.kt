import day13.*
import aoc.util.loadFromResource

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class Day13KtTest {

    @Test
    fun `should handle part 1 sample test data`() {
        assertEquals(13, solution1(readData(samples)))
    }

    @Test
    fun `should handle part 1 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(5825, solution1(testData))
    }

    @Test
    fun `should handle part 2 sample test data`() {
        assertEquals(140, solution2(readData2(samples)))
    }

    @Test
    fun `should handle part 2 full test data`() {
        val testData = readData2(loadFromResource(inputFile))
        assertEquals(24477, solution2(testData))
    }

}
