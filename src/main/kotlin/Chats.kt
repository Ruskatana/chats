import chatServices.createMessage
import java.util.*

fun main() {
    while (true) {
        println(
            """Выберите, что вы хотите сделать:
            | 1. Написать сообщение;
            | 2. Удалить сообщение; 
            | 3. Удалить чат;
            | 4. Вывести список всех чатов с сообщениями;
            | 5. Вывести список непрочитанных чатов;
            | 6. Вывести список последних сообщений из чатов;
            | 7. Редактировать сообщение
        """.trimMargin()
        )
        val scanner1 = Scanner(System.`in`)
        var choosing: Int = scanner1.nextInt()
        if (choosing == 1) {
            println("напишите ID собеседника")
            val scanner2 = Scanner(System.`in`)
            var userId: Int = scanner2.nextInt()
            println("напишите ID  сообщения")
            val scanner3 = Scanner(System.`in`)
            var messageId: Int = scanner3.nextInt()
            println("напишите сообщение")
            var messageFromUser = readLine()
            chatServices.createMessage(chatId = userId, messages(messageId = messageId, "$messageFromUser", "привет"))
        }
        if (choosing == 2) {
            println("напишите ID  сообщения, которое хотите удалить")
            val scanner = Scanner(System.`in`)
            var messageId: Int = scanner.nextInt()
            chatServices.deleteMessages(messageId1 = messageId)
        }
        if (choosing == 3) {
            println("Выберите чат, который хотите удалить, указав ID собеседника ")
            val scanner1 = Scanner(System.`in`)
            var userId: Int = scanner1.nextInt()
            chatServices.deleteChat(idChat = userId)
        }
        if (choosing == 4) {
            chatServices.printlnAllChats()
        }
        if (choosing == 6) {
            println(chatServices.getChatList())
        }
        if (choosing == 7) {
            println("Введите ID сообщения, которое хотите отредактировать")
            val scanner = Scanner(System.`in`)
            var messageId: Int = scanner.nextInt()
            println("напишите новое сообщение")
            var messageNew = readLine()
            chatServices.editMessages(messages(messageId = messageId,"",""),newMessage = messageNew.toString())

        }
    }
}

class MessageAlreadyDeletedException(message: String) : RuntimeException(message)

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

    fun deleteMessages(messageId1: Int): Boolean {
        for (i in allChats.values) {
            for (index in i.messagesCharacters) {
                if (index.messageId == messageId1) {
                    i.messagesCharacters.remove(index)
                    return true
                }
            }
            for (index in i.messagesCharacters) {
                if (index.messageId == messageId1) {
                    throw MessageAlreadyDeletedException("Сообщение уже удалено")
                }
            }
        }
        return false
    }

    fun editMessages(oldMessage: messages, newMessage: String): Boolean {
        for (i in allChats.values) {
            for (index in i.messagesCharacters) {
                if (index.messageId == oldMessage.messageId) {
                    i.messagesCharacters[index.messageId - 1] = oldMessage.copy(outGoingMessages = newMessage)
                    return true
                }
            }

        }
        return false
    }

    fun deleteChat(idChat: Int): Boolean {
        allChats.remove(idChat)
        return true
    }

    fun printlnAllChats() = println(allChats)

    fun getChatList(): List<String> {
        return allChats.values.map { chat ->
            chat.messagesCharacters.lastOrNull { Message -> !Message.deletedMessages }?.outGoingMessages
                ?: "сообщений нет "
        }
    }
 fun MessagesReadList(userId: Int, messageId: Int, messageCount: Int): List<messages>{
     return allChats[userId]?.messagesCharacters?.let { message ->
         val index = message.indexOfFirst {it.messageId == messageId }
         if (index + messageCount <= message.size) {
             message.slice ( index until index + messageCount).onEach { it.read = true }
         } else {
             message.slice(index until message.size).onEach { it.read = true }
         }
     }?: emptyList()
 }
    fun getUnreadedMessages(): Int {
        var count: Int = 0
        allChats.forEach{(key, value) ->
            value.messagesCharacters.map {
                if (!it.read) {
                    count ++
                    return@forEach
                }
            }
        }
        return count

    }
    fun clear() {
        allChats = mutableMapOf<Int, chats>()
        var idNext = 0
    }

}

data class messages(
    var messageId: Int,
    var outGoingMessages: String,
    var incomingMessages: String,
    var deletedMessages: Boolean = false,
    var read: Boolean = false
)
data class chats(
    var chatId: Int,
    var messagesCharacters: MutableList<messages> = mutableListOf(),
)








