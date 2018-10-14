import org.junit.Test

class GoogleSearcherTest {
    val searcher: GoogleSearcher = GoogleSearcher()

    @Test
    fun testDocumentTitle() {
        val document = searcher.getGoogleSearchResults("wikipedia")
        assert(document.title().contains("Google"))
    }

    @Test
    fun testContainsLink() {
        val document = searcher.getGoogleSearchResults("wikipedia")
        val links = document.select("a")
        var containsWikipediaLink = false
        for (element in links) {
            if (element.attr("href").equals("www.wikipedia.org")) containsWikipediaLink = true
        }
    }
}