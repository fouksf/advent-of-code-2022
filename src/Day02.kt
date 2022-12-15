enum class PlayedChoice() {
    A {
        override fun getScore(): Int {
            return 1
        }
    },
    B {
        override fun getScore(): Int {
            return 2
        }
    },
    C {
        override fun getScore(): Int {
            return 3
        }
    };

    abstract fun getScore(): Int
}

enum class RockPaperScissors() {
    X {
        override fun getScore(played: PlayedChoice): Int {
            return when (played) {
                PlayedChoice.A -> 4
                PlayedChoice.B -> 1
                PlayedChoice.C -> 7
            }
        }
    },
    Y {
        override fun getScore(played: PlayedChoice): Int {
            return when (played) {
                PlayedChoice.A -> 8
                PlayedChoice.B -> 5
                PlayedChoice.C -> 2
            }
        }
    },
    Z {
        override fun getScore(played: PlayedChoice): Int {
            return when (played) {
                PlayedChoice.A -> 3
                PlayedChoice.B -> 9
                PlayedChoice.C -> 6
            }
        }
    };

    abstract fun getScore(played: PlayedChoice): Int
}

enum class Result() {
    X {
        override fun getScore(played: PlayedChoice): Int {
            return 0
        }

        override fun getMyChoice(played: PlayedChoice): PlayedChoice {
            return when (played) {
                PlayedChoice.A -> PlayedChoice.C
                PlayedChoice.B -> PlayedChoice.A
                PlayedChoice.C -> PlayedChoice.B
            }
        }
    },
    Y {
        override fun getScore(played: PlayedChoice): Int {
            return 3
        }

        override fun getMyChoice(played: PlayedChoice): PlayedChoice {
            return played
        }
    },
    Z {
        override fun getScore(played: PlayedChoice): Int {
            return 6
        }

        override fun getMyChoice(played: PlayedChoice): PlayedChoice {
            return when (played) {
                PlayedChoice.A -> PlayedChoice.B
                PlayedChoice.B -> PlayedChoice.C
                PlayedChoice.C -> PlayedChoice.A
            }
        }
    };
    abstract fun getScore(played: PlayedChoice): Int
    abstract fun getMyChoice(played: PlayedChoice): PlayedChoice
}


fun main() {

    fun readGames(input: List<String>): List<Int> {
        val scores = ArrayList<Int>()
        for (game in input) {
            val playedChoice = PlayedChoice.valueOf(game.substring(0, 1))
            val myChoice = RockPaperScissors.valueOf(game.substring(2))
            scores.add(myChoice.getScore(playedChoice))
        }
        return scores
    }

    fun part1(input: List<String>): Int {
        val games = readGames(input)
        return games.sum()
    }

    fun part2(input: List<String>): Int {
        val scores = ArrayList<Int>()
        for (game in input) {
            val playedChoice = PlayedChoice.valueOf(game.substring(0, 1))
            val desiredOutcome = Result.valueOf(game.substring(2))
            scores.add(desiredOutcome.getScore(playedChoice) + desiredOutcome.getMyChoice(playedChoice).getScore())
        }
        return scores.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")

    val input = readInput("Day02")
//    println(part1(testInput))
    println(part2(testInput))
//    println(part1(input))
    println(part2(input))
}
