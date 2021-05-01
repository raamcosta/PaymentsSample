package racosta.samples.commons

interface Logger {
    fun verbose(message: Any?)

    fun info(message: Any?)

    fun warn(message: Any?)

    fun debug(message: Any?)

    fun error(message: Any?)
}