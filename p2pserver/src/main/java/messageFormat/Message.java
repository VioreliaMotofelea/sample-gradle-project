package messageFormat;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL) // Ignore null fields during serialization
@JsonIgnoreProperties(ignoreUnknown = true) // Ignore unexpected fields during deserialization
public class Message {
    public String command;
    public String username;
    public int port;

    public String message;

    public Message() {}

    public Message(String command, String username, int port, String message) {
        this.command = command;
        this.username = username;
        this.port = port;
        this.message = message;
    }

    public Message(String command, String username) {
        this.command = command;
        this.username = username;
    }

    public Message(String command, String username, String message) {
        this.command = command;
        this.username = username;
        this.message = message;
    }

    public Message(String command) {
        this.command = command;
    }

    public Message(String command, int port) {
        this.command = command;
        this.port = port;
    }
}
