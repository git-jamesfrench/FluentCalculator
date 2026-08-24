package fr.jamesfrench.fluentcalculator.classes

enum class T(val defaultValue: Char, val values: List<Char> = listOf()) {
    Number('0', listOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.')),
    Minus('-'),
    Operator('+', listOf('+', '-', '*', '/'))
}