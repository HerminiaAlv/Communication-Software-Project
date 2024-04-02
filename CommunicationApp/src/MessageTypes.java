
public enum MessageTypes {
	Login,  
	Logout, 
	ChatMessage, // sending chat message
	GetLogs, 	// IT user requesting user logs
	UpdateUser, // IT user editing a user
	CreateChat, // User creating a new chat-room
	AddUsers,	// Adding a user to the chat
	NotifyUser	// Send notification(new mssgs) to the recipient
}

// public class LoginMessage implements serializable {
//	string username;
//	string password;
//   
//	MessageTypes Type = Login;
//}

// public class CreateChatMessage implements serializable {
//	string[] names;
//	//	MessageTypes Type;
//}

// public class ChatMessage implements serializable {
//	Message msg;
//	MessageTypes Type;
//}