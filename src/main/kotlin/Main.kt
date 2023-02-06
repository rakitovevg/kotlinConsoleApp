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
   var command = readLine()
   runCatching {
      while (true) {
         when (MainCommands.get(command)) {
            MainCommands.HELP -> command = createAnswer(
               "Список команд:\n" +
                       "\"/start\" - запускает приложение\n" +
                       "\"/help\" - помощь по командам" +
                       "\"/end\" - завершение работы"
            )
            MainCommands.END -> {
               println("До свидания!")
               break
            }
            else -> {
               val managerService = ManagerService
               managerService.managerHelper()
            }
         }
      }
   }.onFailure {
      command = createAnswer("Вы ввели некорректную команду ${it.message}.\n" +
              "Введите команду '/help' для отображения списка команд!")
   }
}

private fun createAnswer(message: String): String? {
   println(message)
   return readLine()
}