package ru.klimovitch.compmethods

import kotlin.math.max
import kotlin.math.min

class Tvd(equation: LinearEquation) : LaxWendroff(equation) {

    init {
        val secondLeftBorder = equation.preciseColumn(1)
        for (i in 1 .. equation.n)
            grid[i][1] = secondLeftBorder[i]
    }

    override fun halfIntegerAddition(i: Int, j: Int): Double {
        val q: Double = ((grid[i - 1][j] - grid[i - 1][j - 1]) / (grid[i - 1][j + 1] - grid[i - 1][j]))
            .run { if (isNaN()) 1.0 else this }
        return super.halfIntegerAddition(i, j) * limiter(q)
    }

    private fun limiter(q: Double) = max(0.0, min(1.0, q))

    override val solutionName
        get() = "Решение схемой TVD"

    override val startX
        get() = 1
}
