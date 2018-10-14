import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import org.jsoup.helper.StringUtil

class LibraryFinder {
    fun findJavascriptLibrariesInPages(urls: List<String>) : List<String>{
        var libraries = mutableListOf<String>()

        // TODO: Find a way to find libraries which are not loaded via <script src="url">
        for (url in urls) {
            if (StringUtil.isBlank(url)) {
                continue
            }

            try {
                val document = Jsoup.connect(url).get()
                val scriptTags = document.select("script")
                for (script in scriptTags) {
                    libraries.add(script.attr("src"))
                }
            } catch (exception: IllegalArgumentException) {
                println("Bad URL, skipping...")
                continue
            } catch (exception: HttpStatusException) {
                println("Bad response, skipping...")
                continue
            }
        }

        /*
        * TODO:
        *
        * find a way to condense many different links to libraries into a single library name, for example
        * 0 = "//jquery.com/jquery-wp-content/themes/jquery/js/modernizr.custom.2.8.3.min.js"
        * 1 = "https://code.jquery.com/jquery-1.11.3.js"
        * 2 = "//jquery.com/jquery-wp-content/themes/jquery/js/plugins.js"
        * 3 = "//jquery.com/jquery-wp-content/themes/jquery/js/main.js"
        * all link to the same jquery library
        *
        * one possibility would be to make a list of common substrings, such as 'com' and 'themes'
        * and consider two sources the same if they share a string which isn't a common substring
        *
        * another possibility would be to download the libraries themselves and check the metadata,
        * like author, CDN etc
        * */

        return libraries
    }

}