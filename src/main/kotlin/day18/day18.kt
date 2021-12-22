import java.util.*

fun main() {
    Day18()
}

class Day18 : Runner() {

    override fun a() {

        val numbers = parseInput()
        var currentNumber = numbers.first()
        numbers.subList(1, numbers.size).forEach {
            currentNumber = currentNumber.add(it)
        }
        println(currentNumber.magnitude())
    }

    private fun parseInput(): List<SnailFish> {
        val list = mutableListOf<SnailFish>()
        val reader = getReader()
        var nextLine = reader.readLine()
        while (nextLine != null) {
            val currentSnailFishes = mutableMapOf<Int, SnailFish>()
            var deep = -1
            var currentSnailFish: SnailFish? = null
            nextLine.forEach {
                if (it == '[') {
                    deep++
                    val nextSnailFish = SnailFish()
                    if (currentSnailFish != null) {
                        if (currentSnailFish!!.first == null && currentSnailFish!!.firstValue == null) {
                            currentSnailFish!!.first = nextSnailFish
                        } else {
                            currentSnailFish!!.second = nextSnailFish
                        }
                        nextSnailFish.parent = currentSnailFish
                    }
                    currentSnailFish = nextSnailFish

                } else if (it == ']') {
                    deep--
                    if (currentSnailFish?.parent != null) {
                        currentSnailFish = currentSnailFish!!.parent
                    }
                } else if (it == ',') {
                } else {
                    if (currentSnailFish != null) {
                        if (currentSnailFish!!.first == null && currentSnailFish!!.firstValue == null) {
                            currentSnailFish!!.firstValue = it.toString().toLong()
                        } else {
                            currentSnailFish!!.secondValue = it.toString().toLong()
                        }
                    }
                }

            }
            list.add(currentSnailFish!!)
            nextLine = reader.readLine()
        }
        return list
    }

    override fun b() {

        val numbers = parseInput()
        var best = Long.MIN_VALUE
        for (i in 0 until numbers.size) {
            for (j in 0 until numbers.size) {
                if(i != j) {
                    val numbers = parseInput()
                    val first = numbers[i]
                    val second = numbers[j]
                    val magnitude = first.add(second).magnitude()
                    best = Math.max(magnitude, best)
                }
            }
        }
        println(best)

    }

