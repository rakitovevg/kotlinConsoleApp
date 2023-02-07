import dictionary.MainCommands
import services.ManagerService

private const val START = "/start"

fun main() {
   println("Введите комманду: ")
   val startCommand = readLine()
   if (startCommand != START) {
      println("Для запуска приложения введите, пожалуйста, команду '$START'")
      readLine()
   }
      println("Добро пожаловать в Parking!")
      start()
}

// =====================================================================================================================
// = Implementation
// =====================================================================================================================

private fun start() {
   var input = readLine()
   runCatching {
      while (true) {
         val command = input?.substringBefore(" ")
         val arguments = input?.substringAfter(" ")
         when (MainCommands.get(command)) {
            MainCommands.HELP -> input = createAnswer(
               "Список команд:\n" +
                       "\"/help\" - помощь по командам\n" +
                       "\"/end\" - завершение работы\n" +
                       "\"/park\" - паркует автомобиль на свободное место\n" +
                       "\"/return\" - возвращает автомобиль владельцу по номеру либо месту на парковке, " +
                       "необходимо указать владельца\n" +
                       "\"/park_info_by_car\" - возвращает место где припаркован автомобиль по номеру\n" +
                       "\"/park_info_by_place\" - возвращает информацию по машине по месту на парковке\n"
            )
            MainCommands.END -> {
               println("До свидания!")
               break
            }
            else -> {
               val managerService = ManagerService
               input = managerService.managerHelper(command, arguments)
            }
         }
      }
   }.onFailure {
      createFailureAnswer(it.message)
      start()
   }
}

private fun createAnswer(message: String?): String? {
   println(message)
   return readLine()
}

private fun createFailureAnswer(message: String?) {
   println(message)
}