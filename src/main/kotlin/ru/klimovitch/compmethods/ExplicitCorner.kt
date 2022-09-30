package ru.klimovitch.compmethods

class ExplicitCorner(equation: LinearEquation) : Scheme(equation) {

    override fun computeSolution(): Solution {
        for (i in 1 until grid.size)
            for (j in 1 until grid[0].size) {
                val r = equation.r
                grid[i][j] = (1 - r) * grid[i - 1][j] + r * grid[i - 1][j - 1]
            }
        return Solution(grid, equation, "Решение явным уголком")
    }
}
