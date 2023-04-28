import java.util.*

fun main() {
    while (true) {
        println(
            """Выберите, что вы хотите сделать:
            | 1. Написать сообщение
            | 2. Удалить сообщение 
            | 3. Удалить чат
            | 4. Вывести список всех чатов с сообщениями
            | 5. Вывести список непрочитанных чатов
            | 6. Вывести список последних сообщений из чатов
        """.trimMargin()
        )
        val scanner1 = Scanner(System.`in`)
        var choosing: Int = scanner1.nextInt()
        if (choosing == 1) {
            println("напишите ID собеседника")
            val scanner2 = Scanner(System.`in`)
            var userId: Int = scanner2.nextInt()
            println("напишите сообщение")
            var messafeFromUser = readLine()
            chatServices.createMessage(chatId = userId, messages("$messafeFromUser"))
        }
        if (choosing == 4) {
            chatServices.printlnAllChats()
        }
        if (choosing == 6) {
            println(chatServices.getChatList())
        }


    }

}

object chatServices {
    var allChats = mutableMapOf<Int, chats>()

    fun createMessage(chatId: Int, Message: messages) {
        if (allChats.containsKey(chatId)) {
            allChats[chatId]?.messagesCharacters?.add(Message)
        } else {
            val chat = chats(chatId)
            chat.messagesCharacters.add(Message)
            allChats[chatId] = chat
        }
    }

    fun deleteChat(idChat: Int): Boolean {
        for (index in allChats) {
            if (index. == idChat){
                allChats.remove(index)
                return true
            }
        }
        return false
    }

    fun printlnAllChats() = println(allChats)

    fun getChatList(): List<String> {
        return allChats.values.map { chat ->
            chat.messagesCharacters.lastOrNull { Message -> !Message.deletedMessages }?.outGoingMessages
                ?: "сообщений нет "
        }
    }
}


data class messages(
    var outGoingMessages: String,
    var deletedMessages: Boolean = false,
)

data class chats(
    var chatId: Int,
    var messagesCharacters: MutableList<messages> = mutableListOf(),
)








