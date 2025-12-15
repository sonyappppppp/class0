import java.util.*
import kotlin.math.sqrt
import kotlin.math.abs
import kotlin.math.pow

class Point5(val x: Double, val y: Double) {
    override fun toString(): String = "(${x.format(2)}, ${y.format(2)})"

    private fun Double.format(digits: Int) = "%.${digits}f".format(this)
}

class Triangle5(val p1: Point5, val p2: Point5, val p3: Point5) {
    fun isValid(): Boolean {
        val area = triangleArea5(p1, p2, p3)
        return area > 0.0001
    }
}

class Circle5(val center: Point5, val radius: Double) {
    override fun toString(): String =
        "Окружность с центром в ${center} и радиусом ${radius.format(2)}"
}

fun main() {
    println("Задача 5: Окружность в треугольнике ")

    val scanner = Scanner(System.`in`)

    val triangle = try {
        println("\nВведите координаты треугольника:")
        val p1 = readPoint5(scanner, "Вершина 1 (x y): ")
        val p2 = readPoint5(scanner, "Вершина 2 (x y): ")
        val p3 = readPoint5(scanner, "Вершина 3 (x y): ")

        val triangle = Triangle5(p1, p2, p3)
        if (!triangle.isValid()) {
            println("Ошибка: Точки лежат на одной прямой или совпадают!")
            return
        }
        triangle
    } catch (e: Exception) {
        println("Ошибка ввода: ${e.message}")
        return
    }

    val circle = inscribedCircle5(triangle)

    println("\n" + "=".repeat(50))
    println("РЕЗУЛЬТАТ:")
    println("Треугольник с вершинами:")
    println("  A${triangle.p1}, B${triangle.p2}, C${triangle.p3}")

    val a = distance5(triangle.p2, triangle.p3)
    val b = distance5(triangle.p1, triangle.p3)
    val c = distance5(triangle.p1, triangle.p2)

    println("\nДлины сторон:")
    println("  a (BC): ${a.format(2)}")
    println("  b (AC): ${b.format(2)}")
    println("  c (AB): ${c.format(2)}")

    val perimeter = a + b + c
    val area = triangleArea5(triangle.p1, triangle.p2, triangle.p3)

    println("\nПериметр: ${perimeter.format(2)}")
    println("Площадь: ${area.format(2)}")

    println("\nВписанная окружность:")
    println("  Центр: ${circle.center}")
    println("  Радиус: ${circle.radius.format(2)}")

    println("\nПроверка (расстояния от центра до сторон):")
    val dAB = distanceToLine5(circle.center, triangle.p1, triangle.p2)
    val dBC = distanceToLine5(circle.center, triangle.p2, triangle.p3)
    val dCA = distanceToLine5(circle.center, triangle.p3, triangle.p1)

    println("  До AB: ${dAB.format(4)} (отклонение: ${abs(dAB - circle.radius).format(4)})")
    println("  До BC: ${dBC.format(4)} (отклонение: ${abs(dBC - circle.radius).format(4)})")
    println("  До CA: ${dCA.format(4)} (отклонение: ${abs(dCA - circle.radius).format(4)})")
}

fun readPoint5(scanner: Scanner, prompt: String): Point5 {
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

    return Point5(x, y)
}

fun triangleArea5(a: Point5, b: Point5, c: Point5): Double {
    return abs(
        a.x * (b.y - c.y) +
                b.x * (c.y - a.y) +
                c.x * (a.y - b.y)
    ) / 2.0
}

fun distance5(p1: Point5, p2: Point5): Double {
    return sqrt((p2.x - p1.x).pow(2) + (p2.y - p1.y).pow(2))
}

fun distanceToLine5(point: Point5, lineP1: Point5, lineP2: Point5): Double {
    val numerator = abs(
        (lineP2.y - lineP1.y) * point.x -
                (lineP2.x - lineP1.x) * point.y +
                lineP2.x * lineP1.y -
                lineP2.y * lineP1.x
    )

    val denominator = sqrt(
        (lineP2.y - lineP1.y).pow(2) +
                (lineP2.x - lineP1.x).pow(2)
    )

    return numerator / denominator
}

fun inscribedCircle5(triangle: Triangle5): Circle5 {
    val (A, B, C) = listOf(triangle.p1, triangle.p2, triangle.p3)

    val a = distance5(B, C)
    val b = distance5(A, C)
    val c = distance5(A, B)

    val perimeter = a + b + c

    if (perimeter < 0.0001) {
        throw IllegalStateException("Треугольник вырожденный")
    }

    val Ix = (a * A.x + b * B.x + c * C.x) / perimeter
    val Iy = (a * A.y + b * B.y + c * C.y) / perimeter

    val area = triangleArea5(A, B, C)
    val radius = 2 * area / perimeter

    return Circle5(Point5(Ix, Iy), radius)
}

private fun Double.format(digits: Int): String = "%.${digits}f".format(this)