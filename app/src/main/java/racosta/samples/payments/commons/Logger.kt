package racosta.samples.payments.commons

import android.os.Build
import android.util.Log
import racosta.samples.commons.Logger

interface Tagged: Logger {
    val tag: String get() = this.TAG

    override fun verbose(message: Any?) { Log.v(tag, message?.toString() ?: "null") }

    override fun info(message: Any?) { Log.i(tag, message?.toString() ?: "null") }

    override fun warn(message: Any?) { Log.w(tag, message?.toString() ?: "null") }

    override fun debug(message: Any?) { Log.d(tag, message?.toString() ?: "null") }

    override fun error(message: Any?) { Log.e(tag, message?.toString() ?: "null") }
}

class AndroidLogger(tagClass: Class<*>): Tagged {

    override val tag: String = tagClass.getTag()
}

val Tagged.TAG: String
    get() = javaClass.getTag()

fun Tagged?.verbose(message: Any?) = this?.run { verbose(message?.toString() ?: "null") }
fun Tagged?.info(message: Any?) = this?.run { info(message?.toString() ?: "null") }
fun Tagged?.warn(message: Any?) = this?.run { warn(message?.toString() ?: "null") }
fun Tagged?.debug(message: Any?) = this?.run { debug(message?.toString() ?: "null") }
fun Tagged?.error(message: Any?) = this?.run { error(message?.toString() ?: "null") }

private fun Class<*>.getTag() = this.simpleName.let {
    if (it.length <= 23 || allowsMoreThan23CharTag) it
    else it.substring(0, 23)
}

private val allowsMoreThan23CharTag = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N