package org.fletcher.service

import com.opencsv.CSVReader
import com.opencsv.CSVWriter
import java.io.FileReader
import java.io.FileWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

data class Aggregate(
    val amount: Double,
    val currency: String,
    val purchaseDate: String,
    val dateSold: String,
    val costBasis: Double,
    val proceeds: Double
)

fun main() {
    val reader = CSVReader(FileReader("gain-loss.csv"))
    reader.readNext() // skip original header
    val txns = reader.readAll().map { row ->
        Aggregate(
            row[0].toDouble(), row[1], row[2], row[3], row[4].toDouble(), row[5].toDouble()
        )
    }
    reader.close()

    val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm")

    val grouped = txns.groupBy { Triple(it.currency, it.purchaseDate, it.dateSold) }.map { (key, group) ->
        val totalAmount = group.sumOf { it.amount }
        val totalCostBasis = group.sumOf { it.costBasis }
        val totalProceeds = group.sumOf { it.proceeds }
        val purchaseDate = LocalDateTime.parse(key.second, formatter)
        val dateSold = LocalDateTime.parse(key.third, formatter)
        val daysHeld = ChronoUnit.DAYS.between(purchaseDate, dateSold)
        val term = if (daysHeld < 365) "Short Term" else "Long Term"
        val gainLoss = totalProceeds - totalCostBasis
        arrayOf(
            totalAmount.toString(),
            key.first,
            key.second,
            key.third,
            String.format("%.2f", totalCostBasis),
            String.format("%.2f", totalProceeds),
            term,
            String.format("%.2f", gainLoss)
        )
    }

    val header = arrayOf(
        "Amount",
        "Currency Name",
        "Purchase Date",
        "Date sold",
        "Cost Basis",
        "Proceeds",
        "Term",
        "Gain/Loss"
    )

    val writer = CSVWriter(FileWriter("gain-loss-combined-term-gain.csv"))
    writer.writeNext(header)
    grouped.forEach { writer.writeNext(it) }
    writer.close()

    // Calculate total short-term and long-term capital gains
    var shortTermGain = 0.0
    var longTermGain = 0.0
    grouped.forEach { row ->
        val term = row[6]
        val gainLoss = row[7].toDouble()
        if (term == "Short Term") {
            shortTermGain += gainLoss
        } else {
            longTermGain += gainLoss
        }
    }
    println("Total Short Term Capital Gain: %.2f".format(shortTermGain))
    println("Total Long Term Capital Gain: %.2f".format(longTermGain))
}