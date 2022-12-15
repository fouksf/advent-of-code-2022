import java.io.File
import java.lang.Exception
import kotlin.math.abs

fun main() {
    fun parseInput(input: String): List<List<String>> {
        return input.split("\n")
            .filter { it != "" }
            .map { line -> line.split("").filter { it != "" } }
    }

    fun shouldVisit(
        p: Pair<Int, Int>,
        grid: List<List<String>>,
        visited: List<Pair<Int, Int>>,
        current: Pair<Int, Int>
    ): Boolean {
        return p.first in grid.indices &&
                p.second in grid[p.first].indices &&
                !visited.contains(p) &&
                grid[current.first][current.second][0].code - grid[p.first][p.second][0].code > -2
    }


    fun findHighGround(
        grid: List<List<String>>,
        start: Pair<Int, Int>,
        end: Pair<Int, Int>,
        visited: MutableList<Pair<Int, Int>>
    ): Int {
        val toVisit = ArrayDeque<Pair<Int, Int>>()
        val distances = HashMap<Pair<Int, Int>, Int>()
        toVisit.add(start)
        distances[start] = 0

        while (!toVisit.isEmpty()) {
            val currentPoint = toVisit.removeFirst()
            visited.add(currentPoint)
            if (currentPoint == end) {
                if (distances.contains(currentPoint)) {
                    return distances[currentPoint]!!
                }
                throw Exception("something went wrong")
            } else {
                for (p in listOf(
                    Pair(currentPoint.first - 1, currentPoint.second),
                    Pair(currentPoint.first + 1, currentPoint.second),
                    Pair(currentPoint.first, currentPoint.second - 1),
                    Pair(currentPoint.first, currentPoint.second + 1)
                )) {
                    if (shouldVisit(p, grid, visited, currentPoint)) {
                        if (!toVisit.contains(p)) {
                            toVisit.add(p)
                        }
                        if (!distances.contains(p) || distances[p]!! > distances.getOrDefault(
                                currentPoint,
                                Int.MAX_VALUE
                            ) + 1
                        ) {
                            distances[p] = distances.getOrDefault(currentPoint, -2) + 1
                        }
                    }
                }
            }
        }
        return Int.MAX_VALUE
    }

    fun fixThingy(thingy: String): String {
        if (thingy == "S") {
            return "a"
        } else if (thingy == "E") {
            return "z"
        }
        return thingy
    }

    fun part1(grid: List<List<String>>): Int? {
        var start: Pair<Int, Int>? = null
        var end: Pair<Int, Int>? = null
        for (i in grid.indices) {
            for (j in grid[i].indices) {
                if (grid[i][j] == "S") {
                    start = Pair(i, j)
                }
                if (grid[i][j] == "E") {
                    end = Pair(i, j)
                }
            }
        }
        if (start == null || end == null) {
            throw Exception("ko stana tuka we")
        }
        val newGrid = grid.map { line -> line.map { fixThingy(it) } }
        val visited = mutableListOf<Pair<Int, Int>>()
        return findHighGround(newGrid, start, end, visited)
    }

    fun part2(grid: List<List<String>>): Int? {
        var end: Pair<Int, Int>? = null
        for (i in grid.indices) {
            for (j in grid[i].indices) {
                if (grid[i][j] == "E") {
                    end = Pair(i, j)
                }
            }
        }
        if (end == null) {
            throw Exception("ko stana tuka we")
        }
        val newGrid = grid.map { line -> line.map { fixThingy(it) } }
        val distances = HashMap<Pair<Int, Int>, Int>()
        for (i in grid.indices) {
            for (j in grid[i].indices) {
                if (grid[i][j] == "a") {
                    val start = Pair(i, j)
                    val visited = mutableListOf<Pair<Int, Int>>()
                    distances[start] = findHighGround(newGrid, start, end!!, visited)
                }
            }
        }
        return distances.values.toList().min()
    }

// test if implementation meets criteria from the description, like:
    val testInput = parseInput(File("src", "Day12_test.txt").readText())
    println(part1(testInput))
    println(part2(testInput))
    val input = parseInput(File("src", "Day12.txt").readText())
    println(part1(input))
    println(part2(input))
}
