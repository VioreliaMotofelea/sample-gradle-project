package messageFormat;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonFormat implements Format {
    @Override
    public String format(String input) {
        String[] parts = input.split(" ", 3);
        String command = parts[0];
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            switch (command) {
                case "!connect":
                    return objectMapper.writeValueAsString(new Message(command, Integer.parseInt(parts[2])));
                case "!bye":
                case "!hello":
                case "!ack":
                    return objectMapper.writeValueAsString(new Message(command, parts[1]));
                case "!message":
                    return objectMapper.writeValueAsString(new Message(command, parts[1], parts[2]));
                case "!byebye":
                    return objectMapper.writeValueAsString(new Message(command));
                default:
                    return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Message input(String input) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(input, Message.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String output(Message output) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(output);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
