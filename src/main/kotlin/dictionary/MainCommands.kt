package dictionary

import exceptions.UnsupportedCommads

enum class MainCommands(val s: String) {
    HELP("/help"),
    END("/end");

    companion object {

        fun find(value: String?): MainCommands? = values().firstOrNull { it.s == value }
        fun get(value: String?): MainCommands = find(value) ?: run {
            throw UnsupportedCommads(value)
        }
    }
}