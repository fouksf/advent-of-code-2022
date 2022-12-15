import java.io.File
import java.lang.Exception
import java.util.LinkedList

fun main() {
    class Monkey(
        val items: MutableList<Long>,
        val operation: (Long) -> Long,
        val divisionTest: Long,
        val yesMonkey: Int,
        val noMonkey: Int
    ) {
        var inspected = 0L

        fun calmDown(i: Int) {
            items[i] = items[i] / 3
        }

        fun manageWorry(i: Int) {
            items[i] = items[i] % (19 * 2 * 3 * 17 * 13 * 7 * 5 * 11)
        }

        fun inspectItems(amPanicked: Boolean): List<Pair<Int, Long>> {
            val itemsToThrow = mutableListOf<Pair<Int, Long>>()
            for (i in items.indices) {
//                println("  Monkey takes item ${items[i]}")
                items[i] = operation(items[i])
//                println("    Your worry level increases to ${items[i]}")
                if(amPanicked) {
                    manageWorry(i)
                } else {
                    calmDown(i)
                }
//                println("    Your worry level decreases to ${items[i]}")
                if (items[i] % divisionTest == 0L) {
//                    println("    Monkey test returns true")
                    itemsToThrow.add(Pair(yesMonkey, items[i]))
//                    println("      Monkey throws item to monkey $yesMonkey")
                } else {
//                    println("    Monkey test returns false")
                    itemsToThrow.add(Pair(noMonkey, items[i]))
//                    println("      Monkey throws item to monkey $noMonkey")
                }
            }
            inspected += items.size
            items.clear()
            return itemsToThrow
        }

    }


    fun parseOperation(line: String): (Long) -> Long {
        val tokens = line.split(" = ")[1].split(" ")
        return fun(item: Long): Long {
            val a = if (tokens[0] == "old") item else tokens[0].toLong()
            val b = if (tokens[2] == "old") item else tokens[2].toLong()
            return when (tokens[1]) {
                "+" -> a + b
                "*" -> a * b
                else -> {
                    throw Exception("We don't know this operation " + tokens[1])
                }
            }
        }
    }

    fun parseMonkey(input: List<String>): Monkey {
        val items = input[1].split(": ")[1].split(", ").map { it.toLong() }.toMutableList()
        val operation = parseOperation(input[2])
        val divisionTest = input[3].split("by ")[1].toLong()
        val yesMonkey = input[4].split("monkey ")[1].toInt()
        val noMonkey = input[5].split("monkey ")[1].toInt()
        return Monkey(items, operation, divisionTest, yesMonkey, noMonkey )
    }

    fun part1(input: String): Long {
        val monkeys = input.split("\n\n")
            .map { it.split("\n") }
            .map { parseMonkey(it) }
        for (i in 0 until 20) {
//            println("Round $i")
            monkeys.forEachIndexed { index, monkey ->
//                println("Monkey $index's turn")
                val thrownItems = monkey.inspectItems(false)
                for (item in thrownItems) {
                    monkeys[item.first].items.add(item.second)
                }
            }
        }
        val twoMonkeys = monkeys.map { it.inspected }.sorted().takeLast(2)
        return twoMonkeys[0] * twoMonkeys[1]
    }

    fun part2(input: String): Long {
        val monkeys = input.split("\n\n")
            .map { it.split("\n") }
            .map { parseMonkey(it) }
        for (i in 0 until 10000) {
//            println("Round $i")
            monkeys.forEachIndexed { index, monkey ->
//                println("Monkey $index's turn")
                val thrownItems = monkey.inspectItems(true)
                for (item in thrownItems) {
                    monkeys[item.first].items.add(item.second)
                }
            }
        }
        val twoMonkeys = monkeys.map { it.inspected }.sorted().takeLast(2)
        return twoMonkeys[0] * twoMonkeys[1]
    }

// test if implementation meets criteria from the description, like:
    val testInput = File("src", "Day11_test.txt").readText()
//    println(part1(testInput))
//    println(part2(testInput))
    val input = File("src", "Day11.txt").readText()
//    println(part1(input))
    println(part2(input))
}
