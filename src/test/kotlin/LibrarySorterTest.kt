import org.junit.Test

class LibrarySorterTest {
    val sorter = LibrarySorter()

    @Test
    fun testPairOfLibraries() {
        val sortedLibraries = sorter.sortLibrariesByPopularity(mapOf("jquery" to 1, "analytics" to 2))
        assert(sortedLibraries.get(0).equals(Pair("analytics", 2)))
        assert(sortedLibraries.get(1).equals(Pair("jquery", 1)))
    }
}