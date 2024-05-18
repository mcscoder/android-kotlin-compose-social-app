package com.example.thread.ui.constant

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavBackStackEntry

fun AnimatedContentTransitionScope<NavBackStackEntry>.rightToLeftSlideInAnimation(): EnterTransition =
    fadeIn(
        animationSpec = tween(200, easing = LinearEasing)
    ) + slideIntoContainer(
        animationSpec = tween(300, easing = EaseIn),
        towards = AnimatedContentTransitionScope.SlideDirection.Start
    )

fun AnimatedContentTransitionScope<NavBackStackEntry>.leftToRightSlideOutAnimation(): ExitTransition =
    fadeOut(
        animationSpec = tween(200, easing = LinearEasing)
    ) + slideOutOfContainer(
        animationSpec = tween(300, easing = EaseOut),
        towards = AnimatedContentTransitionScope.SlideDirection.End
    )

fun AnimatedContentTransitionScope<NavBackStackEntry>.bottomToTopSlideInAnimation(): EnterTransition =
    fadeIn(
        animationSpec = tween(200, easing = LinearEasing)
    ) + slideIntoContainer(
        animationSpec = tween(300, easing = EaseIn),
        towards = AnimatedContentTransitionScope.SlideDirection.Up
    )

fun AnimatedContentTransitionScope<NavBackStackEntry>.topToBottomSlideOutAnimation(): ExitTransition =
    fadeOut(
        animationSpec = tween(200, easing = LinearEasing)
    ) + slideOutOfContainer(
        animationSpec = tween(300, easing = EaseOut),
        towards = AnimatedContentTransitionScope.SlideDirection.Down
    )
