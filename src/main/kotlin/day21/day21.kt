fun main() {
    Day21()
}

class Day21 : Runner() {

    override fun a() {
        val reader = getReader()
        val firstStart = reader.readLine().split("starting position: ")[1].toLong()
        val secondStart = reader.readLine().split("starting position: ")[1].toLong()
        val person1 = Person()
        person1.currentPosition = firstStart
        val person2 = Person()
        person2.currentPosition = secondStart
        var diceNumber = 0
        var counter = 0
        var firstPerson = true
        while (true) {
            var nextSteps = 0
            diceNumber++
            if (diceNumber == 101) {
                diceNumber = 1
            }
            nextSteps += diceNumber
            diceNumber++

            if (diceNumber == 101) {
                diceNumber = 1
            }
            nextSteps += diceNumber
            diceNumber++

            if (diceNumber == 101) {
                diceNumber = 1
            }
            nextSteps += diceNumber
            counter += 3
            if (firstPerson) {
                if (person1.takeStep(nextSteps.toLong(), 1000)) {
                    println(counter)
                    print(person2.sum * counter)
                    return
                }

            } else {
                if (person2.takeStep(nextSteps.toLong(), 1000)) {
                    println(counter)
                    print(person1.sum * counter)
                    return
                }
            }
            firstPerson = !firstPerson
        }

    }

    override fun b() {
        val reader = getReader()
        var winnerHolder = WinnerHolder()
        val firstStart = reader.readLine().split("starting position: ")[1].toLong()
        val secondStart = reader.readLine().split("starting position: ")[1].toLong()
        val person1 = Person()
        person1.currentPosition = firstStart
        val person2 = Person()
        person2.currentPosition = secondStart
        nextRoll(0, 1, false, person1, person2, winnerHolder)
        print(winnerHolder.playerOneWinner)
        print(winnerHolder.playerTwoWinner)
    }

    private fun nextRoll(
        steps: Long,
        multiplier2: Long,
        firstPerson: Boolean,
        person1: Person,
        person2: Person,
        winnerHolder: WinnerHolder
    ) {
        if (steps != 0L) {
            if (firstPerson) {
                if (person1.takeStep(steps, 21)) {
                    val multiplier = when (steps) {
                        3L -> 1L
                        4L -> 3L
                        5L -> 6L
                        6L -> 7L
                        7L -> 6L
                        8L -> 3L
                        9L -> 1L
                        else -> 0L
                    }
                    winnerHolder.playerOneWinner += multiplier2
                    return
                }
            } else {
                if (person2.takeStep(steps, 21)) {
                    val multiplier = when (steps) {
                        3L -> 1L // 111
                        4L -> 3L // 121 211 112
                        5L -> 6L // 122 221 212 311 113 131
                        6L -> 7L // 222 312 321 123 132 213 231
                        7L -> 6L
                        8L -> 3L
                        9L -> 1L
                        else -> 0L
                    }
                    winnerHolder.playerTwoWinner += multiplier2
                    return
                }
            }
        }
        for (i in listOf(3 to 1L, 4 to 3L, 5 to 6L, 6 to 7L, 7 to 6L, 8 to 3L, 9 to 1L)) {
            nextRoll(i.first.toLong(), multiplier2 * i.second, !firstPerson, person1.copy(), person2.copy(), winnerHolder)
        }
    }

    data class Person(
        var sum: Long = 0,
        var currentPosition: Long = 0
    ) {
        fun takeStep(diceNumber: Long, winningNumber: Int): Boolean {
            currentPosition += diceNumber
            while (currentPosition >= 11) {
                currentPosition -= 10
            }
            sum += currentPosition
            return sum >= winningNumber
        }
    }


}

class WinnerHolder {
    var playerOneWinner = 0L
    var playerTwoWinner = 0L
}


