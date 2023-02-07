package models

object Parking {

    fun create(): MutableMap<String, Car?> {
        val parking = mutableMapOf<String, Car?>()
        for (i in 1..20) {
            parking["P$i"] = null
        }
        return parking
    }
}