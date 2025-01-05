package messageFormat;

public interface Format {
    String format(String input);
    Message input(String input);
    String output(Message output);
}
