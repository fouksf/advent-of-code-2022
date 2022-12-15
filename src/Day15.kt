import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {

    fun parseInput(input: List<String>, lineNumber: Int): HashMap<Pair<Int, Int>, String> {
        val map = HashMap<Pair<Int, Int>, String>()
        for (line in input) {
            val coordinates = line.replace("Sensor at", "")
                .replace(" [x,y]=".toRegex(), "")
                .split(": closest beacon is at")
                .map { coords -> coords.split(",").map { it.toInt() } }
            val x = coordinates[0][0]
            val y = coordinates[0][1]
            map[Pair(x, y)] = "S"
            map[Pair(coordinates[1][0], coordinates[1][1])] = "B"
            val manhattanDistance =
                abs(x - coordinates[1][0]) + abs(y - coordinates[1][1])
            for (dist in 1..manhattanDistance) {
                for (i in 0..dist) {
                    val j = dist - i
                    if (y + j == lineNumber) {
                        for (p in listOf(
                            Pair(x + i, y + j),
                            Pair(x - i, y + j)
                        )) {
                            if (!map.contains(p)) {
                                map[p] = "#"
                            }
                        }
                    } else if (y - j == lineNumber) {
                        for (p in listOf(
                            Pair(x + i, y - j),
                            Pair(x - i, y - j)
                        )) {
                            if (!map.contains(p)) {
                                map[p] = "#"
                            }
                        }
                    }
                }
            }
        }
        return map
    }

    fun part1(map: HashMap<Pair<Int, Int>, String>, lineNumber: Int): Int {
        return map.keys.filter { it.second == lineNumber }.count { map[it] == "#" }
    }

    fun part2(map: HashMap<Pair<Int, Int>, String>): Int {
        return 0
    }

// test if implementation meets criteria from the description, like:
    val testInput = parseInput(readInput("Day15_test"), 10)
    println(part1(testInput, 10))
//    println(part2(testInput))
    val input = parseInput(readInput("Day15"), 2000000)
    println(part1(input, 2000000))
//    println(part2(input))
}
