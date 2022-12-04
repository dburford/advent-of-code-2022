import day3.*

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class Day3KtTest {

    @Test
    fun `should handle part 1 sample test data`() {
        assertEquals(157, solution1(readData(samples)))
    }

    @Test
    fun `should handle part 1 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(7821, solution1(testData))
    }

    @Test
    fun `Bitmap soln should handle part 2 sample test data`() {
        assertEquals(70, solution2Bitmaps(readData(samples)))
    }

    @Test
    fun `HashSets soln should handle part 2 sample test data`() {
        assertEquals(70, solution2HashSets(readData(samples)))
    }

    @Test
    fun `should handle part 2 sample test data`() {
        assertEquals(70, solution2(readData(samples)))
    }

    @Test
    fun `should handle part 2 full test data`() {
        val testData = readData(loadFromResource(inputFile))
        assertEquals(2752, solution2(testData))
    }
}
