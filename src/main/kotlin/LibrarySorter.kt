class LibrarySorter() {

    fun sortLibrariesByPopularity(librariesWithPopularity: Map<String, Int>) : List<String> {
        val libraries = mutableListOf<Library>()
        for ((name, useCount) in librariesWithPopularity) {
            libraries.add(Library(name, useCount))
        }

        // Sort by descending popularity
        return libraries.sortedWith(compareBy({-1 * it.useCount})).map { it.name }
    }

    class Library(val name: String, val useCount: Int)

}