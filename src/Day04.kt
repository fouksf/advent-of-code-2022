import kotlin.streams.toList

fun main() {
    fun hasTotalOverlap(a: List<Int>, b: List<Int>): Boolean {
        return a[0] >= b[0] && a[1] <= b[1]
    }
    fun hasOverlap(a: List<Int>, b: List<Int>): Boolean {
        return a[0] in b[0]..b[1] ||
                a[1] in b[0]..b[1] ||
                b[0] in a[0]..a[1] ||
                b[1] in a[0]..a[1]
    }

    fun part1(input: List<String>): Long {
        return input.stream()
            .map { it.split(",") }
            .map { it.stream().map { sections -> sections.split("-") }.toList() }
            .map {
                it.stream()
                    .map { section -> section.map { sectionNumber -> sectionNumber.toInt() } }
                    .toList()
            }
            .filter { hasTotalOverlap(it[0], it[1]) || hasTotalOverlap(it[1], it[0]) }
            .count()
    }

    fun part2(input: List<String>): Long {
        return input.stream()
            .map { it.split(",") }
            .map { it.stream().map { sections -> sections.split("-") }.toList() }
            .map {
                it.stream()
                    .map { section -> section.map { sectionNumber -> sectionNumber.toInt() } }
                    .toList()
            }
            .filter { hasOverlap(it[1], it[0]) }
            .count()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")

    val input = readInput("Day04")
//    println(part1(testInput))
    println(part2(testInput))
//    println(part1(input))
    println(part2(input))
}
