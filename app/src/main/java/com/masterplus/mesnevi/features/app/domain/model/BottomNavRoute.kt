package com.masterplus.mesnevi.features.app.domain.model

import androidx.annotation.DrawableRes
import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.domain.util.UiText

sealed class BottomNavRoute(val route: String, val title: UiText, @DrawableRes val resourceId: Int) {
    object Home : BottomNavRoute("home",UiText.Resource(R.string.home), R.drawable.baseline_home_24)
    object List : BottomNavRoute("list",UiText.Resource(R.string.list), R.drawable.baseline_view_list_24)
    object Setting : BottomNavRoute("setting",UiText.Resource(R.string.settings), R.drawable.ic_baseline_settings_24)
}

val kBottomNavRoutes = listOf(
    BottomNavRoute.List,
    BottomNavRoute.Home,
    BottomNavRoute.Setting
)

val kBottomNavRouteNames = kBottomNavRoutes.map { it.route }
