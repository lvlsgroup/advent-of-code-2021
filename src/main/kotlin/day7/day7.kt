import kotlin.math.abs
import kotlin.math.min

fun main() {
    Day7()
}

class Day7 : Runner() {
    override fun a() {
        val reader = getReader()
        val numbers = reader.readLine().split(",").map { it.toLong() }
        val maxNumber = numbers.maxOf { it }
        val minNumber = numbers.minOf { it }
        var minSteps = Long.MAX_VALUE
        for (i in minNumber..maxNumber) {
            var curSteps = 0L
            numbers.forEach {
                curSteps += abs(it - i)
            }
            minSteps = min(minSteps, curSteps)

        }
        print(minSteps, 348996)

    }

    override fun b() {
        val reader = getReader()
        val numbers = reader.readLine().split(",").map { it.toLong() }
        val maxNumber = numbers.maxOf { it }
        val minNumber = numbers.minOf { it }
        var minSteps = Long.MAX_VALUE
        for (i in minNumber..maxNumber) {
            val curSteps = countScore(i, numbers, minSteps)
            minSteps = min(minSteps, curSteps)

        }
        print(minSteps, 98231647)
    }

    private fun countScore(i: Long, numbers: List<Long>, minSteps: Long): Long {
        var curSteps = 0L
        numbers.forEach {
            val distance = abs(it - i)
            for (j in 1..distance) {
                curSteps += j
                if (curSteps > minSteps) {
                    return Long.MAX_VALUE
                }
            }
        }
        return curSteps
    }

}
