package com.github.mariofronza.input

import com.github.mariofronza.Performance

data class PerformanceInput(
    val playID: String,
    val audience: Int
) {
    fun toPerformance(playInput: PlayInput, amount: Int, volumeCredits: Int) = Performance(
        play = playInput,
        audience = audience,
        amount = amount,
        volumeCredits = volumeCredits
    )
}
