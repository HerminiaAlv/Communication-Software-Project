import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServerMessageTest {
    private ServerMessage serverMessage;

    @BeforeEach
    void setUp() {
        serverMessage = new ServerMessage();
    }

    @Test
    void getTypeShouldReturnUndefined() {
        assertEquals(MessageTypes.UNDEFINED, serverMessage.getType());
    }

    @Test
    void getStatusShouldReturnUndefined() {
        assertEquals(MessageStatus.UNDEFINED, serverMessage.getStatus());
    }

}
