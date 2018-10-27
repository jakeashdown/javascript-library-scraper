import org.junit.Test

class GoogleSearcherTest {
    val searcher: GoogleSearcher = GoogleSearcher()

    @Test
    fun testPageLoads() {
        val document = searcher.getGoogleSearchResults("wikipedia")
        assert(document.title().contains("Google Search"))
    }

    @Test
    fun testContainsLink() {
        val document = searcher.getGoogleSearchResults("wikipedia")
        val links = document.select("a")
        var containsWikipediaLink = false
        for (element in links) {
            if (element.attr("href").contains("www.wikipedia.org")) containsWikipediaLink = true
        }
        assert(containsWikipediaLink)
    }

}