package com.example.feed.util

fun Int.shorten(round: Int = 1) = when {
    this >= 1000000000 -> {
        String.format("%.${round}fb", this / 1000000000.0)
    }
    this >= 1000000 -> {
        String.format("%.${round}fm", this / 1000000.0)
    }
    this >= 1000 -> {
        String.format("%.${round}fk", this / 1000.0)
    }
    else -> this.toString()
}