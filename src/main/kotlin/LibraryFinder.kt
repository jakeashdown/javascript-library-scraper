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
                    val source = script.attr("src")
                    if (source.isNotEmpty()) {
                        libraries.add(source)
                    }
                }
            } catch (exception: IllegalArgumentException) {
                println("Bad URL, skipping...")
                continue
            } catch (exception: HttpStatusException) {
                println("Bad response, skipping...")
                continue
            }
        }

        return libraries
    }

}