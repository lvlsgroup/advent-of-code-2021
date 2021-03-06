import java.io.BufferedReader

fun main() {
    Day4()
}

class Day4 : Runner() {
    override fun a() {
        val gamePlan = parseBingos(getReader())
        val numbers = gamePlan.first
        var bingos = gamePlan.second
        numbers.forEach { nextNumber ->
            bingos = markNext(bingos, nextNumber)
            bingos.forEach { bingo ->
                if (hasBingo(bingo)) {
                    println(calculateScore(bingo) * nextNumber)
                    return
                }
            }
        }
    }

    override fun b() {
        val gamePlan = parseBingos(getReader())
        val numbers = gamePlan.first
        var bingos = gamePlan.second
        numbers.forEach { nextNumber ->
            bingos = markNext(bingos, nextNumber)
            bingos = bingos.filter { bingo ->
                if (hasBingo(bingo)) {
                    if (bingos.size == 1) {
                        println(calculateScore(bingo) * nextNumber)
                        return
                    }
                    false
                } else {
                    true
                }
            }.toMutableList()
        }
    }

    private fun markNext(bingos: MutableList<List<List<Int>>>, nextNumber: Int): MutableList<List<List<Int>>> {
        return bingos.map { bingo ->
            bingo.map { row ->
                row.map { number ->
                    if (number == nextNumber) {
                        -1
                    } else {
                        number
                    }
                }
            }
        }.toMutableList()
    }

    private fun calculateScore(bingo: List<List<Int>>): Int {
        return bingo.sumOf { it.filter { it != -1 }.sum() }
    }

    private fun hasBingo(bingo: List<List<Int>>): Boolean {
        bingo.forEach { row ->
            val allInRowMarked = row.all { it == -1 }
            if (allInRowMarked) {
                return true
            }
        }
        for (j in 0 until bingo[0].size) {
            var allInColMarked = true
            for (i in 0 until bingo.size) {
                if (bingo[i][j] != -1) {
                    allInColMarked = false
                }
            }
            if (allInColMarked) {
                return true
            }
        }
        return false
    }

    fun parseBingos(reader: BufferedReader): Pair<List<Int>, MutableList<List<List<Int>>>> {
        val numbers = reader.readLine().split(",").map { it.toInt() }
        reader.readLine()
        var nextLine = reader.readLine()
        val bingos = mutableListOf<List<List<Int>>>()
        var currentBingo: MutableList<List<Int>>? = null
        while (nextLine != null) {
            if (nextLine.length < 2) {
                currentBingo = null
            } else {
                if (currentBingo == null) {
                    currentBingo = mutableListOf()
                    bingos.add(currentBingo)
                }
                currentBingo.add(nextLine.trimStart().replace("  ", " ").split(" ").map { it.trim().toInt() })
            }
            nextLine = reader.readLine()
        }
        return Pair(numbers, bingos)
    }
}