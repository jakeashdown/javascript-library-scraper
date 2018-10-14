import org.jsoup.Jsoup
import org.junit.Test

class LinkExtractorTest {
    val linkExtractor: LinkExtractor = LinkExtractor()

    @Test
    fun testSingleLink() {
        val page = "<html><body><a href='www.wikipedia.org'>wikipedia</a></body></html>"
        val links = linkExtractor.extractLinksFromPage(Jsoup.parse(page))
        assert(links.contains("www.wikipedia.org"))
    }

    @Test
    fun testMultipleLinks() {
        val page = """<html><body><a href='www.wikipedia.org'>wikipedia</a><a href="www.facebook.com">facebook</a></body></html>"""
        val links = linkExtractor.extractLinksFromPage(Jsoup.parse(page))
        assert(links.contains("www.wikipedia.org"))
    }
}