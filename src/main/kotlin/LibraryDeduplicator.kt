import info.debatty.java.stringsimilarity.JaroWinkler
import java.lang.RuntimeException
import java.util.stream.Collectors

class LibraryDeduplicator {
    // This is the threshold we use to determine if two library names refer to the same library;
    // JaroWinkler.similarity() outputs a number between 0 and 1,
    // where 0 means the strings are totally different and 1 means the strings are the same
    val SIMILARITY_THRESHOLD: Double = 0.95

    val jaroWinkler = JaroWinkler()

    // TODO: find a way to avoid having to code for runtime exceptions due to bugs in code
    // Given a list of library names, remove duplicates and return a map of unique library names to the use count
    fun deduplicateLibraries(allLibraryNames: List<String>): Map<String, Int> {
        val uniqueLibraries = mutableMapOf<String, Int>()

        for (libraryName in allLibraryNames) {
            // For each library, check if we have already found a similar one
            val matches = uniqueLibraries.keys.stream().filter { isSimilar(libraryName, it) } .collect(Collectors.toList())

            if (matches.size == 0) uniqueLibraries.put(libraryName, 1)
            else if (matches.size == 1) {
                val matchingUniqueLibraryName = matches.get(0)
                val useCount = uniqueLibraries.get(matchingUniqueLibraryName)
                if (useCount == null) {
                    throw RuntimeException("Couldn't the use count of library $matchingUniqueLibraryName was null!")
                }
                uniqueLibraries.replace(matchingUniqueLibraryName, useCount + 1)
            }
            else {
                throw RuntimeException("We have more than one 'similar' library in our unique libraries [$libraryName, ${matches.get(1)}]!")
            }
        }
        return uniqueLibraries
    }

    fun isSimilar(name1: String, name2: String): Boolean {
        // JaroWinkler.similarity() may not be a symetric function, so check with the strings swapped around
        return jaroWinkler.similarity(name1, name2) > SIMILARITY_THRESHOLD
                || jaroWinkler.similarity(name2, name1) > SIMILARITY_THRESHOLD
    }

}