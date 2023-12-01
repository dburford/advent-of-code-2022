import day16.*
import aoc.util.loadFromResource

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class Day16KtTest {

    @Test
    fun `should handle part 1 sample test data`() {
        assertEquals(26, solution1(readData(samples)))
    }

    @Test
    fun `should handle part 1 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(4793062, solution1(testData))
    }

    @Test
    fun `should handle part 2 sample test data`() {
        assertEquals(56000011, solution2(readData(samples)))
    }

    @Test
    fun `should handle part 2 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(10826395253551, solution2(testData))
    }

}
