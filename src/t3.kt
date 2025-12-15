import java.util.*
import kotlin.math.sqrt
import kotlin.math.abs

class Point3(val x: Double, val y: Double) {
    fun distanceTo(other: Point3): Double {
        val dx = x - other.x
        val dy = y - other.y
        return sqrt(dx * dx + dy * dy)
    }

    override fun toString(): String = "(${x.format(2)}, ${y.format(2)})"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Point3) return false
        return abs(x - other.x) < 0.0001 && abs(y - other.y) < 0.0001
    }

    override fun hashCode(): Int = x.hashCode() * 31 + y.hashCode()

    private fun Double.format(digits: Int) = "%.${digits}f".format(this)
}

data class DistanceInfo3(val p1: Point3, val p2: Point3, val distance: Double)

fun main() {
    println(" Задача 3: Наибольшее и наименьшее расстояние ")

    val scanner = Scanner(System.`in`)

    val n = try {
        print("\nВведите количество точек (больше 2): ")
        val input = scanner.nextLine().trim().toInt()
        if (input <= 2) {
            println("Ошибка: количество точек должно быть больше 2!")
            return
        }
        input
    } catch (e: Exception) {
        println("Ошибка: введите целое число")
        return
    }

    val points = mutableListOf<Point3>()

    println("\nВведите координаты точек:")
    for (i in 1..n) {
        while (true) {
            try {
                print("Точка $i (x y): ")
                val point = readPoint3(scanner)

                if (points.any { it == point }) {
                    println("Ошибка: точка совпадает с уже введенной!")
                    continue
                }

                points.add(point)
                break
            } catch (e: Exception) {
                println("Ошибка: ${e.message}. Попробуйте еще раз")
            }
        }
    }

    val result = findMinMaxDistances3(points)

    println("\n" + "=".repeat(50))
    println("РЕЗУЛЬТАТ:")
    println("Всего точек: ${points.size}")
    println("\nМинимальное расстояние: ${result.first.distance.format(4)}")
    println("Между точками: ${result.first.p1} и ${result.first.p2}")
    println("\nМаксимальное расстояние: ${result.second.distance.format(4)}")
    println("Между точками: ${result.second.p1} и ${result.second.p2}")

    println("\nВсе расстояния между точками:")
    for (i in points.indices) {
        for (j in i + 1 until points.size) {
            val dist = points[i].distanceTo(points[j])
            println("  ${points[i]} - ${points[j]}: ${dist.format(4)}")
        }
    }
}

fun readPoint3(scanner: Scanner): Point3 {
    val input = scanner.nextLine().trim()

    if (input.isEmpty()) {
        throw IllegalArgumentException("Ввод не может быть пустым")
    }

    val parts = input.split("\\s+".toRegex())
    if (parts.size != 2) {
        throw IllegalArgumentException("Введите ДВА числа через пробел")
    }

    val x = parts[0].toDoubleOrNull()
    val y = parts[1].toDoubleOrNull()

    if (x == null || y == null) {
        throw IllegalArgumentException("Неверный формат чисел")
    }

    return Point3(x, y)
}

fun findMinMaxDistances3(points: List<Point3>): Pair<DistanceInfo3, DistanceInfo3> {
    var minDistance = Double.MAX_VALUE
    var maxDistance = 0.0
    var minInfo = DistanceInfo3(points[0], points[1], 0.0)
    var maxInfo = DistanceInfo3(points[0], points[1], 0.0)

    for (i in points.indices) {
        for (j in i + 1 until points.size) {
            val distance = points[i].distanceTo(points[j])

            if (distance < minDistance) {
                minDistance = distance
                minInfo = DistanceInfo3(points[i], points[j], distance)
            }

            if (distance > maxDistance) {
                maxDistance = distance
                maxInfo = DistanceInfo3(points[i], points[j], distance)
            }
        }
    }

    return Pair(minInfo, maxInfo)
}

private fun Double.format(digits: Int): String = "%.${digits}f".format(this)