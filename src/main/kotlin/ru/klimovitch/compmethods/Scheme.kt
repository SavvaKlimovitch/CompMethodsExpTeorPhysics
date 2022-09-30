package ru.klimovitch.compmethods

abstract class Scheme(protected val equation: LinearEquation) {

    protected val grid = equation.emptyGrid()

    init {
        require(equation.r >= 0 || equation.r <= 1.0)

        val leftColumn = equation.preciseColumn(0)
        for (i in 1 until grid.size)
            grid[i][0] = leftColumn[i]

        grid[0] = equation.preciseRow(0)
    }

    abstract fun computeSolution(): Solution
}
