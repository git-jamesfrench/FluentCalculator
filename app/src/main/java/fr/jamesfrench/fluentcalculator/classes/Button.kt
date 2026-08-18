package fr.jamesfrench.fluentcalculator.classes

import fr.jamesfrench.fluentcalculator.components.BigButtonVariant

data class ButtonData(
    val text: String,
    val variant: BigButtonVariant,
    val action: Action,
    val value: String? = null,
)