import kotlin.math.max
import kotlin.math.min

fun main() {

    fun parseTuple(tuple: List<List<Int>>, result: HashMap<Pair<Int, Int>, String>) {
        val x1 = tuple[0][0]
        val x2 = tuple[1][0]
        val y1 = tuple[0][1]
        val y2 = tuple[1][1]
        for (i in min(x1, x2)..max(x1, x2)) {
            for (j in min(y1, y2)..max(y1, y2)) {
                result[Pair(i, j)] = "#"
            }
        }
    }

    fun parseLine(line: String, result: HashMap<Pair<Int, Int>, String>) {
        line.split(" -> ").windowed(2, 1).toList()
            .forEach { parseTuple(it.map { it.split(",").map { it.toInt() } }, result) }
    }

    fun parseInput(input: List<String>): HashMap<Pair<Int, Int>, String> {
        val result = HashMap<Pair<Int, Int>, String>()
        input.forEach { parseLine(it, result) }
        return result
    }

    fun releaseSand(map: HashMap<Pair<Int, Int>, String>): Pair<Int, Int>? {
        var sand = Pair(500, 0)
        val lowestPoint = map.keys.map { it.second }.max()
        while (lowestPoint >= sand.second) {
            val bellowSand = Pair(sand.first, sand.second + 1)
            if (map.contains(bellowSand)) {
                val diagonallyLeftBellowSand = Pair(sand.first - 1, sand.second + 1)
                if (map.contains(diagonallyLeftBellowSand)) {
                    val diagonallyRightBellowSand = Pair(sand.first + 1, sand.second + 1)
                    if (map.contains(diagonallyRightBellowSand)) {
                        map[sand] = "o"
                        return sand
                    } else {
                        sand = diagonallyRightBellowSand
                    }
                } else {
                    sand = diagonallyLeftBellowSand
                }
            } else {
                sand = bellowSand
            }
        }
        return null
    }

    fun releaseSandWithFloor(map: HashMap<Pair<Int, Int>, String>, floor: Int): Pair<Int, Int> {
        var sand = Pair(500, 0)
        while (floor > sand.second + 1) {
            val bellowSand = Pair(sand.first, sand.second + 1)
            if (map.contains(bellowSand)) {
                val diagonallyLeftBellowSand = Pair(sand.first - 1, sand.second + 1)
                if (map.contains(diagonallyLeftBellowSand)) {
                    val diagonallyRightBellowSand = Pair(sand.first + 1, sand.second + 1)
                    if (map.contains(diagonallyRightBellowSand)) {
                        map[sand] = "o"
                        return sand
                    } else {
                        sand = diagonallyRightBellowSand
                    }
                } else {
                    sand = diagonallyLeftBellowSand
                }
            } else {
                sand = bellowSand
            }
        }
        map[sand] = "o"
        return sand
    }

    fun printMap(map: HashMap<Pair<Int, Int>, String>) {
        val minx = map.keys.map { it.first }.min()
        val maxx = map.keys.map { it.first }.max()
        val miny = map.keys.map { it.second }.min()
        val maxy = map.keys.map { it.second }.max()
        println("$minx - $maxx; $miny - $maxy")
        for (i in minx..maxx) {
            print(if (i == 500) "+" else ".")
        }
        println()
        for (j in miny..maxy) {
            print(j)
            for (i in minx..maxx) {
                print(map.getOrDefault(Pair(i, j), "."))
            }
            println()
        }
    }

    fun part1(map: HashMap<Pair<Int, Int>, String>): Int {
        var counter = 0
        while (releaseSand(map) != null) {
            counter++
        }
        return counter
    }

    fun part2(map: HashMap<Pair<Int, Int>, String>): Int {
        val floor = map.keys.map { it.second }.max() + 2
        var counter = 0
        while (releaseSandWithFloor(map, floor) != Pair(500, 0)) {
            counter++
        }
        return counter
    }

// test if implementation meets criteria from the description, like:
    val testInput = parseInput(readInput("Day14_test"))
//    printMap(testInput)
//    println(part1(testInput))
    println(part2(testInput))
    val input = parseInput(readInput("Day14"))
//    printMap(input)
//    println(part1(input))
    println(part2(input))
}
