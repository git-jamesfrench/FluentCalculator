package fr.jamesfrench.fluentcalculator.ui.theming

import androidx.compose.ui.graphics.Color

private val NothingRed = Color(0xFFC8102E)
private val LighterNothingRed = Color(0xFF860C20)
private val White = Color(0xFFFFFFFF)
private val LighterWhite = Color(0xFFDEDEDE)
private val LightGray = Color(0xFF9C9C9C)
private val Gray = Color(0xFF6E6E6E)
private val Black = Color(0xFF000000)
private val FaintDarkGray = Color(0xFF19191A)
private val DarkGray = Color(0xFF323234)

object Themes {

    private val typography = defaultTypography()
    private val shapes = AppShapes()
    private val spacing = AppSpacing()
    private val motion = AppMotion()

    val Default: ThemeFamily = ThemeFamily(
        id = "default",
        label = "Default",
        light = Theme("default-light", LightColors, typography, shapes, spacing, motion),
        dark = Theme("default-dark", DarkColors, typography, shapes, spacing, motion),
    )

    val Amoled: ThemeFamily = ThemeFamily(
        id = "amoled",
        label = "AMOLED",
        light = Default.light,
        dark = Theme("amoled-dark", AmoledColors, typography, shapes, spacing, motion),
    )

    val HighContrast: ThemeFamily = ThemeFamily(
        id = "high-contrast",
        label = "High contrast",
        light = Theme("high-contrast-light", HighContrastLightColors, typography, shapes, spacing, motion),
        dark = Theme("high-contrast-dark", HighContrastDarkColors, typography, shapes, spacing, motion),
    )

    private val registry = LinkedHashMap<String, ThemeFamily>()

    init {
        register(Default)
        register(Amoled)
        register(HighContrast)
    }

    val all: List<ThemeFamily>
        get() = registry.values.toList()

    fun byId(id: String): ThemeFamily? = registry[id]

    fun byIdOrDefault(id: String): ThemeFamily = registry[id] ?: Default

    fun register(family: ThemeFamily) {
        require(family.id.isNotBlank()) { "ThemeFamily id must not be blank." }
        require(family.id !in registry) {
            "A theme family with id '${family.id}' is already registered."
        }
        registry[family.id] = family
    }

    fun override(family: ThemeFamily) {
        require(family.id.isNotBlank()) { "ThemeFamily id must not be blank." }
        registry[family.id] = family
    }
}

internal val DarkColors = AppColors(
    background = Black,
    onBackground = White,
    onBackgroundFaint1 = LighterWhite,
    onBackgroundFaint2 = LightGray,
    onBackgroundFaint3 = Gray,
    surface = DarkGray,
    onSurface = White,
    neutral = FaintDarkGray,
    activeNeutral = DarkGray,
    onNeutral = White,
    inverse = White,
    activeInverse = LightGray,
    onInverse = Black,
    accent = NothingRed,
    activeAccent = LighterNothingRed,
    onAccent = White,
    error = NothingRed,
    onError = White,
    scrim = Black.copy(alpha = 0.5f),
    isDark = true,
    isStatusBarLight = true,
)

internal val LightColors = AppColors(
    background = White,
    onBackground = FaintDarkGray,
    onBackgroundFaint1 = DarkGray,
    onBackgroundFaint2 = Gray,
    onBackgroundFaint3 = LightGray,
    surface = LighterWhite,
    onSurface = FaintDarkGray,
    neutral = Color(0xFFF0F0F0),
    activeNeutral = LighterWhite,
    onNeutral = FaintDarkGray,
    inverse = Black,
    activeInverse = FaintDarkGray,
    onInverse = White,
    accent = NothingRed,
    activeAccent = LighterNothingRed,
    onAccent = White,
    error = NothingRed,
    onError = White,
    scrim = Black.copy(alpha = 0.4f),
    isDark = false,
    isStatusBarLight = false,
)

internal val AmoledColors = DarkColors.copy(
    surface = Color(0xFF141416),
    neutral = Color(0xFF0A0A0B),
    activeNeutral = FaintDarkGray,
    scrim = Black.copy(alpha = 0.65f),
)

internal val HighContrastDarkColors = DarkColors.copy(
    onBackground = White,
    onBackgroundFaint1 = Color(0xFFEDEDED),
    onBackgroundFaint2 = Color(0xFFCFCFCF),
    onBackgroundFaint3 = Color(0xFFADADAD),
    accent = NothingRed.ensureContrast(Black, ContrastRatioAa),
    activeAccent = NothingRed.ensureContrast(Black, ContrastRatioAa),
    error = NothingRed.ensureContrast(Black, ContrastRatioAa),
)

internal val HighContrastLightColors = LightColors.copy(
    onBackground = Black,
    onBackgroundFaint1 = FaintDarkGray,
    onBackgroundFaint2 = DarkGray,
    onBackgroundFaint3 = Gray,
    accent = NothingRed.ensureContrast(White, ContrastRatioAa),
    activeAccent = NothingRed.ensureContrast(White, ContrastRatioAa),
    error = NothingRed.ensureContrast(White, ContrastRatioAa),
)
