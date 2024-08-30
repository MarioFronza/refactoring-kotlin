package com.github.mariofronza

import com.github.mariofronza.input.InvoiceInput
import com.github.mariofronza.input.PerformanceInput
import com.github.mariofronza.input.PlayInput
import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test
import kotlin.test.assertEquals

class StatementPrinterTest {


    @Test
    fun `given a valid input should print the correct statement when calls print`() {
        val plays = mapOf(
            "hamlet" to PlayInput("Hamlet", "tragedy"),
            "as-like" to PlayInput("As You Like It", "comedy"),
            "othello" to PlayInput("Othello", "tragedy")
        )

        val invoice = InvoiceInput(
            "BigCo", listOf(
                PerformanceInput("hamlet", 55),
                PerformanceInput("as-like", 35),
                PerformanceInput("othello", 40)
            )
        )

        val expected = """
            Statement for BigCo
              Hamlet: ${'$'}650.00 (55 seats)
              As You Like It: ${'$'}580.00 (35 seats)
              Othello: ${'$'}500.00 (40 seats)
            Amount owed is ${'$'}1,730.00
            You earned 47 credits
            
        """.trimIndent()

        val statementPrinter = StatementPrinter(invoice, plays)
        val actual = statementPrinter.statement()

        assertEquals(expected, actual)
    }

    @Test
    fun `statement with new play types`() {
        val plays = mapOf(
            "henry-v" to PlayInput("Henry V", "history"),
            "as-like" to PlayInput("As You Like It", "pastoral")
        )

        val invoice = InvoiceInput(
            "BigCo", listOf(
                PerformanceInput("henry-v", 53),
                PerformanceInput("as-like", 55)
            )
        )

        val statementPrinter = StatementPrinter(invoice, plays)
        assertThrows(Error::class.java) { statementPrinter.statement() }
    }
}