fun main() {
    val day = Day1()
    day.a()
    day.b()
}

class Day1 {


    fun a() {
        val reader = getReader(this.javaClass.name)
        var currentNumber = reader.readInt()
        var inreases = 0
        var lastNumber = Integer.MAX_VALUE
        while (currentNumber != null) {
            if (currentNumber > lastNumber) {
                inreases++
            }
            lastNumber = currentNumber
            currentNumber = reader.readInt()
        }
        println(inreases)
    }

    fun b() {
        val reader = getReader(this.javaClass.name)
        val ints = mutableListOf<Int>()
        var currentNumber = reader.readInt()
        currentNumber?.let { ints.add(it) }
        var inreases = 0
        while (currentNumber != null) {
            if (ints.size > 3 && ints.takeLast(3).sum() > (ints.takeLast(4).sum() - ints.takeLast(1).sum())) {
                inreases++
            }
            currentNumber = reader.readInt()
            currentNumber?.let { ints.add(it) }
        }
        println(inreases)
    }
}