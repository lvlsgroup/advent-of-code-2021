fun main() {
    Day11()
}

class Day11 : Runner() {
    override fun a() {
        val matrix = parseInput()
        val sum = SumHolder()
        for (i in 1..100) {
            oneStep(sum, matrix)
        }
        print(sum.sum)
    }

    /**
     * Take one step and return true if all flashes
     */
    private fun oneStep(sum: SumHolder, matrix: MutableList<MutableList<Int>>): Boolean {
        loopWithFunction(matrix) { _, i, j ->
            matrix[i][j]++
        }
        var finished = false
        while (!finished) {
            var hasOverNine = false
            loopWithFunction(matrix) { value, i, j ->
                if (value > 9) {
                    hasOverNine = true
                    matrix[i][j] = 0
                    sum.sum++
                    for (ii in i - 1..i + 1) {
                        for (jj in j - 1..j + 1) {
                            if (isValid(ii, jj, matrix)) {
                                matrix[ii][jj]++
                            }
                        }
                    }

                }
            }
            if (!hasOverNine) {
                finished = true
            }
        }

        var allFlashed = true
        loopWithFunction(matrix) { value, i, j ->
            if (value != 0) {
                allFlashed = false
            }
        }
        return allFlashed
    }

    private fun parseInput(): MutableList<MutableList<Int>> {
        val reader = getReader()
        var nextLine = reader.readLine()
        val matrix = mutableListOf<MutableList<Int>>()
        while (nextLine != null) {
            val numbers = nextLine.toCharArray().map { it.toString().toInt() }
            matrix.add(numbers.toMutableList())
            nextLine = reader.readLine()
        }
        return matrix
    }

    override fun b() {
        val matrix = parseInput()
        val sum = SumHolder()
        var step = 1
        while (true) {
            if (oneStep(sum, matrix)) {
                print(step)
                return
            }
            step++
        }
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
        if (matrix[i][j] == 0) {
            return false
        }

        return true
    }

    private fun loopWithFunction(matrix: List<List<Int>>, function: (Int, Int, Int) -> Any) {
        for (i in 0 until matrix.size) {
            val row = matrix[i]
            for (j in 0 until row.size) {
                function(matrix[i][j], i, j)
            }
        }
    }

    class SumHolder {
        var sum: Int = 0
    }
}
