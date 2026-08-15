package fr.jamesfrench.fluentcalculator.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.jamesfrench.fluentcalculator.R
import fr.jamesfrench.fluentcalculator.icons.LucideCog
import fr.jamesfrench.fluentcalculator.ui.theme.C

@Composable
fun Navigation() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, C.colors.faintHighlight, RoundedCornerShape(100))
    ) {
        RoundButton() {
            Icon(LucideCog, stringResource(R.string.settings_description), size = 24.dp)
        }
    }
}