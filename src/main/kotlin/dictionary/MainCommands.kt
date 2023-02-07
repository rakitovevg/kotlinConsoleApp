package dictionary

import exceptions.UnsupportedCommads

enum class MainCommands(val s: String) {
    HELP("/help"),
    END("/end"),
    PARK("/park"),
    RETURN("/return"),
    PARK_INFO_BY_CAR("/park_info_by_car"),
    PARK_INFO_BY_PLACE("/park_info_by_place");

    companion object {

        fun find(value: String?): MainCommands? = values().firstOrNull { it.s == value }
        fun get(value: String?): MainCommands = find(value) ?: run {
            throw UnsupportedCommads("Вы ввели некорректную команду '$value'.\n" +
                "Введите команду '/help' для отображения списка команд!")
        }
    }
}