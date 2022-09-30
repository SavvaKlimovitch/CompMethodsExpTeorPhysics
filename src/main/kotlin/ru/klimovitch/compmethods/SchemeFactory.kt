package ru.klimovitch.compmethods

class SchemeFactory {

    fun createScheme(type: SchemeType, equation: LinearEquation) = when (type) {
        SchemeType.ExplicitCorner -> ExplicitCorner(equation)
        SchemeType.LaxWendroff -> LaxWendroff(equation)
        SchemeType.Tvd -> Tvd(equation)
    }
}
