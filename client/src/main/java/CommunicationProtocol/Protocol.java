package CommunicationProtocol;

public interface Protocol {
    String input(String input) throws Exception;

    String output(String output) throws Exception;
}
