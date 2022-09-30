package ru.klimovitch.compmethods

class Grid(private val grid: Array<DoubleArray>) {

    constructor(n: Int, m: Int) : this(
        Array(n + 1) { DoubleArray(m + 1) }
    )

    val n: Int
        get() = grid.size - 1

    val m: Int
        get() = grid[0].size - 1

    val nXPoints: Int
        get() = grid.size

    val nTimePoints: Int
        get() = grid[0].size

    fun get(timePoint: Int, xPoint: Int) = grid[timePoint][xPoint]

    fun set(timePoint: Int, xPoint: Int, value: Double) {
        grid[timePoint][xPoint] = value
    }
}
