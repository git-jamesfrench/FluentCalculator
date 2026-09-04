package fr.jamesfrench.fluentcalculator.classes

enum class T(val value: Char = ' ', val values: List<Char> = listOf()) {
    Empty,
    Number('0', listOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.')),
    Prefix('+', listOf('+', '-')),
    Operator('+', listOf('+', '-', '*', '/')),
    OpenParentheses('(', listOf('(')),
    CloseParentheses(')', listOf(')'))
}