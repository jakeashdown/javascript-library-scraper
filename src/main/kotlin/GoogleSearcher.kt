import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.net.URLEncoder

class GoogleSearcher {
    val RESULT_COUNT = 30

    fun getGoogleSearchResults(searchTerm: String): Document {
        val encodedSearchTerm = URLEncoder.encode(searchTerm)
        return Jsoup.connect("http://www.google.com/search?q=$encodedSearchTerm&num=$RESULT_COUNT").get()
    }
}