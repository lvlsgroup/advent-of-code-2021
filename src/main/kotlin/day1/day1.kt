fun main() {
    val day = Day1()
    day.a()
    day.b()
}

class Day1 {


    fun a() {
        val reader = getReader("day1")
        var currentNumber: Int = reader.readInt()
        var inreases = 0
        var lastNumber = Integer.MAX_VALUE
        while (currentNumber != -1) {
            if (currentNumber > lastNumber) {
                inreases++
            }
            lastNumber = currentNumber
            currentNumber = reader.readInt()
        }
        println(inreases)
    }

    fun b() {
        val reader = getReader("day1")
        val ints = mutableListOf<Int>()
        var currentNumber: Int = reader.readInt()
        ints.add(currentNumber)
        var inreases = 0
        while (currentNumber != -1) {
            if (ints.size > 3 && ints.takeLast(3).sum() > (ints.takeLast(4).sum() - ints.takeLast(1).sum())) {
                inreases++
            }
            currentNumber = reader.readInt()
            ints.add(currentNumber)
        }
        println(inreases)
    }
}