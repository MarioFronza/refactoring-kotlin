package com.github.mariofronza

import com.github.mariofronza.input.PlayInput

data class Performance(
    val play: PlayInput,
    val audience: Int,
    val amount: Int,
    val volumeCredits: Int
)