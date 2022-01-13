fun main() {
    Day22()
}

class Day22 : Runner() {

    override fun a() {
        val reader = getReader()
        var nextLine = reader.readLine()
        val matrix = mutableMapOf<Int, MutableMap<Int, MutableMap<Int, Boolean>>>()
        while (nextLine != null) {
            val split = nextLine.split(" ")
            val action = split[0]
            val coordinates = split[1].split(",")
            val x1 = coordinates[0].split("..")[0].split("=")[1].toInt()
            val x2 = coordinates[0].split("..")[1].toInt()
            val y1 = coordinates[1].split("..")[0].split("=")[1].toInt()
            val y2 = coordinates[1].split("..")[1].toInt()
            val z1 = coordinates[2].split("..")[0].split("=")[1].toInt()
            val z2 = coordinates[2].split("..")[1].toInt()
            if (x2 >= -50 && x1 <= 50 && y2 >= -50 && y1 <= 50 && z2 >= -50 && z1 <= 50) {
                for (x in x1..x2) {
                    if (x >= -50 && x <= 50) {
                        for (y in y1..y2) {
                            if (y >= -50 && y <= 50) {
                                for (z in z1..z2) {
                                    if (z >= -50 && z <= 50) {
                                        if (action == "on") {
                                            val ys = matrix[x] ?: mutableMapOf()
                                            val zs = ys[y] ?: mutableMapOf()
                                            zs.put(z, true)
                                            matrix.put(x, ys)
                                            matrix[x]?.put(y, zs)
                                        } else {
                                            val ys = matrix[x]
                                            val zs = ys?.get(y)
                                            zs?.remove(z)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            nextLine = reader.readLine()
        }

        var counter = 0
        for (x in -50..50) {
            for (y in -50..50) {
                for (z in -50..50) {
                    if (matrix.get(x)?.get(y)?.get(z) == true) {
                        counter++
                    }
                }
            }
        }
        print(counter)
    }

    override fun b() {
        val reader = getReader()

    }


}

