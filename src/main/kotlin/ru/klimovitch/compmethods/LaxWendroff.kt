package ru.klimovitch.compmethods

open class LaxWendroff(equation: LinearEquation) : Scheme(equation) {

    init {
        val rightColumn = equation.preciseColumn(equation.m)
        for (i in 1 .. equation.n)
            grid[i][equation.m] = rightColumn[i]
    }

    override fun computeSolution(): Solution {
        for (i in 1 .. equation.n) {
            val halfIntegersPoints = DoubleArray(equation.m)
            val r = equation.r
            for (j in startX until equation.m)
                halfIntegersPoints[j] = grid[i - 1][j] + halfIntegerAddition(i, j)
            for (j in startX + 1 until equation.m)
                grid[i][j] = grid[i - 1][j] - r * (halfIntegersPoints[j] - halfIntegersPoints[j - 1])
        }
        return Solution(grid, equation, solutionName)
    }

    protected open fun halfIntegerAddition(i: Int, j: Int) =
            (grid[i - 1][j + 1] - grid[i - 1][j]) * (1 - equation.r) / 2

    protected open val solutionName
        get() = "Решение схемой Лакса-Вендроффа"

    protected open val startX
        get() = 0
}
