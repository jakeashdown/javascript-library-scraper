class LibrarySorter {

    // Takes a map of library names to use counts and returns a sorted list of pairs of names and use counts
    fun sortLibrariesByPopularity(librariesWithPopularity: Map<String, Int>) : List<Pair<String, Int>> {
        val libraries = mutableListOf<Library>()
        for ((name, useCount) in librariesWithPopularity) {
            libraries.add(Library(name, useCount))
        }

        // Sort by descending popularity
        return libraries.sortedWith(compareBy({-1 * it.useCount})).map { Pair(it.name, it.useCount) }
    }

    class Library(val name: String, val useCount: Int)

}