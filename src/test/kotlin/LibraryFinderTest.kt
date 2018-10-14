import org.junit.Test

class LibraryFinderTest {
    val libraryFinder = LibraryFinder()

    @Test
    fun testSingleUrl() {
        val libraries = libraryFinder.findJavascriptLibrariesInPages(listOf("http://www.jquery.com"))
        assert(libraries.contains("https://code.jquery.com/jquery-1.11.3.js"))
    }
}