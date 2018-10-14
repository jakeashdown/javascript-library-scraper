import org.jsoup.nodes.Document

class LinkExtractor {
    fun extractLinksFromPage(page: Document) : List<String> {
        val links: MutableList<String> = mutableListOf()
        // TODO: make specific to result links, eg. not links to google links
        for (element in page.select("a")) {
            links.add(element.attr("href"))
        }

        return links
    }
}