package racosta.samples.core.commons

import java.util.*

/**
 * Class that uses [Calendar] to provide an
 * instance with the current date.
 *
 * It enables components that use this to be testable, since
 * we can mock the date to be fixed in tests.
 */
internal class CurrentDateProvider {

    val currentDate: Calendar get() = Calendar.getInstance()
}