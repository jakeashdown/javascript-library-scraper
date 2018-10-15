import java.lang.Integer.min

val THREAD_COUNT = 2

fun main(args: Array<String>) {
    println("Starting javascript library scraper...")

    if (args.size != 1) {
        println("Single program argument required - search term")
        System.exit(1)
    }

    val searchTerm: String = args[0]
    val googleSearcher = GoogleSearcher()

    println("Getting google results for search term $searchTerm...")
    val searchResults = googleSearcher.getGoogleSearchResults(searchTerm)

    println("Getting links from search results...")
    val linkExtractor = LinkExtractor()
    val links = linkExtractor.extractLinksFromPage(searchResults)
    println("Found ${links.size} links")

    println("Group links for multithreading...")
    // Divide the list of links into THREAD_COUNT groups
    val linkGroups = mutableMapOf<Int, MutableList<String>>()
    for (index in 1..links.size) {
        val group = index % THREAD_COUNT
        if (linkGroups.containsKey(group)) {
            linkGroups.get(group)!!.add(links.get(index - 1))
        } else {
            linkGroups.put(group, mutableListOf(links.get(index - 1)))
        }
    }

    /*
    * TODO:
    * Process each group of links on a seperate thread.
    * One way to do this is to use Java's CompletableFuture API, creating one future
    * for each group of links and blocking execution of this thread until all futures are complete.
    *
    * Alternatively, find out how Kotlin does multithreading.
    * */
    val libraryFinder = LibraryFinder()
    val libraries = mutableListOf<String>()
    for (linkGroup in linkGroups.values) {
        libraries.addAll(libraryFinder.findJavascriptLibrariesInPages(linkGroup))
    }

    /*
    * TODO:
    * Remove duplicate libraries from results.
    * One way to do this would be to use a 'fuzzy' string-matching algorithm such as Jaro-Winkler,
    * so that we consider two libraries matching if the string similarity is over a certain threshold.
    *
    * For example, the following
    * 0 = "//jquery.com/jquery-wp-content/themes/jquery/js/modernizr.custom.2.8.3.min.js"
    * 1 = "https://code.jquery.com/jquery-1.11.3.js"
    * 2 = "//jquery.com/jquery-wp-content/themes/jquery/js/plugins.js"
    * 3 = "//jquery.com/jquery-wp-content/themes/jquery/js/main.js"
    * all link to the same jquery library
    *
    * Another way would be to find the library online (e.g. in a CDN) and check the author
    * */
    println("Outputting top Javascript libraries...")
    for (i in 1..min(5, libraries.size)) {
        println(libraries.get(i - 1))
    }
}