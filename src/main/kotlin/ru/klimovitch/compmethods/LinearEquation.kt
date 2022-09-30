package ru.klimovitch.compmethods

class LinearEquation(val c: Double,
                     val t: Double,
                     val a: Double, val b: Double,
                     val n: Int,
                     val m: Int,
                     val initialFunc: (Double) -> Double) {

    val tau: Double
        get() = t / n

    val h: Double
        get() = (b - a) / m

    val r: Double
        get() = c * tau / h

    constructor(c: Double, t: Double, a: Double, b: Double,
                tau: Double, h: Double,
                initialFunc: (Double) -> Double
    ) : this(c, t, a, b,
             (t / tau).toInt(),
             ((b - a) / h).toInt(),
             initialFunc)

    fun getX(index: Int) = a + index * h

    fun preciseRow(index: Int) =
            DoubleArray(m + 1) { j -> preciseValue(coordinateIndex = j, index) }

    fun preciseColumn(index: Int) =
            DoubleArray(n + 1) { i -> preciseValue(index, timeIndex = i) }

    private fun preciseValue(coordinateIndex: Int, timeIndex: Int) =
            preciseValue(a + coordinateIndex * h, timeIndex * tau)

    private fun preciseValue(coordinate: Double, time: Double) = initialFunc(coordinate - c * time)

    fun preciseSolution(): Solution {
        val grid = emptyGrid()
        for (i in grid.indices)
            for (j in grid[0].indices)
                grid[i][j] = preciseValue(timeIndex = i, coordinateIndex = j)
        return Solution(grid, this, "Точное решение")
    }

    fun emptyGrid(): Array<DoubleArray> = Array(n + 1) { DoubleArray(m + 1) }
}
