import java.math.BigInteger

fun main() {
    Day6()
}

class Day6 : Runner() {
    override fun a() {
        val reader = getReader()
        var ages = reader.readLine().split(",").map { it.toInt() }
        var prevValue = 0
        for (i in 1..80) {
            val newAges = mutableListOf<Int>()
            ages.forEach {
                if (it == 0) {
                    newAges.add(6)
                    newAges.add(8)
                } else {
                    newAges.add(it - 1)
                }
            }
            ages = newAges
        }
        print(ages.size)
    }

    override fun b() {
        val reader = getReader()
        var total = BigInteger.ZERO
        val totalAges = 256
        val cache = mutableMapOf<Int, BigInteger>()
        reader.readLine().split(",").forEach {
            val currentAge = it.toInt()
            val produces = produces(totalAges, currentAge, cache) + BigInteger.ONE
            total += produces
        }
        println(total.toString())
    }

    fun produces(agesLeft: Int, age: Int, cache: MutableMap<Int, BigInteger>): BigInteger {
        if (cache[agesLeft] != null && age == 8) {
            return cache[agesLeft]!!
        }
        var currentAge = age
        var produced = BigInteger.ZERO
        for (i in 1..agesLeft) {
            if (currentAge == 0) {
                produced += produces(agesLeft - i, 8, cache) + BigInteger.ONE
                currentAge = 6
            } else {
                currentAge--
            }
        }
        if (age == 8) {
            cache[agesLeft] = produced
        }
        return produced


    }
}
