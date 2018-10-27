import java.util.concurrent.CompletableFuture
import java.util.stream.Collectors

val THREAD_COUNT = 2

// TODO: add .idea and .gradle to gitignore
fun main(args: Array<String>) {
    println("Starting javascript library scraper...")

    if (args.size != 1) {
        println("Single program argument required - search term")
        System.exit(1)
    }

    val searchTerm: String = args[0]
    val googleSearcher = GoogleSearcher()

    println("Getting google results for search term '$searchTerm...'")
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

    println("Removing duplicate libraries...")
    val deduplicator = LibraryDeduplicator()
    val uniqueLibraries = deduplicator.deduplicateLibraries(libraries)

    println("Displaying the 5 most popular libraies...")
    val sorter = LibrarySorter()
    val sortedLibraries = sorter.sortLibrariesByPopularity(uniqueLibraries)
    for (i in 0..4) {
        println("[$i] ${sortedLibraries.get(i)}")
    }
}
