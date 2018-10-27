import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import org.jsoup.helper.StringUtil
import java.net.UnknownHostException
import java.util.regex.Pattern
import javax.net.ssl.SSLHandshakeException

class LibraryFinder {

    // We will take the library name as the substring between the final '/' and the '.js';
    // since the first .* is greedy, we shouldn't get '/' inside our captured group
    val PATTERN_LIBRARY_NAME = Pattern.compile(".*/(.*)\\.js.*")
    val PATTERN_LIBRARY_NAME_MIN = Pattern.compile(".*/(.*)\\.min\\.js.*")

    fun findJavascriptLibrariesInPages(urls: List<String>): List<String> {
        val libraries = mutableListOf<String>()

        for (url in urls) {
            if (StringUtil.isBlank(url)) continue

            try {
                val document = Jsoup.connect(url).get()
                println("Searching for JS libraries in $url...")

                val scriptTags = document.select("script")
                for (script in scriptTags) {
                    val fullSource = script.attr("src")
                    if (fullSource.isNotEmpty()) {
                        // 'src' may contain multiple URLs seperated by commas
                        val sources = fullSource.split(",")

                        // Extract the library name from each source
                        for (source in sources) {
                            val minMatcher = PATTERN_LIBRARY_NAME_MIN.matcher(source)
                            var library: String? = null

                            // Try to remove the '.min'
                            if (minMatcher.find()) library = minMatcher.group(1)
                            else {
                                val nonMinMatcher = PATTERN_LIBRARY_NAME.matcher(source)
                                if (nonMinMatcher.find()) library = nonMinMatcher.group(1)
                            }

                            if (library != null) {
                                println("Found library $library")
                                libraries.add(library)
                            }
                        }
                    }
                }
            } catch (exception: IllegalArgumentException) {
                println("Bad URL, skipping...")
                continue
            } catch (exception: HttpStatusException) {
                println("Bad response, skipping...")
                continue
            } catch (exception: UnknownHostException) {
                println("Unknown host, skipping...")
                continue
            } catch (exception: SSLHandshakeException) {
                println("Handshake exception, skipping...")
                continue
            }
        }

        return libraries
    }
}