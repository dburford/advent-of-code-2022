import day7.*
import aoc.util.loadFromResource

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class Day7KtTest {

    @Test
    fun `should handle part 1 sample test data`() {
        assertEquals(95437, solution1(readData(samples)))
    }

    @Test
    fun `should handle part 1 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(1611443, solution1(testData))
    }

    @Test
    fun `should handle part 2 sample test data`() {
        assertEquals(24933642, solution2(readData(samples)))
    }

    @Test
    fun `should handle part 2 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(2086088, solution2(testData))
    }
}
