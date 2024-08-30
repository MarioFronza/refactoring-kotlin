package com.github.mariofronza

import com.github.mariofronza.input.InvoiceInput
import com.github.mariofronza.input.PerformanceInput
import com.github.mariofronza.input.PlayInput
import java.text.NumberFormat.getCurrencyInstance
import java.util.Locale.US

class StatementPrinter(
    private val invoice: InvoiceInput,
    private val plays: Map<String, PlayInput>
) {

    fun statement(): String {
        return renderPlaintText(createStatement())
    }

    private fun renderPlaintText(data: Statement): String {
        var result = "Statement for ${data.customer}\n"
        data.performances.forEach { perf ->
            result += "  ${perf.play.name}: ${usd((perf.amount).toLong())} (${perf.audience} seats)\n"
        }
        result += "Amount owed is ${usd((data.totalAmount).toLong())}\n"
        result += "You earned ${data.totalVolumeCredits} credits\n"
        return result
    }

    private fun createStatement() = Statement(
        customer = invoice.customer,
        performances = invoice.performances.map(::createPerformance)
    )

    private fun createPerformance(performanceInput: PerformanceInput): Performance {
        val calculator = createPerformanceCalculator(
            performanceInput = performanceInput,
            playInput = playFor(performanceInput)
        )
        return performanceInput.toPerformance(
            playInput = calculator.play,
            amount = calculator.amount(),
            volumeCredits = calculator.volumeCredits()
        )
    }

    private fun createPerformanceCalculator(performanceInput: PerformanceInput, playInput: PlayInput) =
        when (playInput.type) {
            "tragedy" -> PerformanceCalculator(playInput, calculator = TragedyCalculator(performanceInput))
            "comedy" -> PerformanceCalculator(playInput, calculator = ComedyCalculator(performanceInput))
            else -> throw Error("unknown type: ${playInput.type}")
        }

    private fun usd(number: Long) = getCurrencyInstance(US).format(number / 100)

    private fun playFor(aPerformance: PerformanceInput) = plays.getValue(aPerformance.playID)


}