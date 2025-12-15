import java.util.*
import kotlin.math.sqrt

class Point2(val x: Double, val y: Double) {
    fun distanceTo(other: Point2): Double {
        val dx = x - other.x
        val dy = y - other.y
        return sqrt(dx * dx + dy * dy)
    }

    override fun toString(): String = "(${x.format(2)}, ${y.format(2)})"

    private fun Double.format(digits: Int) = "%.${digits}f".format(this)
}

fun main() {
    println(" Задача 2: Расстояние между точками ")


    val scanner = Scanner(System.`in`)

    val points = mutableListOf<Point2>()

    for (i in 1..2) {
        try {
            println("\nТочка $i:")
            val point = readPoint2(scanner)
            points.add(point)
        } catch (e: Exception) {
            println("Ошибка: ${e.message}")
            println("Попробуйте еще раз")
            return
        }
    }

    val distance = points[0].distanceTo(points[1])

    println("\n" + "=".repeat(50))
    println("РЕЗУЛЬТАТ:")
    println("Точка A: ${points[0]}")
    println("Точка B: ${points[1]}")
    println("\nРасстояние между точками: ${distance.format(2)} единиц")
    println("\nФормула: √[(x₂ - x₁)² + (y₂ - y₁)²]")
    println("Расчет: √[(${points[1].x} - ${points[0].x})² + (${points[1].y} - ${points[0].y})²] = $distance")
}

fun readPoint2(scanner: Scanner): Point2 {
    print("Введите координаты (x y): ")
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
        throw IllegalArgumentException("Неверный формат чисел. Используйте точку как разделитель")
    }

    return Point2(x, y)
}

private fun Double.format(digits: Int): String = "%.${digits}f".format(this)