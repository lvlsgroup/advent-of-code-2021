abstract class Runner {
    init {
        this.a()
        this.b()
    }

    abstract fun b()

    abstract fun a()
    fun <T> print(result: T, correct: T? = null) {
        if (correct != null && result != correct) {
            throw Error("Result: $result, correct: $correct")
        }
        println(result)
    }
}