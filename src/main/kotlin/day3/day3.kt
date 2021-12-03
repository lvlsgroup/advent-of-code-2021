fun main() {
    Day3()
}

class Day3 : Runner() {
    override fun a() {
        val reader = getReader()
        val matrix = mutableListOf<List<Int>>()
        var line = reader.readLine()
        while (line != null) {
            matrix.add(line.toList().map { it - '0' })
            line = reader.readLn()
        }
        var bineryGamma = ""
        var bineryEpsilon = ""
        for (i in 0 until matrix[0].size) {
            var count0 = 0
            var count1 = 0

            for (j in 0 until matrix.size) {
                if (matrix[j][i] == 0) {
                    count0++
                } else {
                    count1++
                }
            }
            if (count0 > count1) {
                bineryGamma += "0"
                bineryEpsilon += "1"
            } else {
                bineryGamma += "1"
                bineryEpsilon += "0"
            }
        }
        print(bineryGamma.toInt(2) * bineryEpsilon.toInt(2), 1131506)
    }

    override fun b() {
        val reader = getReader()
        val matrix = mutableListOf<List<Int>>()
        var line = reader.readLine()
        while (line != null) {
            matrix.add(line.toList().map { it - '0' })
            line = reader.readLn()
        }

        val binaryOxygenRate = findRate(matrix, false)
        val binaryCO2Rate = findRate(matrix, true)

        print(binaryOxygenRate.toInt(2) * binaryCO2Rate.toInt(2), 7863147)
    }

    private fun findRate(matrix: List<List<Int>>, negativeFinder: Boolean): String {
        var rateMatrix = matrix.toMutableList()
        var j = 0
        var binary = ""
        while (rateMatrix.size > 1) {
            var count0 = 0
            var count1 = 0
            for (i in 0 until rateMatrix.size) {
                if (rateMatrix[i][j] == 0) {
                    count0++
                } else {
                    count1++
                }
            }
            val mostCommon0: Boolean
            if (count0 == count1) {
                mostCommon0 = !negativeFinder
                binary += if (negativeFinder) "0" else "1"
            } else if (count0 > count1) {
                mostCommon0 = true
                binary += if (negativeFinder) "1" else "0"
            } else {
                mostCommon0 = false
                binary += if (negativeFinder) "0" else "1"
            }
            val newList = mutableListOf<List<Int>>()
            for (i in 0 until rateMatrix.size) {
                if (negativeFinder) {
                    if (rateMatrix[i][j] == 0 && !mostCommon0) {
                        newList.add(rateMatrix[i])
                    }
                    if (rateMatrix[i][j] == 1 && mostCommon0) {
                        newList.add(rateMatrix[i])
                    }
                } else {
                    if (rateMatrix[i][j] == 0 && mostCommon0) {
                        newList.add(rateMatrix[i])
                    }
                    if (rateMatrix[i][j] == 1 && !mostCommon0) {
                        newList.add(rateMatrix[i])
                    }
                }
            }
            rateMatrix = newList
            j++
        }
        return binary + (if (rateMatrix.size == 1 && j < rateMatrix[0].size) rateMatrix[0].subList(
            j,
            rateMatrix[0].size
        ).joinToString("") else "")
    }
}