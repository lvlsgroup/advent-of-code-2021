fun main() {
    Day9()
}

class Day9 : Runner() {
    override fun a() {
        val reader = getReader()
        var nextLine = reader.readLine()
        val matrix = mutableListOf<List<Int>>()
        while (nextLine != null) {
            val numbers = nextLine.toCharArray().map { it.toString().toInt() }
            matrix.add(numbers)
            nextLine = reader.readLine()
        }
        var sum = 0
        for (i in 0 until matrix.size) {
            val row = matrix[i]
            for (j in 0 until row.size) {
                if (isLowPoint(i, j, matrix)) {
                    val value = matrix[i][j]
                    sum += (1 + value)
                }
            }
        }
        print(sum, 526)
    }

    override fun b() {
        val reader = getReader()
        var nextLine = reader.readLine()
        val largestBasins = mutableListOf<Int>()
        val matrix = mutableListOf<List<Int>>()
        while (nextLine != null) {
            val numbers = nextLine.toCharArray().map { it.toString().toInt() }
            matrix.add(numbers)
            nextLine = reader.readLine()
        }
        for (i in 0 until matrix.size) {
            val row = matrix[i]
            for (j in 0 until row.size) {
                if (isLowPoint(i, j, matrix)) {
                    val cache = Array(matrix.size) { IntArray(matrix[0].size).map { -1 }.toIntArray() }
                    val basinSize = findBasin(i, j, matrix, cache)
                    if (largestBasins.size == 3) {
                        largestBasins.sort()
                        if (largestBasins[0] < basinSize) {
                            largestBasins.removeAt(0)
                            largestBasins.add(basinSize)
                        }
                    } else {
                        largestBasins.add(basinSize)
                    }
                }
            }
        }
        print(largestBasins[0] * largestBasins[1] * largestBasins[2], 1123524)

    }

    fun isLowPoint(i: Int, j: Int, matrix: List<List<Int>>): Boolean {
        val value = matrix[i][j]
        val left = if (i - 1 < 0) Int.MAX_VALUE else matrix[i - 1][j]
        val top = if (j - 1 < 0) Int.MAX_VALUE else matrix[i][j - 1]
        val bottom = if (j + 1 >= matrix[0].size) Int.MAX_VALUE else matrix[i][j + 1]
        val right = if (i + 1 >= matrix.size) Int.MAX_VALUE else matrix[i + 1][j]
        if (value < left && value < top && value < bottom && value < right) {
            return true
        }
        return false
    }

    fun findBasin(i: Int, j: Int, matrix: List<List<Int>>, cache: Array<IntArray>): Int {
        if (i < 0) {
            return 0
        }
        if (j < 0) {
            return 0
        }
        if (i >= matrix.size) {
            return 0
        }
        if (j >= matrix[0].size) {
            return 0
        }
        if (cache[i][j] != -1) {
            return 0
        }
        val value = matrix[i][j]
        if (value == 9) {
            return 0
        }
        cache[i][j] = value
        return 1 + findBasin(i - 1, j, matrix, cache) + findBasin(i + 1, j, matrix, cache) + findBasin(
            i,
            j - 1,
            matrix,
            cache
        ) + findBasin(i, j + 1, matrix, cache)
    }


}
