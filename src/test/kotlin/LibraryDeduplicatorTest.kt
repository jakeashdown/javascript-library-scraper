import org.junit.Test

class LibraryDeduplicatorTest {
    val deduplicator = LibraryDeduplicator()

    @Test
    fun testSingleLibrary() {
        val library = "jquery"
        val uniqueLibraries = deduplicator.deduplicateLibraries(listOf(library))
        assert(uniqueLibraries.size == 1)
        assert(uniqueLibraries.contains(library))
    }

    @Test
    fun testTwoUniqueLibraries() {
        val library1 = "jquery"
        val library2 = "pinit"
        val uniqueLibraries = deduplicator.deduplicateLibraries(listOf(library1, library2))
        assert(uniqueLibraries.size == 2)
        assert(uniqueLibraries.contains(library1))
        assert(uniqueLibraries.contains(library2))
    }

    @Test
    fun testTwoSimilarJqueryLibraries() {
        val jquery1 = "jquery-1.11.3"
        val jquery2 = "jquery-1.11.2"
        val uniqueLibraries = deduplicator.deduplicateLibraries(listOf(jquery1, jquery2))
        assert(uniqueLibraries.size == 1)
        assert(uniqueLibraries.contains(jquery1) || uniqueLibraries.contains(jquery2))
    }
}