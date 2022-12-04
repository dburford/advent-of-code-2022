import day4.*

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class Day4KtTest {

    @Test
    fun `should handle part 1 sample test data`() {
        assertEquals(2, solution1(readData(samples)))
    }

    @Test
    fun `should handle part 1 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(498, solution1(testData))
    }

    @Test
    fun `should handle part 2 sample test data`() {
        assertEquals(4, solution2(readData(samples)))
    }

    @Test
    fun `should handle part 2 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(2752, solution2(testData))
    }
}
