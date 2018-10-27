import java.lang.Integer.min
import java.util.concurrent.CompletableFuture
import java.util.stream.Collectors

val THREAD_COUNT = 2

// TODO: test running from compiled JAR
fun main(args: Array<String>) {
    println("Starting javascript library scraper...")

    // TODO: find way to pass in command line args containing spaces
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

    println("Getting libraries on $THREAD_COUNT threads...")
    val futures = mutableListOf<CompletableFuture<List<String>>>()
    for (linkGroup in linkGroups.values) {
        futures.add(CompletableFuture.supplyAsync {
            val libraryFinder = LibraryFinder()
            libraryFinder.findJavascriptLibrariesInPages(linkGroup)
        })
    }

    // Block execution until all threads have finished execution
    while (futures.stream().anyMatch { !it.isDone }) {
        Thread.sleep(100)
    }
    println("Successfully extracted libraries from links")

    // Flatten the results into a single list
    val libraries = futures.stream().flatMap { it.get().stream() } .collect(Collectors.toList())
    println("${libraries.count()} libraries have been found")

    // TODO: deduplicate libraries and output
    val jw =
}