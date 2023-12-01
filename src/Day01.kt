fun main() {
    fun part1(input: List<String>): Int {
        return input.map { line ->
            "${line.first { it.isDigit() }}${line.last { it.isDigit() }}".toInt()
        }.fold(0) { acc, element ->
            acc + element
        }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
