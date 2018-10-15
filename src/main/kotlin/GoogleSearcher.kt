import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class GoogleSearcher {

    fun getGoogleSearchResults(searchTerm: String) : Document {
        // TODO: find neater way to deal with encoding (also encode chars like '?')
        val encodedSearchTerm = searchTerm.replace(" ", "+")
        return Jsoup.connect("http://www.google.com/search?q=$encodedSearchTerm").get()
    }
}