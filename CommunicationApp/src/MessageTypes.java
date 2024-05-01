
public enum MessageTypes {
	LOGIN,
	LOGOUT,
	CHAT_MESSAGE, 		// sending chat message
	GET_LOGS, 		// IT user requesting user logs
	UPDATE_USER, 		// IT user editing a user
	CREATE_CHAT, 		// User creating a new chat-room
	ADD_USERS_TO_CHAT,	// Adding a user to the chat - low priority 
	NOTIFY_USER,		// Send notification(new msgs) to the recipient - low priority
	PIN_CHAT,		// When a user pins a chat, Server must keep a record - low priority
	UPDATE_USER_LIST, 
	UNDEFINED		// Default type
}
