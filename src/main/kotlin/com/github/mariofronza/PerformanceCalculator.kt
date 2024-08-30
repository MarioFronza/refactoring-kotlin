package com.github.mariofronza

import com.github.mariofronza.input.PerformanceInput
import com.github.mariofronza.input.PlayInput
import kotlin.math.floor
import kotlin.math.max

interface Calculator {
    fun amount(): Int
    fun volumeCredits(): Int
}

class PerformanceCalculator(
    val play: PlayInput,
    private val calculator: Calculator
) : Calculator by calculator

class TragedyCalculator(
    private val performance: PerformanceInput
) : Calculator {
    override fun amount(): Int {
        var result = 40000
        if (performance.audience > 30) {
            result += 1000 * (performance.audience - 30)
        }
        return result
    }

    override fun volumeCredits() = max(performance.audience - 30, 0)
}

class ComedyCalculator(
    private val performance: PerformanceInput
) : Calculator {
    override fun amount(): Int {
        var result = 30000
        if (performance.audience > 20) {
            result += 10000 + 500 * (performance.audience - 20)
        }
        result += 300 * performance.audience
        return result
    }

    override fun volumeCredits(): Int {
        return max(performance.audience - 30, 0) + floor((performance.audience / 5).toDouble()).toInt()
    }
}


