import kotlin.math.min

fun main() {
    Day15()
}

class Day15 : Runner() {
    var goalX: Int = 0
    var goalY: Int = 0
    override fun a() {
        val matrix = parseInput()
        goalX = matrix.size - 1
        goalY = matrix[0].size - 1
        val sum = SumHolder()
        val cache = Array(matrix.size) { IntArray(matrix[0].size).map { Int.MAX_VALUE }.toIntArray() }

        findShortestPath(matrix, 0, 0, 0, sum, cache)
        //print(sum.sum, 498)
    }

    private fun findShortestPath(
        matrix: MutableList<MutableList<Int>>,
        x: Int,
        y: Int,
        steps: Int,
        sum: SumHolder,
        cache: Array<IntArray>
    ): Int {
        var newSteps = steps
        if (!isValid(x, y, matrix)) {
            return Int.MAX_VALUE
        }
        if (!(x == 0 && y == 0)) {
            newSteps += matrix[x][y]
        }
        if (newSteps < cache[x][y]) {
            cache[x][y] = newSteps
        } else {
            return Int.MAX_VALUE
        }
        if (newSteps > sum.sum) {
            return Int.MAX_VALUE
        }
        if (x == goalX && y == goalY) {
            if (sum.sum > newSteps) {
                sum.sum = newSteps
            }
            return newSteps
        }
        val bottomStep = findShortestPath(matrix, x, y + 1, newSteps, sum, cache)
        val rightStep = findShortestPath(matrix, x + 1, y, newSteps, sum, cache)
        val leftStep = findShortestPath(matrix, x - 1, y, newSteps, sum, cache)
        val topStep = findShortestPath(matrix, x, y - 1, newSteps, sum, cache)
        return min(min(min(leftStep, topStep), bottomStep), rightStep)
    }

    private fun parseInput(times: Int = 1): MutableList<MutableList<Int>> {
        val reader = getReader()
        var nextLine = reader.readLine()
        val matrix = mutableListOf<MutableList<Int>>()
        while (nextLine != null) {
            val numbers = nextLine.toCharArray().map { it.toString().toInt() }
            val numberArray = mutableListOf<Int>()
            for (i in 1..times) {
                numberArray.addAll(numbers.map {
                    var newInt = it + (i - 1)
                    if (newInt > 9) {
                        newInt -= 9
                    }
                    newInt
                })
            }
            matrix.add(numberArray.toMutableList())
            nextLine = reader.readLine()
        }

        val outputMatrix = mutableListOf<MutableList<Int>>()
        for (i in 1..times) {
            outputMatrix.addAll(matrix.map {
                it.map {
                    var newInt = (it + (i - 1))
                    if (newInt > 9) {
                        newInt -= 9
                    }
                    newInt
                }.toMutableList()
            }.toMutableList())
        }
        return outputMatrix
    }

    override fun b() {
        val matrix = parseInput(5)

        goalX = matrix.size - 1
        goalY = matrix[0].size - 1
        val sum = SumHolder()
        val cache = Array(matrix.size) { IntArray(matrix[0].size).map { Int.MAX_VALUE }.toIntArray() }

        findShortestPath(matrix, 0, 0, 0, sum, cache)
        kotlin.io.print(sum.sum)
    }

    fun isValid(i: Int, j: Int, matrix: List<List<Int>>): Boolean {
        if (i < 0) {
            return false
        }
        if (j < 0) {
            return false
        }
        if (i >= matrix.size) {
            return false
        }
        if (j >= matrix[0].size) {
            return false
        }

        return true
    }

    class SumHolder {
        var sum: Int = Int.MAX_VALUE
    }
}
