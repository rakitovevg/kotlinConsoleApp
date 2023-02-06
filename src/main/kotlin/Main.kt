fun main() {
   println("Введите комманду: ")
   consoleHelper()
}

// =====================================================================================================================
// = Implementation
// =====================================================================================================================

private fun consoleHelper() {
   var command = readLine()

   while (true) {
      when(command) {
         "/start" -> command = createAnswer("Приветствую в приложении паркинг")
         "/help" -> command = createAnswer("Список команд:\n" +
                 "\"/start\" - запускает приложение\n" +
                 "\"/help\" - помощь по командам" +
                 "\"/end\" - завершение работы")
         "/end" -> {
            println("Досвидания!")
            break
         }
         else -> command = createAnswer("Вы ввели не коррекную комманду, введите '/help' для вывода списка команд")
      }
   }
}

private fun createAnswer(message: String): String? {
   println(message)
   return readLine()
}