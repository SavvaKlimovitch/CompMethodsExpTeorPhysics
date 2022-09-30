package ru.klimovitch.compmethods

import jetbrains.letsPlot.geom.geomLine
import jetbrains.letsPlot.label.ggtitle
import jetbrains.letsPlot.letsPlot
import jetbrains.letsPlot.scale.guideLegend

import kotlin.math.abs
import kotlin.math.sqrt

class Solution(private val grid: Array<DoubleArray>,
               private val equation: LinearEquation,
               private val title: String) {

    private val colors = arrayOf("red", "green", "blue", "yellow", "pink")

    fun plot(vararg ts: Double = DoubleArray(3) { i -> equation.t * i / 2 }) {

        val xs = DoubleArray(equation.m + 1)
        for (j in 0 .. equation.m) {
            xs[j] = equation.getX(j)
        }

        val pairs: Array<Pair<String, DoubleArray>> = Array(ts.size + 1) { "x" to xs }
        ts.forEachIndexed {
            i, t -> pairs[i + 1] = "u$i" to grid[timeIndex(t)]
        }

        val data: Map<String, DoubleArray> = mapOf(*pairs)

        var plot = letsPlot(data) + ggtitle(title)
        ts.forEachIndexed {
            i, _ -> plot += geomLine(color = colors[i % colors.size], size = 0.7) { x = "x"; y = "u$i" }
        }
        plot.show()
    }

    private fun timeIndex(t: Double): Int = (equation.n * t / equation.t).toInt()

    operator fun minus(solution: Solution): Solution {
        require(equation === solution.equation)
        val grid = equation.emptyGrid()
        for (i in grid.indices)
            for (j in grid[0].indices)
                grid[i][j] = this.grid[i][j] - solution.grid[i][j]
        return Solution(grid, equation,
                "Разность (${title.lowercase()}, ${solution.title.lowercase()})")
    }

    fun maxError(t: Double) = maxError(timeIndex(t))
        .also { println("Max error (t = $t): $it") }

    fun maxError(t: Int): Double {
        val isoChron = grid[t]
        val preciseIsoChron = equation.preciseRow(t)
        var error = 0.0
        for (j in isoChron.indices) {
            val currentError = abs(isoChron[j] - preciseIsoChron[j])
            if (currentError > error) error = currentError
        }
        return error
    }

    fun rootMeanSquare(t: Double) = rootMeanSquare(timeIndex(t))
        .also { println("Root mean square (t = $t): $it") }

    fun rootMeanSquare(t: Int): Double {
        val isoChron = grid[t]
        val preciseIsoChron = equation.preciseRow(t)
        val quantity = equation.m + 1
        var sum = 0.0
        for (j in 0 until quantity) {
            sum += square(isoChron[j] - preciseIsoChron[j])
        }
        return sqrt(sum / quantity)
    }

    private fun square(x: Double) = x * x

    fun variation(t: Double) = variation(timeIndex(t))
        .also { println("Variation (t = $t): $it") }

    fun variation(t: Int): Double {
        val isoChron = grid[t]
        var sum = 0.0
        for (j in 0 until equation.m)
            sum += abs(isoChron[j + 1] - isoChron[j])
        return sum
    }
}
