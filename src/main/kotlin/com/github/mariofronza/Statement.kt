package com.github.mariofronza


data class Statement(
    val customer: String,
    val performances: List<Performance>,
) {
    val totalAmount
        get() = performances.sumOf { perf ->
            perf.amount
        }

    val totalVolumeCredits
        get() = performances.sumOf { perf ->
            perf.volumeCredits
        }

}