import java.util.*
import kotlin.math.abs

class Point1(val x: Double, val y: Double) {
    override fun toString(): String = "(${x.format(2)}, ${y.format(2)})"

    private fun Double.format(digits: Int) = "%.${digits}f".format(this)
}

class Triangle1(val p1: Point1, val p2: Point1, val p3: Point1) {
    fun isValid(): Boolean {
        val area = 0.5 * abs(
            p1.x * (p2.y - p3.y) +
                    p2.x * (p3.y - p1.y) +
                    p3.x * (p1.y - p2.y)
        )
        return area > 0.0001
    }
}

fun main() {
    println(" Задача 1: Точка и треугольник ")


    val scanner = Scanner(System.`in`)

    val triangle = try {
        println("\nВведите координаты треугольника:")
        val p1 = readPoint1(scanner, "Вершина 1 (x y): ")
        val p2 = readPoint1(scanner, "Вершина 2 (x y): ")
        val p3 = readPoint1(scanner, "Вершина 3 (x y): ")

        val triangle = Triangle1(p1, p2, p3)
        if (!triangle.isValid()) {
            println("Ошибка: Точки лежат на одной прямой или совпадают!")
            return
        }
        triangle
    } catch (e: Exception) {
        println("Ошибка ввода: ${e.message}")
        return
    }

    println("\nВведите координаты точки для проверки:")
    val testPoint = try {
        readPoint1(scanner, "Точка (x y): ")
    } catch (e: Exception) {
        println("Ошибка ввода: ${e.message}")
        return
    }

    val result = isPointInTriangle1(testPoint, triangle)

    println("\n" + "=".repeat(50))
    println("РЕЗУЛЬТАТ:")
    println("Треугольник с вершинами:")
    println("  A${triangle.p1}, B${triangle.p2}, C${triangle.p3}")
    println("Точка: ${testPoint}")
    println("\nТочка находится: ${if (result) "ВНУТРИ треугольника" else "ВНЕ треугольника"}")
}

fun readPoint1(scanner: Scanner, prompt: String): Point1 {
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

    return Point1(x, y)
}

fun isPointInTriangle1(p: Point1, t: Triangle1): Boolean {
    val areaABC = triangleArea1(t.p1, t.p2, t.p3)

    val areaPBC = triangleArea1(p, t.p2, t.p3)
    val areaAPC = triangleArea1(t.p1, p, t.p3)
    val areaABP = triangleArea1(t.p1, t.p2, p)

    return abs(areaABC - (areaPBC + areaAPC + areaABP)) < 0.0001
}

fun triangleArea1(a: Point1, b: Point1, c: Point1): Double {
    return abs(
        a.x * (b.y - c.y) +
                b.x * (c.y - a.y) +
                c.x * (a.y - b.y)
    ) / 2.0
}