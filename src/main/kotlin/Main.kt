import kotlin.math.exp

import ru.klimovitch.compmethods.*

fun square(x: Double) = x * x

fun main() {
    val delta = 1.0
    val equation = LinearEquation(c = 0.25, t = 10.0, a = -2.0, b = 8.0, tau = 0.001, h = 0.005)
            { x -> if (x <= 0) 1.0 else exp(-square(x / delta)) }

    firstExercise(
        SchemeFactory()
        .createScheme(SchemeType.ExplicitCorner, equation)
        .computeSolution(),
        0.0, 4.5, 9.0
    )
}

fun firstExercise(solution: Solution, vararg ts: Double) {
    ts.forEach {
        solution.apply {
            maxError(it)
            rootMeanSquare(it)
            variation(it)
        }
        println()
    }
    solution.plot(*ts)
}
