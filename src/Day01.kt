fun main() {
    fun part1(input: List<String>): Int {
        return input.map { line ->
            "${line.first { it.isDigit() }}${line.last { it.isDigit() }}".toInt()
        }.fold(0) { acc, element ->
            acc + element
        }
    }

    val digitMap = mapOf(
            "one" to "1",
            "two" to "2",
            "three" to "3",
            "four" to "4",
            "five" to "5",
            "six" to "6",
            "seven" to "7",
            "eight" to "8",
            "nine" to "9"
    )

    fun part2(input: List<String>): Int {
        return input.map { line ->
            val firstWordDigit = line.findAnyOf(digitMap.keys)?.second
            val lastWordDigit = firstWordDigit?.let { line.findLastAnyOf(digitMap.keys)?.second }

            val firstWordDigitLine = firstWordDigit?.let {
                line.replace(it, digitMap[it]!!)
            } ?: line

            val lastWordDigitLine = lastWordDigit?.let {
                line.replace(it, digitMap[it]!!)
            } ?: line

            "${firstWordDigitLine.first { it.isDigit() }}${lastWordDigitLine.last { it.isDigit() }}".toInt()
        }.fold(0) { acc, element ->
            acc + element
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part2(testInput) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
