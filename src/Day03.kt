import java.util.stream.Collectors
import java.util.stream.IntStream
import kotlin.jvm.optionals.toList
import kotlin.jvm.optionals.toSet
import kotlin.streams.toList

@OptIn(ExperimentalStdlibApi::class)
fun main() {

    fun getPriority(item: Char): Int {
        if (item.isLowerCase()) {
            return item.code - 'a'.code + 1
        }
        return item.code - 'A'.code + 27
    }

    fun findWrongItem(backpack: String): String {
        val first = backpack.substring(0, backpack.length / 2).split("").toSet().minus("")
        val second = backpack.substring(backpack.length / 2).split("").toSet().minus("")
        return first.intersect(second).elementAt(0)

    }

    fun findBadge(backpacks: List<String>): Char {
        return backpacks.stream()
            .map { it.toSet() }
            .reduce { intersection, backpack -> intersection.intersect(backpack)}
            .get()
            .elementAt(0)
    }

    fun part1(input: List<String>): Int {
        val b = input.stream()
            .map { findWrongItem(it) }
            .map { getPriority(it[0])}
            .toList()
        return b.sum();
    }

    fun part2(input: List<String>): Int {
        return input
            .windowed(3, 3)
            .map { findBadge(it) }
            .sumOf { getPriority(it) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")

    val input = readInput("Day03")
//    println(part1(testInput))
    println(part2(testInput))
//    println(part1(input))
    println(part2(input))
}
