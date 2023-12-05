fun main() {
    fun part1(input: List<String>): Long {
        val seeds = getSeeds(input[0])
        println("seeds: $seeds")

        val stepsResultsMap = hashMapOf<Long, MutableList<Long>>()
        for (seed in seeds) {
            stepsResultsMap[seed] = mutableListOf(seed)
        }

        println(stepsResultsMap)

        val sortedSet = sortedSetOf<Long>()
        val almanacStepMap = hashMapOf<Long, AlmanacEntryInfo>()

        var lastStep = 0
        var currentLine = 1
        while (currentLine < input.size) {
            if (input[currentLine].isBlank()) {
                // finished almanac step, clear step data
                sortedSet.clear()
                almanacStepMap.clear()
                currentLine++
            } else {
                // almanac step, skip title line, read other almanac info lines
                currentLine++
                while (currentLine < input.size && input[currentLine].isNotBlank()) {
                    val almanacLine = input[currentLine]
                    val almanacEntryInfo = getAlmanacEntryInfo(almanacLine)
                    sortedSet.add(almanacEntryInfo.sourceStart)
                    almanacStepMap[almanacEntryInfo.sourceStart] = almanacEntryInfo
                    println("current Line: $almanacLine")
                    currentLine++
                }

                println("sortedSet: $sortedSet")

                for (seed in seeds) {
                    println("seed: $seed")
                    val lastStepValue = stepsResultsMap[seed]!![lastStep]
                    val potentialRangeStart = sortedSet.floor(lastStepValue)
                    if (potentialRangeStart == null) {
                        stepsResultsMap[seed]!!.add(lastStepValue)
                        println("seed: $seed transformations: ${stepsResultsMap[seed]!!}")
                        continue
                    }
                    val potentialAlmanacEntry = almanacStepMap[potentialRangeStart]!!

                    if (lastStepValue <= potentialAlmanacEntry.sourceEnd) {
                        stepsResultsMap[seed]!!.add(lastStepValue + potentialAlmanacEntry.destinationAlignment)
                    } else {
                        stepsResultsMap[seed]!!.add(lastStepValue)
                    }
                    println("seed: $seed transformations: ${stepsResultsMap[seed]!!}")
                }

                lastStep++

                println("sortedSet: $sortedSet")
                println("almanacStepMap: $almanacStepMap")
                println("currentStep: ${lastStep}")
            }
        }


        val result = stepsResultsMap.values.map { it[lastStep] }.min()
        println("stepsResultsMap: $stepsResultsMap")
        println("result: $result")

        return result
    }

    fun part2(input: List<String>): Long {
        getSeedsRanges(input[0])
        return 0
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35L)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}

private fun getSeeds(almanacLine: String): List<Long> {
    return almanacLine.split(" ").drop(1).filter { it.isNotBlank() }.map { it.toLong() }
}

private fun getSeedsRanges(almanacLine: String): List<SeedRange> {
    val seedsInfoList =
        almanacLine.split(" ").drop(1).filter { it.isNotBlank() }.map { it.toLong() }
    val seedRanges = sortedMapOf<Long, SeedRange>()

    for (i in seedsInfoList.indices step 2) {
        val seedRangeStart = seedsInfoList[i]
        seedRanges[seedRangeStart] =
            (SeedRange(seedRangeStart, seedRangeStart + seedsInfoList[i + 1] - 1))
    }
    println("seedRanges: $seedRanges")

    return seedRanges.values.toList()
}

private fun getAlmanacEntryInfo(almanacLine: String): AlmanacEntryInfo {
    val (destinationStart, sourceStart, rangeSize) = almanacLine.split(" ")
        .filter { it.isNotBlank() }.map { it.toLong() }
    return AlmanacEntryInfo(
        sourceStart,
        sourceStart + rangeSize - 1,
        destinationStart - sourceStart
    )
}

private data class AlmanacEntryInfo(
    val sourceStart: Long,
    val sourceEnd: Long,
    val destinationAlignment: Long
)

private data class SeedRange(
    val rangeStart: Long,
    val rangeEnd: Long
)
