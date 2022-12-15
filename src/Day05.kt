import java.io.File
import java.util.Deque

fun main() {
    fun parseBox(index: Int, box: String, boxes: List<ArrayDeque<Char>>) {
        if (box.isNotBlank()) {
            boxes[index].add(box[1])
        }
    }

    //    [T] [R] [Z]     [H] [H] [G] [C]
    fun parseLine(line: String, boxes: List<ArrayDeque<Char>>): Unit {
        line
            .windowed(4, 4, true)
            .toList()
            .forEachIndexed { index, box -> parseBox(index, box, boxes) }
    }

    fun parseBoxes(input: String): List<ArrayDeque<Char>> {
        val lines = input
            .split("\n")
        val boxes = lines
            .last()
            .split(" ")
            .filter { it.isNotEmpty() }
            .map { ArrayDeque<Char>() }
        lines
            .dropLast(1)
            .reversed()
            .forEach { parseLine(it, boxes) }
        return boxes

    }

    fun executeCommand(command: String, boxes: List<ArrayDeque<Char>>) {
        val numbers = command
            .replace("[a-z]".toRegex(), "")
            .replace("  ", " ")
            .split(" ")
            .filter { it.isNotBlank() }
            .map { it.toInt() }
        for (i in 1..numbers[0]) {
            boxes[numbers[2] - 1].add(boxes[numbers[1] - 1].last())
            boxes[numbers[1] - 1].removeLast()
        }
    }

    fun executeCommand9001(command: String, boxes: List<ArrayDeque<Char>>) {
        val numbers = command
            .replace("[a-z]".toRegex(), "")
            .replace("  ", " ")
            .split(" ")
            .filter { it.isNotBlank() }
            .map { it.toInt() }
        boxes[numbers[2] - 1].addAll(boxes[numbers[1] - 1].takeLast(numbers[0]))
        for (i in 1..numbers[0]) {
            boxes[numbers[1] - 1].removeLast()
        }
    }


    fun part1(input: String): String {
        val boxes = parseBoxes(input.split("\n\n")[0])
        input.split("\n\n")[1].split("\n").filter { it.isNotBlank() }
            .forEach { executeCommand(it, boxes) }
        return boxes.map { it.last() }.joinToString("")
    }

    fun part2(input: String): String {
        val boxes = parseBoxes(input.split("\n\n")[0])
        input.split("\n\n")[1].split("\n").filter { it.isNotBlank() }
            .forEach { executeCommand9001(it, boxes) }
        return boxes.map { it.last() }.joinToString("")
    }

    // test if implementation meets criteria from the description, like:
    val testInput = File("src", "Day05_test.txt").readText()
    println(part1(testInput))
    println(part2(testInput))

    val input = File("src", "Day05.txt").readText()
    println(part1(input))
    println(part2(input))
}
