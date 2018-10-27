import org.jsoup.Jsoup
import org.junit.Test

class LinkExtractorTest {
    val linkExtractor: LinkExtractor = LinkExtractor()

    @Test
    fun testSingleLink() {
        val page = "<html><body><cite>www.wikipedia.org<cite></body></html>"
        val links = linkExtractor.extractLinksFromPage(Jsoup.parse(page))
        assert(links.contains("www.wikipedia.org"))
    }

    @Test
    fun testMultipleLinks() {
        val page = """<html><body><cite>www.facebook.com<cite></body><cite>www.twitter.com<cite></body></body></html>"""
        val links = linkExtractor.extractLinksFromPage(Jsoup.parse(page))
        assert(links.contains("www.facebook.com"))
        assert(links.contains("www.twitter.com"))
    }
}