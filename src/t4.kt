import java.util.*
import kotlin.math.sqrt
import kotlin.math.pow
import kotlin.math.abs

class Point4(val x: Double, val y: Double) {
    override fun toString(): String = "(${x.format(2)}, ${y.format(2)})"

    private fun Double.format(digits: Int) = "%.${digits}f".format(this)
}

class Triangle4(val p1: Point4, val p2: Point4, val p3: Point4) {
    fun isValid(): Boolean {
        val area = triangleArea4(p1, p2, p3)
        return area > 0.0001
    }
}

class Circle4(val center: Point4, val radius: Double) {
    override fun toString(): String =
        "Окружность с центром в ${center} и радиусом ${radius.format(2)}"
}

fun main() {
    println(" Задача 4: Треугольник в окружности ")

    val scanner = Scanner(System.`in`)

    val triangle = try {
        println("\nВведите координаты треугольника:")
        val p1 = readPoint4(scanner, "Вершина 1 (x y): ")
        val p2 = readPoint4(scanner, "Вершина 2 (x y): ")
        val p3 = readPoint4(scanner, "Вершина 3 (x y): ")

        val triangle = Triangle4(p1, p2, p3)
        if (!triangle.isValid()) {
            println("Ошибка: Точки лежат на одной прямой или совпадают!")
            return
        }
        triangle
    } catch (e: Exception) {
        println("Ошибка ввода: ${e.message}")
        return
    }

    val circle = circumscribedCircle4(triangle)

    println("\n" + "=".repeat(50))
    println("РЕЗУЛЬТАТ:")
    println("Треугольник с вершинами:")
    println("  A${triangle.p1}, B${triangle.p2}, C${triangle.p3}")
    println("\nОписанная окружность:")
    println("  Центр: ${circle.center}")
    println("  Радиус: ${circle.radius.format(2)}")

    println("\nПроверка (расстояния от центра до вершин):")
    val d1 = distance4(circle.center, triangle.p1)
    val d2 = distance4(circle.center, triangle.p2)
    val d3 = distance4(circle.center, triangle.p3)
    println("  До A: ${d1.format(4)} (отклонение: ${abs(d1 - circle.radius).format(4)})")
    println("  До B: ${d2.format(4)} (отклонение: ${abs(d2 - circle.radius).format(4)})")
    println("  До C: ${d3.format(4)} (отклонение: ${abs(d3 - circle.radius).format(4)})")
}

fun readPoint4(scanner: Scanner, prompt: String): Point4 {
    print(prompt)
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

    return Point4(x, y)
}

fun triangleArea4(a: Point4, b: Point4, c: Point4): Double {
    return abs(
        a.x * (b.y - c.y) +
                b.x * (c.y - a.y) +
                c.x * (a.y - b.y)
    ) / 2.0
}

fun distance4(p1: Point4, p2: Point4): Double {
    return sqrt((p2.x - p1.x).pow(2) + (p2.y - p1.y).pow(2))
}

fun circumscribedCircle4(triangle: Triangle4): Circle4 {
    val (A, B, C) = listOf(triangle.p1, triangle.p2, triangle.p3)

    val D = 2 * (A.x * (B.y - C.y) + B.x * (C.y - A.y) + C.x * (A.y - B.y))

    if (abs(D) < 0.0001) {
        throw IllegalStateException("Треугольник вырожденный")
    }

    val Ux = ((A.x.pow(2) + A.y.pow(2)) * (B.y - C.y) +
            (B.x.pow(2) + B.y.pow(2)) * (C.y - A.y) +
            (C.x.pow(2) + C.y.pow(2)) * (A.y - B.y)) / D

    val Uy = ((A.x.pow(2) + A.y.pow(2)) * (C.x - B.x) +
            (B.x.pow(2) + B.y.pow(2)) * (A.x - C.x) +
            (C.x.pow(2) + C.y.pow(2)) * (B.x - A.x)) / D

    val center = Point4(Ux, Uy)
    val radius = distance4(center, A)

    return Circle4(center, radius)
}

private fun Double.format(digits: Int): String = "%.${digits}f".format(this)