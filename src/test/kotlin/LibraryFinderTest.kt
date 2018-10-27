import org.junit.Test

// TODO: make these tests not reliant on external webpages
class LibraryFinderTest {
    val libraryFinder = LibraryFinder()

    @Test
    fun testSingleUrl() {
        val libraries = libraryFinder.findJavascriptLibrariesInPages(listOf("http://www.jquery.com"))
        assert(libraries.contains("jquery-1.11.3"))
    }

    @Test
    fun testTwoUrls() {
        val libraries = libraryFinder.findJavascriptLibrariesInPages(listOf("http://www.jquery.com", "https://getbootstrap.com"))
        assert(libraries.contains("jquery-1.11.3"))
        assert(libraries.contains("analytics"))
        assert(libraries.contains("carbon"))
    }

    @Test
    fun testRemoveMinifiedLibrary() {
        val libraries = libraryFinder.findJavascriptLibrariesInPages(listOf("http://www.jquery.com"))
        // The jquery website uses the minified library 'modernizr.custom.2.8.3.min.js'
        assert(libraries.contains("modernizr.custom.2.8.3"))
        assert(!libraries.contains("modernizr.custom.2.8.3.min"))
    }
}