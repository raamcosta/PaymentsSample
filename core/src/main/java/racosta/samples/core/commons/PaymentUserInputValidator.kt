package racosta.samples.core.commons

import racosta.samples.commons.Logger
import java.lang.Exception
import java.util.*
import java.util.regex.Pattern

private val VISA_REGEX = Pattern.compile("^4\\d{3}([\\ \\-]?)(?:\\d{4}\\1){2}\\d(?:\\d{3})?$")
private val MASTERCARD_REGEX = Pattern.compile("^5[1-5]\\d{2}([\\ \\-]?)\\d{4}\\1\\d{4}\\1\\d{4}$")
private val CVV_REGEX = Pattern.compile("^[0-9]{3}$")

/**
 * Validates user inputs
 */
class PaymentUserInputValidator internal constructor(
    private val currentDateProvider: CurrentDateProvider,
    private val logger: Logger? = null
) {

    fun isDateValid(month: String, year: String): Boolean {
        return try {
            val monthInt = month.toInt()
            if (monthInt <= 0 || monthInt > 12) {
                return false
            }
            val currentDate: Calendar = currentDateProvider.currentDate
            val yearInt = year.toInt() + 2000
            if (yearInt < currentDate[Calendar.YEAR]) {
                return false
            }
            val inputDate: Calendar = currentDateProvider.currentDate
            inputDate[Calendar.YEAR] = yearInt
            inputDate[Calendar.MONTH] = monthInt - 1
            !inputDate.before(currentDate)
        } catch (e: Exception) {
            logger?.verbose("Error reading input, " + e.message)
            false
        }
    }

    fun isCardNumberValid(cardNumber: String): Boolean {
        return VISA_REGEX.matcher(cardNumber).matches() || MASTERCARD_REGEX.matcher(cardNumber)
            .matches()
    }

    fun isCvvValid(cvv: String): Boolean {
        return CVV_REGEX.matcher(cvv).matches()
    }

    fun isAmountValid(amount: String): Boolean {
        val parts = amount.split("[,.]".toRegex(), 3).toTypedArray()
        if (parts.size > 2) {
            return false
        }

        val integerPart = parts[0]
        if (integerPart.length > 8) {
            return false
        }

        val decimalPart: String = if (parts.size > 1 && parts[1].isNotEmpty()) {
            parts[1]
        } else {
            "00"
        }

        if (decimalPart.length > 2) {
            return false
        }

        try {
            integerPart.toInt()
            decimalPart.toInt()
        } catch (e: Exception) {
            logger?.verbose("Error reading input, " + e.message)
            return false
        }

        return true
    }
}