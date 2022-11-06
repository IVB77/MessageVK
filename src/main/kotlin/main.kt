import java.time.LocalDate
import java.time.LocalTime


data class Messages(
    val userId: Int,
    var messageText: String,
    val isIncomingMessage: Boolean = false,
    var isDelete: Boolean = false,
    var isRead: Boolean = false,
    val dateOfCreation: LocalDate = LocalDate.now(),
    val timeOfCreation: LocalTime = LocalTime.now()
)

data class User(
    val userName: String,
    var isDelete: Boolean = false
)

object MessageService {
    private var messageId: Int = 0
    private var userId: Int = 0
    private var user: MutableMap<Int, User> = mutableMapOf()
    private var message: MutableMap<Int, Messages> = mutableMapOf()

    fun addUser(userName: String) {
        user[++userId] = User(userName)
        addMessage(Messages(userId, "Invite on new chat"))
    }

    fun addMessage(addMessage: Messages): Boolean {
        return if (user.findIndex(addMessage.userId) && !user[addMessage.userId]?.isDelete!!) {
            message[++messageId] = addMessage
            true
        } else {
            false
        }
    }

    private fun <E, T> MutableMap<E, T>.findIndex(index: Int): Boolean = this.filter { it.key == index }.isNotEmpty()

    fun deleteMessage(idMessage: Int) {
        if (message.findIndex(idMessage))
            message[idMessage]?.isDelete = true
        if (message.filter { it.value.userId == message[idMessage]?.userId && !it.value.isDelete }.isEmpty())
            user[message[idMessage]?.userId]?.isDelete = true
    }

    fun deleteChat(idUser: Int) {
        if (user.findIndex(idUser))
            user[idUser]?.isDelete = true
    }

    fun readMessage(idMessage: Int) {
        if (message.findIndex(idMessage) && !message[idMessage]?.isDelete!!)
            message[idMessage]?.isRead = true
    }

    fun editMessage(idMessage: Int, messages: String) {
        if (message.findIndex(idMessage) && !message[idMessage]?.isDelete!!)
            message[idMessage]?.messageText = messages
    }

    fun statisticOfChat() {
        for (us in user) {
            if (!us.value.isDelete) {
                val totalMessage: Int = message.filter { it.value.userId == us.key }.size
                val unreadMessage: Int = message.filter { it.value.userId == us.key && !it.value.isRead }.size
                println("chat with ${us.value.userName} messages $totalMessage from them $unreadMessage are not read")
            }
        }
    }

    fun listOfChat() {
        for (us in user) {
            if (!us.value.isDelete) {
                println("chat with ${us.value.userName}")
                println("Last Message: ${message.filter { it.value.userId == us.key && !it.value.isDelete }.values.last().messageText}")
            }
        }
    }

    fun listOfChatId(userId: Int) {
        val messageFilterUser = message.filter { it.value.userId == userId }
        val messageNotRead = messageFilterUser.filter { !it.value.isRead }
        if (messageNotRead.isNotEmpty()) {
            println("Chat with ${user[userId]?.userName}")
            println("${messageNotRead.size} new messages")
            println("${messageNotRead.keys.first()} id of first not read message ")
            for (mes in messageNotRead)
                mes.value.isRead = true
        } else
            println("There are no new messages in this chat")
    }

    fun getUser(): MutableMap<Int, User> {
        for (us in user)
            println(us)
        return user
    }

    fun getMessage() {
        for (mes in message)
            println(mes)
    }

    fun clearBase() {
        user.clear()
        message.clear()
        userId = 0
        messageId = 0
    }
}

fun main() {
    MessageService.addUser("Jhon")
    MessageService.addUser("Olga")
    MessageService.addUser("Smith")
    MessageService.addMessage(Messages(1, "Hi Jhon"))
    MessageService.addMessage(Messages(2, "Hi Olga"))
    MessageService.addMessage(Messages(3, "Hi Smith"))
    MessageService.addMessage(Messages(2, "Hi, how are you?", true))
    MessageService.getUser()
    MessageService.getMessage()
    MessageService.deleteMessage(1)
    MessageService.readMessage(4)
    MessageService.editMessage(5, "Sorry mistake")
    MessageService.editMessage(1, "mistake")
    println()
    println("Последние сообщения по чатам")
    MessageService.listOfChat()
    println()
    println("Статистика по сообщениям")
    MessageService.statisticOfChat()
    println()
    println("Непрочитанных сообщений в заданном чате")
    MessageService.listOfChatId(2)
    println("Проверка на то что все сообщения пометились как прочитанные")
    MessageService.listOfChatId(2)
    println()
    println("Удаление чата 1")
    MessageService.deleteChat(1)
    MessageService.listOfChat()
    println()
    println("Удаление всех сообщений приводит к удалению чата")
    MessageService.deleteMessage(3)
    MessageService.deleteMessage(6)
    MessageService.listOfChat()
    MessageService.statisticOfChat()

}