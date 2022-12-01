import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

import day1.*

internal class Day1KtTest {

    @Test
    fun `should handle part 1 sample test data`() {
        assertEquals(7, solution1(samples))
    }

    @Test
    fun `should handle part 1 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(1400, solution1(testData))
    }

    @Test
    fun `should handle part 2 sample test data`() {
        assertEquals(5, solution2(samples))
    }

    @Test
    fun `should handle part 2 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(1429, solution2(testData))
    }
}
