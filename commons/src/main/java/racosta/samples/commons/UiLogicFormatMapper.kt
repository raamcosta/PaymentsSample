package racosta.samples.commons

import java.math.BigDecimal
import java.text.DateFormat
import java.text.DecimalFormat
import java.util.*

object UiLogicFormatMapper {

    fun getAmountFromUI(amount: String): BigDecimal {
        return BigDecimal(amount.replace(",", "."))
    }

    fun getCardNumberFromUI(cardNumber: String): Long {
        return cardNumber.replace(" ", "").toLong()
    }

    fun getAmountForUI(amount: BigDecimal): String {
        return if (amount.stripTrailingZeros().scale() <= 0) {
            amount.toString()
        } else DecimalFormat("0.00").format(amount)
    }

    fun getCardNumberForUI(cardNumber: Long): String {
        val uiCardNumber = cardNumber.toString()
        return insertPeriodically(uiCardNumber, " ", 4)
    }

    fun getDateForUI(epochTime: Long): String {
        val date = Calendar.getInstance()
        date.timeInMillis = epochTime
        return DateFormat.getDateTimeInstance().format(date.time)
    }

    private fun insertPeriodically(
        text: String,
        insert: String,
        period: Int
    ): String {
        val builder = StringBuilder(text.length + insert.length * (text.length / period) + 1)
        var index = 0
        var prefix = ""
        while (index < text.length) {
            builder.append(prefix)
            prefix = insert
            builder.append(text.substring(index, (index + period).coerceAtMost(text.length)))
            index += period
        }
        return builder.toString()
    }
}