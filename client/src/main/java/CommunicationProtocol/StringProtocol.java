package CommunicationProtocol;

public class StringProtocol implements Protocol{

    @Override
    public String input(String input) {
        return input;
    }

    @Override
    public String output(String output) {
        return output;
    }
}
