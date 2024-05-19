package me.ks.chan.pica.plus.ui.theme

// https://m3.material.io/styles/motion/easing-and-duration/tokens-specs#c009dec6-f29b-4503-b9f0-482af14a8bbd

private const val DurationMultiplier = 50

/**
 * [Short durations]
 * These are used for small utility-focused transitions.
 **/
const val Duration_Short1 = DurationMultiplier * 1      // 50ms
const val Duration_Short2 = DurationMultiplier * 2      // 100ms
const val Duration_Short3 = DurationMultiplier * 3      // 150ms
const val Duration_Short4 = DurationMultiplier * 4      // 200ms

/**
 * [Medium durations]
 * These are used for transitions that traverse a medium area of the screen.
 **/
const val Duration_Medium1 = DurationMultiplier * 5     // 250ms
const val Duration_Medium2 = DurationMultiplier * 6     // 300ms
const val Duration_Medium3 = DurationMultiplier * 7     // 350ms
const val Duration_Medium4 = DurationMultiplier * 8     // 400ms

/**
 * [Long durations]
 * These durations are often paired with Emphasized easing.
 * They're used for large expressive transitions.
 **/
const val Duration_Long1 = DurationMultiplier * 9       // 450ms
const val Duration_Long2 = DurationMultiplier * 10      // 500ms
const val Duration_Long3 = DurationMultiplier * 11      // 550ms
const val Duration_Long4 = DurationMultiplier * 12      // 600ms

/**
 * [Extra long durations]
 * Though rare, some transitions use durations above 600ms.
 * These are usually used for ambient transitions that don't involve user input.
 **/
const val Duration_ExtraLong1 = DurationMultiplier * 14 // 700ms
const val Duration_ExtraLong2 = DurationMultiplier * 16 // 800ms
const val Duration_ExtraLong3 = DurationMultiplier * 18 // 900ms
const val Duration_ExtraLong4 = DurationMultiplier * 20 // 1000ms