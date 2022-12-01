fun main() {

    fun readCalories(input: List<String>): List<Int> {
        val calories = ArrayList<Int>()
        var current = 0
        for (cal in input) {
            if (cal.isBlank()) {
                calories.add(current)
                current = 0
            } else {
                current += cal.toInt()
            }
        }
        if (current != 0) {
            calories.add(current)
        }
        return calories
    }

    fun part1(input: List<String>): Int {
        val calories = readCalories(input)
        return calories.max()
    }

    fun part2(input: List<String>): Int {
        val calories = readCalories(input)
        return calories.sorted().takeLast(3).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")

    val input = readInput("Day01")
//    println(part1(testInput))
    println(part2(testInput))
//    println(part1(input))
    println(part2(input))
}
