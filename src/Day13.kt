import java.io.File
import java.lang.Exception
import kotlin.math.abs
import kotlin.math.min
import kotlin.reflect.typeOf

fun main() {
    fun parseArray(iterator: ListIterator<String>): List<Any> {
        val result = mutableListOf<Any>()
        while (iterator.hasNext()) {
            val token = iterator.next()
            if (token == "]") {
                return result
            } else if (token == ",") {
                continue
            } else if (token == "[") {
                result.add(parseArray(iterator))
            } else {
                val next = iterator.next()
                if (next.matches("[0-9]".toRegex())) {
                    result.add((token + next).toInt())
                } else {
                    result.add(token.toInt())
                    iterator.previous()
                }
            }
        }
        throw Exception("ko stana tuka")
    }

    fun doThingy(it: String): List<Any> {
        val iterator = it.split("").filter { it != "" }.listIterator()
        iterator.next()
        return parseArray(iterator)
    }

    fun parseInput(input: String): List<List<List<Any>>> {
        return input.split("\n\n")
            .map { tuple ->
                tuple
                    .split("\n")
                    .filter { it != "" }
                    .map { doThingy(it) }
            }
    }

    fun parseInput2(input: String): List<List<Any>> {
        return input.replace("\n\n", "\n")
            .split("\n")
            .filter { it != "" }
            .map { doThingy(it) }
            .plus(arrayListOf(arrayListOf(arrayListOf(2))))
            .plus(arrayListOf(arrayListOf(arrayListOf(6))))
    }


    fun areCorrectlyOrdered(a: List<Any>, b: List<Any>): Boolean? {
        for (i in 0 until min(a.size, b.size)) {
            if (a[i] is Int && b[i] is Int) {
                if (a[i] == b[i]) {
                    continue
                }
                return (a[i] as Int) < (b[i] as Int)
            } else if (a[i] is List<*> && b[i] is List<*>) {
                val comparison = areCorrectlyOrdered(a[i] as List<Any>, b[i] as List<Any>)
                if (comparison != null) {
                    return comparison
                }
            } else if (a[i] is Int && b[i] is List<*>) {
                val comparison = areCorrectlyOrdered(listOf(a[i]), b[i] as List<Any>)
                if (comparison != null) {
                    return comparison
                }
            } else if (a[i] is List<*> && b[i] is Int) {
                val comparison = areCorrectlyOrdered(a[i] as List<Any>, listOf(b[i]))
                if (comparison != null) {
                    return comparison
                }
            }
        }
        if (a.size == b.size) {
            return null
        }
        return a.size < b.size
    }

    fun notALambda(index: Int, anies: List<List<Any>>): Int {
        val areCorrect =
            areCorrectlyOrdered(anies[0], anies[1]) ?: throw Exception("kur tate banica")
        return if (areCorrect) index + 1 else 0
    }

    fun part1(signal: List<List<List<Any>>>): Int {
        return signal.mapIndexed { index, anies ->
            notALambda(index, anies)
        }.sum()
    }

    fun part2(signal: List<List<Any>>): Int {
        val sorted = signal.sortedWith(Comparator<List<Any>> { a, b ->
            if (areCorrectlyOrdered(a, b) == true) -1 else 1
        })

        var index1 = sorted.indexOf(arrayListOf(arrayListOf(2))) + 1
        var index2 = sorted.indexOf(arrayListOf(arrayListOf(6))) + 1
        return index1 * index2
    }

// test if implementation meets criteria from the description, like:
    val testInput = parseInput(File("src", "Day13_test.txt").readText())
    val testInput2 = parseInput2(File("src", "Day13_test.txt").readText())
//    println(part1(testInput))
    println(part2(testInput2))
    val input = parseInput(File("src", "Day13.txt").readText())
//    println(part1(input))
    val input2 = parseInput2(File("src", "Day13.txt").readText())
    println(part2(input2))
}
