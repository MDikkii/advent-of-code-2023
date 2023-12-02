fun main() {
    fun part1(input: List<String>): Int {
        return input.map { line ->
            val gameSplit = line.split(":", ";")
            val gameId = gameSplit[0].getGameId()
            val firstNotValidValue =
                gameSplit.drop(1).firstOrNull { cubesDraw -> cubesDraw.isNotValidDraw() }
            val isValid = firstNotValidValue == null

            gameId to isValid
        }.filter { (_, isValid) -> isValid }.fold(0) { acc, element ->
            acc + element.first
        }
    }

    fun part2(input: List<String>): Int {
        return input.map { line ->
            val gameSplit = line.split(":", ";")
            calculateGameCubeSetPower(gameSplit.drop(1))
        }.fold(0) { acc, element ->
            acc + element
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

val part1CheckCubesMap = mapOf(
    "red" to 12,
    "green" to 13,
    "blue" to 14,
)

fun String.isNotValidDraw(): Boolean {
    return this
        .split(",")
        .map { colorInfo ->
            val (colorName, colorCubesCount) = colorInfo.getColorEntry()
            (part1CheckCubesMap[colorName] ?: 0) < colorCubesCount
        }.any { it }
}

fun String.getGameId(): Int {
    return this.split(" ")[1].toInt()
}

fun String.getColorEntry(): Pair<String, Int> {
    val colorEntry = this.trim().split(" ")
    val cubesCount = colorEntry[0].toInt()
    val colorName = colorEntry[1]
    return colorName to cubesCount
}

fun calculateGameCubeSetPower(draws: List<String>): Int {
    val cubesColorMaxCountMap = hashMapOf<String, Int>()
    draws.forEach { draw ->
        draw.split(",").map { colorInfo ->
            val (colorName, colorCubesCount) = colorInfo.getColorEntry()
            val currentColorCubesCount = cubesColorMaxCountMap[colorName] ?: 0
            if (currentColorCubesCount < colorCubesCount) {
                cubesColorMaxCountMap[colorName] = colorCubesCount
            }
        }
    }

    return cubesColorMaxCountMap.values.fold(1) { acc, element -> acc * element }
}
