import org.junit.Test

import org.junit.Assert.*

class MessageServiceTest {

    @Test
    fun addUser() {
        MessageService.clearBase()
        MessageService.addUser("Jhon")
    }

    @Test
    fun addMessageWrongUser() {
        MessageService.clearBase()
        val result = MessageService.addMessage(Messages(3, "Hi Smith"))
        assertFalse(result)
    }

    @Test
    fun addMessageWrongIsDelete() {
        MessageService.clearBase()
        MessageService.addUser("Jhon")
        val result1 = MessageService.addMessage(Messages(2, "Hi Smith"))
        val resultTrue = MessageService.addMessage(Messages(1, "Hi Smith"))
        MessageService.deleteChat(1)
        val result2 = MessageService.addMessage(Messages(1, "Hi Smith"))
        assertFalse(result1 && result2 && !resultTrue)
    }

    @Test
    fun deleteMessage() {
        MessageService.clearBase()
        MessageService.addUser("Jhon")
        MessageService.addMessage(Messages(1, "Hi Smith"))
        MessageService.deleteMessage(1)
        MessageService.deleteMessage(3)
        MessageService.deleteMessage(2)
    }

    @Test
    fun deleteChat() {
        MessageService.clearBase()
        MessageService.addUser("Jhon")
        MessageService.deleteChat(2)
        MessageService.deleteChat(1)
    }

    @Test
    fun readMessage() {
        MessageService.clearBase()
        MessageService.addUser("Jhon")
        MessageService.addMessage(Messages(1, "Hi Smith"))
        MessageService.readMessage(1)
        MessageService.readMessage(4)
    }

    @Test
    fun editMessage() {
        MessageService.clearBase()
        MessageService.addUser("Jhon")
        MessageService.addMessage(Messages(1, "Hi Smith"))
        MessageService.editMessage(1, "111")
        MessageService.editMessage(3, "111")
        MessageService.deleteMessage(1)
        MessageService.editMessage(1, "111")

    }

    @Test
    fun statisticOfChat() {
        MessageService.statisticOfChat()
        MessageService.addUser("111")
        MessageService.statisticOfChat()
    }

    @Test
    fun listOfChat() {
        MessageService.listOfChat()
    }

    @Test
    fun listOfChatId() {
        MessageService.clearBase()
        MessageService.listOfChatId(1)
        MessageService.addUser("111")
        MessageService.listOfChatId(1)
        MessageService.listOfChatId(3)
    }

    @Test
    fun getUser() {
        MessageService.getUser()
    }

    @Test
    fun getMessage() {
        MessageService.getMessage()
    }
}