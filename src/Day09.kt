import java.lang.Exception
import kotlin.math.abs

fun main() {
    data class Point(val x: Int, val y: Int) {
        override fun toString(): String {
            return "($x, $y)"
        }
    }

    class Rope(knotsCount: Int) {
        var knots = mutableListOf<Point>()
        init {
            for (i in 1..knotsCount) {
                knots.add(Point(0, 0))
            }
        }
        var visitedByTail: Set<String> = mutableSetOf(tail().toString())

        fun tail(): Point {
            return knots.last()
        }

        fun nextKnotHasToMove(i: Int): Boolean {
            return i + 1 < knots.size && (abs(knots[i].x - knots[i + 1].x) == 2 || abs(knots[i].y - knots[i + 1].y) == 2)
        }

        fun moveTailOf(i: Int) {
            if (nextKnotHasToMove(i)) {
                val dx = if (knots[i].x == knots[i + 1].x) 0 else 1
                val dy = if (knots[i].y == knots[i + 1].y) 0 else 1
                knots[i + 1] = Point(knots[i + 1].x + if (knots[i + 1].x > knots[i].x) -1 * dx else dx,
                                     knots[i + 1].y + if (knots[i + 1].y > knots[i].y) -1 * dy else dy )
                if (nextKnotHasToMove(i + 1)) {
                    moveTailOf(i + 1)
                }
            }
        }

        fun moveUp() {
            knots[0] = Point(knots[0].x - 1, knots[0].y)
        }

        fun moveDown() {
            knots[0] = Point(knots[0].x + 1, knots[0].y)
        }

        fun moveLeft() {
            knots[0] = Point(knots[0].x, knots[0].y - 1)
        }

        fun moveRight() {
            knots[0] = Point(knots[0].x, knots[0].y + 1)
        }

        fun move(direction: String) {
            when (direction) {
                "L" -> moveLeft()
                "R" -> moveRight()
                "U" -> moveUp()
                "D" -> moveDown()
            }
            moveTailOf(0)
            visitedByTail = visitedByTail.plus(tail().toString())
        }
    }

    fun findVisitedByTail(rope: Rope, input: List<String>): Int {
        for (line in input) {
            for (i in 1..line.split(" ")[1].toInt()) {
                rope.move(line[0].toString())
            }
        }
        return rope.visitedByTail.size
    }
    fun part1(input: List<String>): Int {
        val rope = Rope(2)
        return findVisitedByTail(rope, input)
    }

    fun part2(input: List<String>): Int {
        val rope = Rope(10)
        return findVisitedByTail(rope, input)
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    println(part1(testInput))
    val testInput2 = readInput("Day09_test_part2")
    println(part2(testInput2))
    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
