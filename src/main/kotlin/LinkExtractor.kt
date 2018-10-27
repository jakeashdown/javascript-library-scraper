import org.jsoup.nodes.Document

class LinkExtractor {

    fun extractLinksFromPage(page: Document) : List<String> {
        val links: MutableList<String> = mutableListOf()

        // The main result links appear as green text, inside <cite> elements
        for (element in page.select("cite")) {
            links.add(element.text())
        }

        return links
    }
}