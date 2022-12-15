import java.io.File
import java.util.Arrays
import java.util.Deque

fun main() {

    fun generateTallnessMapLeft(forest: List<List<Int>>): List<List<Int>> {
        val tallnessMap = ArrayList<MutableList<Int>>()
        for (i in forest.indices) {
            tallnessMap.add(mutableListOf())
            for (j in forest[i].indices) {
                tallnessMap[i].add(j,  if (j > 0) forest[i].subList(0, j).max() else 0)
            }
        }
        return tallnessMap
    }

    fun generateTallnessMapRight(forest: List<List<Int>>): List<List<Int>> {
        val tallnessMap = ArrayList<MutableList<Int>>()
        for (i in forest.indices) {
            tallnessMap.add(mutableListOf())
            for (j in forest[i].indices) {
                tallnessMap[i].add(j,  if (j < forest[i].size - 1) forest[i].subList(j + 1, forest[i].size).max() else 0)
            }
        }
        return tallnessMap
    }

    fun generateTallnessMapDown(forest: List<List<Int>>): List<List<Int>> {
        val tallnessMap = ArrayList<MutableList<Int>>()
        for (i in forest.indices) {
            tallnessMap.add(mutableListOf())
            for (j in forest[i].indices) {
                tallnessMap[i].add(j,  if (i > 0) forest.map { it[j] }.subList(0, i).max() else 0)
            }
        }
        return tallnessMap
    }

    fun generateTallnessMapUp(forest: List<List<Int>>): List<List<Int>> {
        val tallnessMap = ArrayList<MutableList<Int>>()
        for (i in forest.indices) {
            tallnessMap.add(mutableListOf())
            for (j in forest[i].indices) {
                tallnessMap[i].add(j,  if (i < forest.size - 1) forest.map { it[j] }.subList(i + 1, forest[i].size).max() else 0)
            }
        }
        return tallnessMap
    }



    fun part1(input: List<String>): Int {
        val forest = input.map { line -> line.split("").filter { it.isNotBlank() }.map { it.toInt() }}
        val tallnessMapLeft = generateTallnessMapLeft(forest)
        val tallnessMapRight = generateTallnessMapRight(forest)
        val tallnessMapUp = generateTallnessMapUp(forest)
        val tallnessMapDown = generateTallnessMapDown(forest)

        var count = 0
        for (i in 1 until forest.size - 1) {
            for (j in 1 until forest[i].size - 1) {
                if (forest[i][j] > tallnessMapDown[i][j] ||
                    forest[i][j] > tallnessMapUp[i][j] ||
                    forest[i][j] > tallnessMapRight[i][j] ||
                    forest[i][j] > tallnessMapLeft[i][j] ) {
                    count++
                    print("y")
                } else {
                    print(" ")
                }
            }
            println()
        }
        return count + forest.size * 2 + forest[0].size * 2 - 4
    }
    fun calculateScenicScore(x: Int, y: Int, forest: List<List<Int>>): Int {
        var score = 1
        var currentCount = 0
        //up
        for (i in x - 1 downTo 0) {
            if (forest[x][y] > forest[i][y]) {
                currentCount++
            } else {
                currentCount++
                break
            }
        }
        score *= currentCount
        //down
        currentCount = 0
        for (i in x + 1 until  forest.size) {
            if (forest[x][y] > forest[i][y]) {
                currentCount++
            } else {
                currentCount++
                break
            }
        }
        score *= currentCount
        //left
        currentCount = 0
        for (j in y - 1 downTo 0) {
            if (forest[x][y] > forest[x][j]) {
                currentCount++
            } else {
                currentCount++
                break
            }
        }
        score *= currentCount
        //right
        currentCount = 0
        for (j in y + 1 until  forest[0].size) {
            if (forest[x][y] > forest[x][j]) {
                currentCount++
            } else {
                currentCount++
                break
            }
        }
        score *= currentCount
        return score
    }


    fun part2(input: List<String>): Int {
        val forest = input.map { line -> line.split("").filter { it.isNotBlank() }.map { it.toInt() }}

        println(calculateScenicScore(3, 2, forest))
        var max = 0
        for (i in 1 until forest.size - 1) {
            for (j in 1 until forest[i].size - 1) {
                val score = calculateScenicScore(i, j , forest)
                max = if (score > max) score else max
            }
        }
        return max
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
//    println(part1(testInput))
    println(part2(testInput))
    val input = readInput("Day08")
//    println(part1(input))
    println(part2(input))
}
