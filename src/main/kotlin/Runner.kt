import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import kotlin.math.min

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

    // TODO: implement this with concurrency for each thread group, using Java completable futures or kotlin concurrency
    val libraryFinder = LibraryFinder()
    val libraries = mutableListOf<String>()
    for (linkGroup in linkGroups.values) {
        libraries.addAll(libraryFinder.findJavascriptLibrariesInPages(linkGroup))
    }

    // TODO: find way to sort by most common results
    println("Outputting top Javascript libraries...")
    for (i in 1..min(5, libraries.size)) {
        println(libraries.get(i - 1))
    }
}