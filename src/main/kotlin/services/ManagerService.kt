package services

import dictionary.MainCommands
import exceptions.*
import models.Car
import models.Owner
import models.Parking

object ManagerService {

    private val parking = Parking.create()
    private var count = 0

    fun managerHelper(command: String?, arguments: String?): String? {
        val carInfo = parseInput(arguments)
        return when (MainCommands.get(command)) {
            MainCommands.PARK -> startParking(carInfo)
            MainCommands.RETURN -> returnCarToOwner(carInfo)
            MainCommands.PARK_INFO_BY_CAR -> parkInfoByCar(carInfo)
            MainCommands.PARK_INFO_BY_PLACE -> parkInfoByPlace(carInfo)
            MainCommands.PARK_STATS -> parkStats()
            MainCommands.PARK_ALL_STATS -> parkAllStats()
            else -> throw UnsupportedCommads("")
        }
    }

    // =================================================================================================================
    // = Implementation
    // =================================================================================================================

    private fun startParking(carInfo: List<String>): String? {
        checkArgumentSize(carInfo, 5)
        val owner = Owner(
            carInfo[4],
            carInfo[3]
        )

        val car = Car(
            model = carInfo[0],
            number = carInfo[1],
            color = carInfo[2],
            owner = owner
        )
        val parkingPlace = parking.filterValues { it == null }.keys.firstOrNull() ?: throw NoEmptyPlace("К сожелению, " +
                "нет свободных мест")
        parking[parkingPlace] = car
        count++

        println("Вы успешно припарковали машину, Ваше место '$parkingPlace'")
        return readLine()
    }

    private fun returnCarToOwner(carInfo: List<String>): String? {
        checkArgumentSize(carInfo, 3)

        val owner = Owner(
            carInfo[2],
            carInfo[1]
        )
        val car = parking[carInfo[0]]
            ?: parking.filterValues { it?.number == carInfo[0] }.values.firstOrNull()
            ?: throw NotFoundCar("По заданным критериям мы не смогли найти машину")

        if (car.owner != owner) {
            throw NoCorrectOwner("Вы пытались взять не свой автомобиль! Повторите попытку, пожалуйста")
        }

        val place = parking.filterValues { it == car }.keys.first()
        parking[place] = null

        println("Вы забрали свой автомобиль: '$car'")
        return readLine()
    }

    private fun parkInfoByCar(carInfo: List<String>): String? {
        checkArgumentSize(carInfo, 1)

        println("Ваше место на парковке '${carInfo[0]}'")
        return readLine()
    }

    private fun parkInfoByPlace(carInfo: List<String>): String? {
        checkArgumentSize(carInfo, 1)
        val car = parking.filterValues { it?.number == carInfo[0] }.values.firstOrNull()
            ?: throw NotFoundCar("По заданным критериям мы не смогли найти машину")

        println("Ваш автомобиль '$car'")
        return readLine()
    }

    private fun parkStats(): String? {
        val result: StringBuilder = StringBuilder("Загруженность парковки: \n")
        parking.forEach { (key, value) ->
            result.append(key)
                .append(" - ")
                .append(value ?: "FREE")
                .append("\n")
        }

        println(result)
        return readLine()
    }

    private fun parkAllStats(): String? {
        println("Всего было выполнено '$count' операций на парковке")
        return readLine()
    }

    private fun parseInput(arguments: String?) = arguments?.split(" ") ?: throw ErrorInput(
        "Вы не ввели " +
                "необходимые аргументы, повторите пожалуйста"
    )

    private fun checkArgumentSize(arguments: List<String>, size: Int) {
        if (arguments.size != size) {
            throw ErrorInput("Вы ввели не коррекные аргументы, повторите ввод, пожалуйста")
        }
    }
}