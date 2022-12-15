import java.io.File
import java.util.Deque

fun main() {
    fun part1(input: String): Int {
        return input.windowed(4, 1)
            .toList()
            .mapIndexed { index, s -> if (s.toSet().count() == 4) index + 4 else Int.MAX_VALUE }
            .min()
    }

    fun part2(input: String): Int {
        return input.windowed(14, 1)
            .toList()
            .mapIndexed { index, s -> if (s.toSet().count() == 14) index + 14 else Int.MAX_VALUE }
            .min()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = File("src", "Day06_test.txt").readText()
//    println(part1(testInput))
    println(part2(testInput))

    val input = File("src", "Day06.txt").readText()
//    println(part1(input))
    println(part2(input))
}
