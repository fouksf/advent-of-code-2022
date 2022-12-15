import java.lang.Exception
import kotlin.math.abs

fun main() {

    fun part1(input: List<String>): Int {
        var register = 1
        var result = 0
        var cycle = 1
        val cyclesOfInterest = arrayListOf(20, 60, 100, 140, 180, 220)
        for (line in input) {
            if (cycle in cyclesOfInterest) {
                result += cycle * register
            }
            if (line == "noop") {
                cycle++
            } else {
                if (cycle + 1 in cyclesOfInterest) {
                    result += (cycle + 1) * register
                }
                cycle += 2
                register += line.split(" ")[1].toInt()
            }
        }
        return result
    }

    fun draw(screen: List<MutableList<String>>, spriteIndex: Int, cycle: Int) {
        screen[cycle / 40].add(if (abs(cycle % 40 - spriteIndex) < 2) "#" else ".")
    }

    fun part2(input: List<String>): Unit {
        var spriteIndex = 1
        var cycle = 0
        val screen = List<MutableList<String>>(6) { mutableListOf() }
        for (line in input) {
            draw(screen, spriteIndex, cycle)
            if (line == "noop") {
                cycle++
            } else {
                cycle += 1
                draw(screen, spriteIndex, cycle)
                cycle += 1
                spriteIndex += line.split(" ")[1].toInt()
            }
        }
        for (line in screen) {
            println(line.joinToString(""))
        }
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
//    println(part1(testInput))
    part2(testInput)
    val input = readInput("Day10")
//    println(part1(input))
    part2(input)
}
