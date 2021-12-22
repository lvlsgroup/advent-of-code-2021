fun main() {
    Day17()
}

class Day17 : Runner() {

    override fun a() {

        var bestY = Int.MIN_VALUE
        val area = getArea()
        for (initX in 1..2000) {
            for (initY in 9..2000) {

                bestY = calculateBestY(initX, initY, bestY, area)

            }
        }
        print(bestY)
    }

    private fun calculateBestY(
        initX: Int,
        initY: Int,
        bestY: Int,
        area: Pair<Pair<Int, Int>, Pair<Int, Int>>
    ): Int {
        val x1 = area.first.first
        val x2 = area.first.second
        val y1 = area.second.first
        val y2 = area.second.second
        var volX = initX
        var volY = initY
        var curX = 0
        var curY = 0
        var highestY = 0
        while (true) {
            curX += volX
            curY += volY
            if (curX > x2) {
                return bestY
            }
            if (curY < y1) {
                return bestY
            }
            if (curX in x1 until x2 && curY in y1 until y2) {
                return Math.max(highestY, bestY)
            }
            if (highestY < bestY && volY <= 0) {
                return bestY
            }
            highestY = Math.max(curY, highestY)
            if (volX > 0) {
                volX--
            } else if (volX < 0) {
                volX++
            }
            volY--
        }
    }

    private fun calculateWithin(
        initX: Int,
        initY: Int,
        area: Pair<Pair<Int, Int>, Pair<Int, Int>>
    ): Int {
        val x1 = area.first.first
        val x2 = area.first.second
        val y1 = area.second.first
        val y2 = area.second.second
        var volX = initX
        var volY = initY
        var curX = 0
        var curY = 0
        while (true) {
            curX += volX
            curY += volY
            if (curX > x2) {
                return 0
            }
            if (curY < y1) {
                return 0
            }
            if (curX in x1 .. x2 && curY in y1 .. y2) {
                //println("$initX,$initY")
                return 1
            }
            if (volX <= 0 && curX < x1) {
                return 0
            }
            if (volX > 0) {
                volX--
            } else if (volX < 0) {
                volX++
            }
            volY--
        }
    }

    private fun getArea(): Pair<Pair<Int, Int>, Pair<Int, Int>> {
        val reader = getReader()
        val nextLine = reader.readLine()
        val split = nextLine.split(": ")[1].split(", ")
        val split2 = split[0].split("x=")[1].split("..")
        val xInterval = split2.map { it.toInt() }
        val yInterval = split[1].split("y=")[1].split("..").map { it.toInt() }
        return Pair(Pair(xInterval[0], xInterval[1]), Pair(yInterval[0], yInterval[1]))
    }

    override fun b() {
        var sum = 0
        val area = getArea()
        for (initX in 1..2000) {
            for (initY in -100000..20000) {
                sum += calculateWithin(initX, initY, area)
            }
        }
        print(sum)
    }


}