    data class SnailFish(
        var id: String = UUID.randomUUID().toString(),
        var firstValue: Long? = null,
        var first: SnailFish? = null,
        var secondValue: Long? = null,
        var second: SnailFish? = null,
        var parent: SnailFish? = null
    ) {
        override fun toString(): String {
            val left = if (firstValue != null) firstValue.toString() else first?.toString()
            val right = if (secondValue != null) secondValue.toString() else second?.toString()
            return "[${left},${right}]"
        }

        override fun equals(other: Any?): Boolean {
            return other is SnailFish && other.id == id
        }

        fun add(other: SnailFish): SnailFish {
            val newSnailFish = SnailFish(first = this, second = other)
            this.parent = newSnailFish
            other.parent = newSnailFish
            while (newSnailFish.reduce()) {

            }
            return newSnailFish
        }

        fun reduce(): Boolean {
            if (countMaxDeep(1) > 4) {
                explode(1)
                return true
            }
            if (maxRegularNumberSize() > 9) {
                split()
                return true
            }
            return false
        }

        private fun explode(currentDeep: Long): Boolean {
            if (first != null) {
                if (first!!.explode(currentDeep + 1)) {
                    return true
                }
            }
            if (second != null) {
                if (second!!.explode(currentDeep + 1)) {
                    return true
                }
            }
            if (currentDeep > 4 && firstValue != null && secondValue != null) {
                parent!!.findLeftAndIncrease(this, firstValue!!)
                parent!!.findRightAndIncrease(this, secondValue!!)
                if (parent!!.first == this) {
                    parent!!.first = null
                    parent!!.firstValue = 0
                } else if (parent!!.second == this) {
                    parent!!.second = null
                    parent!!.secondValue = 0
                }

                return true
            }

            return false
        }

        private fun increaseLeft(value: Long, skipRight: Boolean): Boolean {
            if (!skipRight) {
                if (secondValue != null) {
                    secondValue = secondValue!! + value
                    return true
                }
                if (second != null) {
                    second!!.increaseLeft(value, false)
                    return true
                }
            }
            if (firstValue != null) {
                firstValue = firstValue!! + value
                return true
            }
            if (first != null) {
                first!!.increaseLeft(value, true)
                return true
            }
            if (parent != null) {
                return parent!!.increaseLeft(value, true)
            } else {
                return false
            }
        }

        private fun findLeftAndIncrease(skipSnailFish: SnailFish, value: Long): Boolean {
            if (second == skipSnailFish) {
                if (firstValue != null) {
                    firstValue = firstValue!! + value
                    return true
                }
                if (first != null) {
                    first!!.increaseRightUp(value)
                    return true
                }
            }
            if (parent == null) {
                return false
            }
            return parent!!.findLeftAndIncrease(this, value)
        }

        private fun findRightAndIncrease(skipSnailFish: SnailFish, value: Long): Boolean {
            if (first == skipSnailFish) {
                if (secondValue != null) {
                    secondValue = secondValue!! + value
                    return true
                }
                if (second != null) {
                    second!!.increaseLeftUp(value)
                    return true
                }
            }
            if (parent == null) {
                return false
            }
            return parent!!.findRightAndIncrease(this, value)
        }

        private fun increaseRightUp(value: Long) {
            if (secondValue != null) {
                secondValue = value + secondValue!!
                return
            }
            second?.increaseRightUp(value)
        }

        private fun increaseLeftUp(value: Long) {
            if (firstValue != null) {
                firstValue = value + firstValue!!
                return
            }
            first?.increaseLeftUp(value)
        }

        private fun increaseRight(value: Long, skipLeft: Boolean): Boolean {
            if (!skipLeft) {
                if (firstValue != null) {
                    firstValue = firstValue!! + value
                    return true
                }
                if (first != null) {
                    first!!.increaseRight(value, false)
                    return true
                }
            }
            if (secondValue != null) {
                secondValue = secondValue!! + value
                return true
            }
            if (second != null) {
                second!!.increaseRight(value, false)
                return true
            }
            if (parent != null) {
                return parent!!.increaseRight(value, true)
            } else {
                return false
            }
        }

        private fun split(): Boolean {
            if ((firstValue ?: 0) > 9) {
                first = SnailFish(
                    firstValue = Math.floor((firstValue!! / 2f).toDouble()).toLong(),
                    secondValue = Math.ceil((firstValue!! / 2f).toDouble()).toLong()
                )
                first!!.parent = this
                firstValue = null
                return true
            }
            if (first != null) {
                if (first!!.split()) {
                    return true
                }
            }
            if ((secondValue ?: 0) > 9) {
                second = SnailFish(
                    firstValue = Math.floor((secondValue!! / 2f).toDouble()).toLong(),
                    secondValue = Math.ceil((secondValue!! / 2f).toDouble()).toLong()
                )
                second!!.parent = this
                secondValue = null
                return true
            }
            if (second != null) {
                if (second!!.split()) {
                    return true
                }
            }
            return false
        }

        fun countMaxDeep(currentDeep: Long): Long {
            val firstDeep = if (first != null) {
                first!!.countMaxDeep(currentDeep + 1)
            } else {
                currentDeep
            }
            val secondDeep = if (second != null) {
                second!!.countMaxDeep(currentDeep + 1)
            } else {
                currentDeep
            }
            return Math.max(firstDeep, secondDeep)
        }

        private fun maxRegularNumberSize(): Long {
            val valuesMax = Math.max(firstValue ?: 0, secondValue ?: 0)
            val deepValuesMax = Math.max(first?.maxRegularNumberSize() ?: 0, second?.maxRegularNumberSize() ?: 0)
            return Math.max(valuesMax, deepValuesMax)
        }

        fun magnitude(): Long {
            val left = if (firstValue != null) {
                3 * firstValue!!
            } else {
                3 * first!!.magnitude()
            }
            val right = if (secondValue != null) {
                2 * secondValue!!
            } else {
                2 * second!!.magnitude()
            }
            return left + right
        }
    }


}


