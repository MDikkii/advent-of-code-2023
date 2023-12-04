fun main() {
    fun part1(input: List<String>): Int {
        return input.map { card ->
            val cardInfo = getCardInfo(card)
            val cardPoints =
                cardInfo.myNumbers.fold(0) { acc, element ->
                    return@fold if (cardInfo.winningNumbers.contains(element)) {
                        if (acc == 0) 1 else acc * 2
                    } else {
                        acc
                    }
                }

            cardPoints
        }.fold(0) { acc, element -> acc + element }
    }

    fun part2(input: List<String>): Int {
        val cardsCountMap = hashMapOf<Int, Int>()

        return input.mapIndexed { cardIndex, card ->
            val cardInfo = getCardInfo(card)
            val matchingCount =
                cardInfo.myNumbers.fold(0) { acc, element ->
                    return@fold if (cardInfo.winningNumbers.contains(element)) {
                        acc + 1
                    } else {
                        acc
                    }
                }

            // get how many cards of current index we have and update map with next cards counts
            val currentCardCount = (cardsCountMap[cardIndex] ?: 0) + 1
            for (i in 1..matchingCount) {
                if (cardsCountMap.contains(cardIndex + i)) {
                    cardsCountMap.computeIfPresent(cardIndex + i) { _, value -> value + currentCardCount }
                } else {
                    cardsCountMap[cardIndex + i] = currentCardCount
                }
            }

            currentCardCount
        }.fold(0) { acc, element -> acc + element }
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}

private fun getCardInfo(cardLine: String): CardInfo {
    val splitCard = cardLine.split(":", "|").map { it.trim() }
    val winningNumbers =
        splitCard[1].split(" ").filter { it.isNotEmpty() }.map { it.trim().toInt() }
            .toHashSet()

    val myNumbers =
        splitCard[2].split(" ").filter { it.isNotEmpty() }.map { it.trim().toInt() }

    return CardInfo(winningNumbers, myNumbers)
}

private data class CardInfo(val winningNumbers: HashSet<Int>, val myNumbers: List<Int>)
