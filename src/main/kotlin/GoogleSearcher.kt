import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class GoogleSearcher {

    fun getGoogleSearchResults(searchTerm: String) : Document {
        // TODO: deal with search term containing spaces
        return Jsoup.connect("http://www.google.com/search?q=$searchTerm").get()
    }
}