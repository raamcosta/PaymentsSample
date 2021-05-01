package racosta.samples.payments.commons

import android.app.Activity
import androidx.navigation.NavDirections

data class NavigationAction(
    val navDirections: NavDirections,
    val finishedAction: String? = null,
    val actionResult: Int = Activity.RESULT_OK
)

fun NavDirections.toNavAction() = NavigationAction(this)