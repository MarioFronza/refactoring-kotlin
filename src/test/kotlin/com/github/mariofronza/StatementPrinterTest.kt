package com.github.mariofronza

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class StatementPrinterTest {


    @Test
    fun `given a valid input should print the correct statement when calls print`() {
        val plays = mapOf(
            "hamlet" to Play("Hamlet", "tragedy"),
            "as-like" to Play("As You Like It", "comedy"),
            "othello" to Play("Othello", "tragedy")
        )

        val invoice = Invoice(
            "BigCo", listOf(
                Performance("hamlet", 55),
                Performance("as-like", 35),
                Performance("othello", 40)
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

        val statementPrinter = StatementPrinter()
        val actual = statementPrinter.print(invoice, plays)

        kotlin.test.assertEquals(expected, actual)
    }

    @Test
    fun `statement with new play types`() {
        val plays = mapOf(
            "henry-v" to Play("Henry V", "history"),
            "as-like" to Play("As You Like It", "pastoral")
        )

        val invoice = Invoice(
            "BigCo", listOf(
                Performance("henry-v", 53),
                Performance("as-like", 55)
            )
        )

        val statementPrinter = StatementPrinter()
        assertThrows(Error::class.java) { statementPrinter.print(invoice, plays) }
    }
}