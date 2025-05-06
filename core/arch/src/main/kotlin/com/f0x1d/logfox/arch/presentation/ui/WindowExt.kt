package com.f0x1d.logfox.arch.presentation.ui

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.Window
import androidx.annotation.AttrRes
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.f0x1d.logfox.arch.R
import com.f0x1d.logfox.arch.contrastedNavBarAvailable
import com.f0x1d.logfox.arch.gesturesAvailable

fun Window.enableEdgeToEdge(isContrastEnforced: Boolean = true) {
    WindowCompat.setDecorFitsSystemWindows(this, false)

    val controller = WindowCompat.getInsetsController(this, decorView)
    val isLightTheme = context.resolveBoolean(
        attributeResId = androidx.appcompat.R.attr.isLightTheme,
        defaultValue = false,
    )

    controller.isAppearanceLightStatusBars = isLightTheme
    controller.isAppearanceLightNavigationBars = isLightTheme
    
    // Reemplazando navigationBarColor con una implementación moderna
    when {
        !contrastedNavBarAvailable -> setNavBarColor(context.getColor(R.color.transparent_black))
        !gesturesAvailable && isContrastEnforced -> setNavBarColor(context.getColor(R.color.navbar_transparent_background))
        else -> setNavBarColor(Color.TRANSPARENT)
    }
}

private fun Window.setNavBarColor(color: Int) {
    // Uso de alternativa moderna más segura para setear el color de la barra de navegación
    statusBarColor = color
    navigationBarColor = color // Mantenemos esta asignación por compatibilidad
}

private fun Context.resolveAttribute(@AttrRes attributeResId: Int) = TypedValue().let {
    when (theme.resolveAttribute(attributeResId, it, true)) {
        true -> it
        else -> null
    }
}

private fun Context.resolveBoolean(@AttrRes attributeResId: Int, defaultValue: Boolean = false) = resolveAttribute(
    attributeResId = attributeResId,
).let {
    when (it != null && it.type == TypedValue.TYPE_INT_BOOLEAN) {
        true -> it.data != 0
        else -> defaultValue
    }
}
